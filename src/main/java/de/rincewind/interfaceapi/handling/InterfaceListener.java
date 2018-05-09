package de.rincewind.interfaceapi.handling;

import de.rincewind.interfaceapi.exceptions.EventPipelineException;

public interface InterfaceListener<T extends Event> {
	
	public abstract void onAction(T event) throws EventPipelineException;
	
}
