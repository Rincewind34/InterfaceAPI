package de.rincewind.interfaceplugin.gui.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.rincewind.interfaceapi.exceptions.EventPipelineException;
import de.rincewind.interfaceapi.handling.Event;
import de.rincewind.interfaceapi.handling.EventManager;
import de.rincewind.interfaceapi.handling.InterfaceListener;
import de.rincewind.interfaceplugin.Validate;

public final class CraftEventManager implements EventManager {

	private Set<Entry<?>> entries;

	public CraftEventManager() {
		this.entries = new HashSet<>();
	}

	@Override
	public int countRegisteredPipelineListeners(Class<? extends Event> eventCls) {
		Validate.notNull(eventCls, "The event class cannot be null");

		return this.touchEntry(eventCls).listeners.size();
	}

	@Override
	public int countRegisteredMonitorListeners(Class<? extends Event> eventCls) {
		Validate.notNull(eventCls, "The event class cannot be null");

		return this.touchEntry(eventCls).monitors.size();
	}

	@Override
	public int countRegisteredListeners(Class<? extends Event> eventCls) {
		Validate.notNull(eventCls, "The event class cannot be null");

		Entry<?> entry = this.touchEntry(eventCls);
		return entry.listeners.size() + entry.monitors.size();
	}

	@Override
	@SuppressWarnings("unchecked")
	public int countActiveListeners(Class<? extends Event> eventCls) {
		int count = this.countRegisteredListeners(eventCls);

		if (eventCls == Event.class) {
			return count;
		} else {
			return count + this.countActiveListeners((Class<? extends Event>) eventCls.getSuperclass());
		}
	}

	@Override
	public <E extends Event> void callEvent(Class<E> eventCls, E event) {
		Validate.notNull(eventCls, "The event class cannot be null");
		Validate.notNull(event, "The event cannot be null");

		if (event.isConsumed()) {
			throw new IllegalArgumentException("The event is already consumed");
		}

		if (event.isInMonitor()) {
			throw new IllegalArgumentException("The event is in monitor");
		}
		
		this.callEvent0(eventCls, event);
	}
	
	@Override
	public <E extends Event> ListenerBase registerListener(Class<E> eventCls, Runnable listener) {
		Validate.notNull(listener, "The listener cannot be null");
		
		return this.registerListener(eventCls, (event) -> {
			listener.run();
		});
	}

	@Override
	public <E extends Event> ListenerBase registerListener(Class<E> eventCls, InterfaceListener<E> listener) {
		Validate.notNull(eventCls, "The event class cannot be null");
		Validate.notNull(listener, "The listener cannot be null");

		return this.new ListenerBaseImpl<>(eventCls, listener);
	}
	
	@SuppressWarnings("unchecked")
	protected <E extends Event> Entry<E> touchEntry(Class<E> eventCls) {
		assert eventCls != null : "The event class is null";

		for (Entry<?> target : this.entries) {
			if (target.eventCls == eventCls) {
				return (Entry<E>) target;
			}
		}

		Entry<E> entry = new Entry<>(eventCls);
		this.entries.add(entry);
		return entry;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <E extends Event> void callEvent0(Class<E> eventCls, E event) {
		Entry<E> entry = this.touchEntry(eventCls);

		if (!event.isConsumed()) {
			entry.fireEvent(event);
		}

		if (eventCls != Event.class) {
			this.callEvent0((Class) eventCls.getSuperclass(), event);
		} else {
			event.monitor();
		}

		entry.monitorEvent(event);
	}

	protected static class Entry<E extends Event> {

		private final Class<E> eventCls;

		protected List<InterfaceListener<E>> listeners;
		protected Set<InterfaceListener<E>> monitors;

		public Entry(Class<E> eventCls) {
			this.eventCls = eventCls;
			this.listeners = new ArrayList<>();
			this.monitors = new HashSet<>();
		}

		public void fireEvent(E event) {
			assert event != null : "The event is null";
			assert !event.isConsumed() : "The event is already consumed";
			assert !event.isInMonitor() : "The event is already in monitor";

			for (InterfaceListener<E> listener : this.listeners) {
				try {
					listener.onAction(event);
				} catch (Exception exception) {
					System.err.println("Exception occurred in pipeline of " + event.getClass());
					exception.printStackTrace();

					if (!(exception instanceof EventPipelineException)) {
						event.consume();
					}
				}

				if (event.isInMonitor()) {
					throw new IllegalStateException("Unable to enable monitor mode of event in listener");
				}

				if (event.isConsumed()) {
					break;
				}
			}
		}

		public void monitorEvent(E event) {
			assert event != null : "The event is null";
			assert event.isInMonitor() : "The event is not in monitor";

			for (InterfaceListener<E> listener : this.monitors) {
				try {
					listener.onAction(event);
				} catch (Exception exception) {
					System.err.println("Exception occured in pipeline of " + event.getClass());
					exception.printStackTrace();
				}
			}
		}

	}

	private class ListenerBaseImpl<E extends Event> implements ListenerBase {

		private boolean added;

		private Class<E> eventCls;
		private InterfaceListener<E> listener;

		public ListenerBaseImpl(Class<E> eventCls, InterfaceListener<E> listener) {
			assert eventCls != null : "Event class is null";
			assert listener != null : "Listener class is null";

			this.eventCls = eventCls;
			this.listener = listener;
			this.added = false;
		}

		@Override
		public void addAfter() {
			this.validateAdded();

			CraftEventManager.this.touchEntry(this.eventCls).listeners.add(this.listener);
			this.added = true;
		}

		@Override
		public void addBefore() {
			this.validateAdded();

			CraftEventManager.this.touchEntry(this.eventCls).listeners.add(0, this.listener);
			this.added = true;
		}

		@Override
		public void monitor() {
			this.validateAdded();

			CraftEventManager.this.touchEntry(this.eventCls).monitors.add(this.listener);
			this.added = true;
		}

		private void validateAdded() {
			if (this.added) {
				throw new IllegalStateException("The listener is already added");
			}
		}

	}

}
