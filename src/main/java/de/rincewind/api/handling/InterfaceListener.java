package de.rincewind.api.handling;

public interface InterfaceListener<T extends Event<?>> {
	
	public abstract void onAction(T event);
	
}
