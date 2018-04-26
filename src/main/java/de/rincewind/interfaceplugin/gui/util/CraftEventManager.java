package de.rincewind.interfaceplugin.gui.util;

import java.util.ArrayList;
import java.util.List;

import de.rincewind.interfaceapi.handling.Event;
import de.rincewind.interfaceapi.handling.EventManager;
import de.rincewind.interfaceapi.handling.InterfaceListener;

public class CraftEventManager implements EventManager {

	private List<Entry<?>> entries;

	public CraftEventManager() {
		this.entries = new ArrayList<>();
	}

	@Override
	public <E extends Event<?>> void callEvent(Class<E> eventCls, E event) {
		if (eventCls == null) {
			throw new IllegalArgumentException("The event class cannot be null");
		}

		if (event == null) {
			throw new IllegalArgumentException("The event cannot be null");
		}

		this.touchEntry(eventCls).fireEvent(event);
	}

	@Override
	public <E extends Event<?>> ListenerBase<E> registerListener(Class<E> eventCls, InterfaceListener<E> listener) {
		if (eventCls == null) {
			throw new IllegalArgumentException("The event class cannot be null");
		}

		if (listener == null) {
			throw new IllegalArgumentException("The listener cannot be null");
		}

		return new ListenerBase<>(eventCls, listener, this);
	}

	@Override
	public <E extends Event<?>> List<InterfaceListener<E>> getRegisteredListeners(Class<E> eventCls) {
		if (eventCls == null) {
			throw new IllegalArgumentException("The event class cannot be null");
		}

		return this.touchEntry(eventCls).listeners;
	}

	public <E extends Event<?>> void addListenerBefore(Class<E> eventCls, InterfaceListener<E> listener) {
		this.touchEntry(eventCls).listeners.add(0, listener);
	}

	public <E extends Event<?>> void addListenerAfter(Class<E> eventCls, InterfaceListener<E> listener) {
		this.touchEntry(eventCls).listeners.add(listener);
	}

	@SuppressWarnings("unchecked")
	private <E extends Event<?>> Entry<E> touchEntry(Class<E> eventCls) {
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

		private void fireEvent(E event) {
			for (InterfaceListener<E> listener : this.listeners) {
				listener.onAction(event);
				
				if (event.isConsumed()) {
					break;
				}
			}
		}

	}

}
