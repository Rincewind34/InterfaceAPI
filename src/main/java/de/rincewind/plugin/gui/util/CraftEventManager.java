package de.rincewind.plugin.gui.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.rincewind.api.gui.util.EventManager;
import de.rincewind.api.handling.events.Event;
import de.rincewind.api.handling.listener.Listener;

public class CraftEventManager implements EventManager {
	
	private List<Listener<?>> listenersBefore;
	private List<Listener<?>> listenersAfter;
	
	public CraftEventManager() {
		this.listenersBefore = new ArrayList<>();
		this.listenersAfter = new ArrayList<>();
	}
	
	@Override
	public List<Listener<?>> getListeners() {
		List<Listener<?>> result = new ArrayList<>();
		
		for (int i = 0; i < this.listenersBefore.size(); i++) {
			result.add(this.listenersBefore.get(this.listenersBefore.size() - (i + 1)));
		}
		
		for (int i = 0; i < this.listenersAfter.size(); i++) {
			result.add(this.listenersAfter.get(i));
		}
		
		return Collections.unmodifiableList(result);
	}
	
	@Override
	public void addListener(Listener<?> listener) {
		this.registerListener(listener).addAfter();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <E extends Event<?>> void callEvent(E event) {
		for (Listener<?> listener : this.getListeners()) {
			if (listener.getEventClass().equals(event.getClass())) {
				((Listener<E>) listener).onFire(event);
			}
		}
	}

	@Override
	public ListenerBase registerListener(Listener<?> listener) {
		return new ListenerBase(listener, this);
	}
	
	@Override
	public List<Listener<?>> getListeners(Class<? extends Event<?>> eventClass) {
		List<Listener<?>> listeners = new ArrayList<>();

		for (Listener<?> listener : this.getListeners()) {
			if (listener.getEventClass().equals(eventClass)) {
				listeners.add(listener);
			}
		}
		
		return listeners;
	}
	
	public void addListenerBefore(Listener<?> listener) {
		this.listenersBefore.add(listener);
	}
	
	public void addListenerAfter(Listener<?> listener) {
		this.listenersAfter.add(listener);
	}

}
