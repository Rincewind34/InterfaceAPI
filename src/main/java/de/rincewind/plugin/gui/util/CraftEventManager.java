package de.rincewind.plugin.gui.util;

import java.util.ArrayList;
import java.util.List;

import de.rincewind.api.gui.util.EventManager;
import de.rincewind.api.handling.InterfaceListener;
import de.rincewind.api.handling.events.Event;
import de.rincewind.api.handling.listener.Listener;

@SuppressWarnings("deprecation")
public class CraftEventManager implements EventManager {
	
	private List<Entry<?>> entries;
	
	public CraftEventManager() {
		this.entries = new ArrayList<>();
	}
	
	@Override
	@Deprecated
	public List<Listener<?>> getListeners() {
		return new ArrayList<>();
	}
	
	@Override
	@Deprecated
	public void callEvent(Object event) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <E extends Event<?>> void callEvent(Class<E> eventCls, E event) {
		Entry<E> entry = null;
		
		for (Entry<?> target : this.entries) {
			if (target.eventCls == eventCls) {
				entry = (Entry<E>) target;
			}
		}
		
		if (entry == null) {
			entry = new Entry<>();
			this.entries.add(entry);
		}
		
		entry.fireEvent(event);
	}

	@Override
	@Deprecated
	public <E extends Event<?>> ListenerBase<E> registerListener(Listener<E> listener) {
		return this.registerListener(listener.getEventClass(), listener);
	}
	
	@Override
	public <E extends Event<?>> ListenerBase<E> registerListener(Class<E> eventCls, InterfaceListener<E> listener) {
		return new ListenerBase<>(eventCls, listener, this);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <E extends Event<?>> List<InterfaceListener<E>> getRegisteredListeners(Class<E> eventCls) {
		for (Entry<?> entry : this.entries) {
			if (entry.eventCls == eventCls) {
				return ((Entry<E>) entry).listeners;
			}
		}
		
		return new ArrayList<>();
	}
	
	@Override
	@Deprecated
	public List<Listener<?>> getListeners(Class<? extends Event<?>> eventClass) {
		List<Listener<?>> listeners = new ArrayList<>();

		for (Listener<?> listener : this.getListeners()) {
			if (listener.getEventClass().equals(eventClass)) {
				listeners.add(listener);
			}
		}
		
		return listeners;
	}
	
	@SuppressWarnings("unchecked")
	public <E extends Event<?>> void addListenerBefore(Class<E> eventCls, InterfaceListener<E> listener) {
		for (Entry<?> entry : this.entries) {
			if (entry.eventCls == eventCls) {
				((Entry<E>) entry).listeners.add(0, listener);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public <E extends Event<?>> void addListenerAfter(Class<E> eventCls, InterfaceListener<E> listener) {
		for (Entry<?> entry : this.entries) {
			if (entry.eventCls == eventCls) {
				((Entry<E>) entry).listeners.add(listener);
			}
		}
	}
	
	
	private static class Entry<E extends Event<?>> {
		
		private Class<E> eventCls;
		private List<InterfaceListener<E>> listeners;
		
		public Entry() {
			this.listeners = new ArrayList<>();
		}
		
		private void fireEvent(E event) {
			for (InterfaceListener<E> listener : this.listeners) {
				listener.onAction(event);
			}
		}
		
	}
	
}
