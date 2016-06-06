package de.rincewind.api.handling.listener;

public interface Listener<T> {
	
	public abstract Class<T> getEventClass();
	
	public abstract void onFire(T event);
	
}
