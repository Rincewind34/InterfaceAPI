package de.rincewind.api.handling;

import de.rincewind.api.handling.events.Event;

public interface InterfaceListener<T extends Event<?>> {
	
	public abstract void onAction(T event);
	
}
