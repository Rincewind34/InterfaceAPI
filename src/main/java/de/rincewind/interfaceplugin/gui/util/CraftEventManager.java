package de.rincewind.interfaceplugin.gui.util;

import java.util.ArrayList;
import java.util.List;

import de.rincewind.interfaceapi.handling.Event;
import de.rincewind.interfaceapi.handling.EventManager;
import de.rincewind.interfaceapi.handling.InterfaceListener;
import de.rincewind.interfaceplugin.Validate;

public class CraftEventManager implements EventManager {

	private List<Entry<?>> entries;

	public CraftEventManager() {
		this.entries = new ArrayList<>();
	}	
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <E extends Event<?>, F extends E> void callEvent(Class<E> eventCls, F event) {
		Validate.notNull(eventCls, "The event class cannot be null");
		Validate.notNull(event, "The event cannot be null");
		
		this.touchEntry(eventCls).fireEvent(event);
		
		if (eventCls != Event.class) {
			this.callEvent((Class) eventCls.getSuperclass(), event);
		}
	}

	@Override
	public <E extends Event<?>> ListenerBase<E> registerListener(Class<E> eventCls, InterfaceListener<E> listener) {
		Validate.notNull(eventCls, "The event class cannot be null");
		Validate.notNull(listener, "The listener cannot be null");

		return new ListenerBase<>(eventCls, listener, this);
	}

	@Override
	public <E extends Event<?>> List<InterfaceListener<E>> getRegisteredListeners(Class<E> eventCls) {
		Validate.notNull(eventCls, "The event class cannot be null");

		return this.touchEntry(eventCls).listeners;
	}

	public <E extends Event<?>> void addListenerBefore(Class<E> eventCls, InterfaceListener<E> listener) {
		assert eventCls != null : "The event class is nulll";
		assert listener != null : "The listener is null";
		
		this.touchEntry(eventCls).listeners.add(0, listener);
	}

	public <E extends Event<?>> void addListenerAfter(Class<E> eventCls, InterfaceListener<E> listener) {
		assert eventCls != null : "The event class is nulll";
		assert listener != null : "The listener is null";
		
		this.touchEntry(eventCls).listeners.add(listener);
	}

	@SuppressWarnings("unchecked")
	private <E extends Event<?>> Entry<E> touchEntry(Class<E> eventCls) {
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

	private static class Entry<E extends Event<?>> {

		private Class<E> eventCls;
		private List<InterfaceListener<E>> listeners;

		public Entry(Class<E> eventCls) {
			this.listeners = new ArrayList<>();
			this.eventCls = eventCls;
		}

		public <F extends E> void fireEvent(F event) {
			assert event != null : "The event is null";
			
			for (InterfaceListener<E> listener : this.listeners) {
				listener.onAction(event);
				
				if (event.isConsumed()) {
					break;
				}
			}
		}

	}

}
