package de.rincewind.api.handling.listener;

import de.rincewind.api.handling.InterfaceListener;
import de.rincewind.api.handling.events.Event;

@Deprecated
public interface Listener<T extends Event<?>> extends InterfaceListener<T> {
	
	public abstract Class<T> getEventClass();
	
	public abstract void onFire(T event);
	
	@Override
	public default void onAction(T event) {
		this.onFire(event);
	}
	
}
