package de.rincewind.api.listener;

import de.rincewind.api.events.DefaultElementEvent;

public interface ElementListener<T extends DefaultElementEvent<?>> {
	
	public abstract Class<T> getEventClass();
	
	public abstract void onFire(T event);
	
}
