package de.rincewind.interfaceapi.handling;

public interface InterfaceListener<T extends Event<?>> {
	
	public abstract void onAction(T event);
	
}
