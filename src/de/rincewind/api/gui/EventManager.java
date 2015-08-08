package de.rincewind.api.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.rincewind.api.events.DefaultElementEvent;
import de.rincewind.api.listener.ElementListener;

public class EventManager {
	
	private List<ElementListener<?>> listeners;
	
	public EventManager() {
		this.listeners = new ArrayList<>();
	}
	
	public List<ElementListener<?>> getListeners() {
		return Collections.unmodifiableList(this.listeners);
	}
	
	public void addListener(ElementListener<?> listener) {
		this.listeners.add(listener);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends DefaultElementEvent<?>> void callEvent(T event) {
		for (ElementListener<?> listener : this.listeners) {
			if (listener.getEventClass().equals(event.getClass())) {
				((ElementListener<T>) listener).onFire(event);
			}
		}
	}
	
}
