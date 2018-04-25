package de.rincewind.interfaceapi.gui.components;

import de.rincewind.interfaceapi.handling.Event;
import de.rincewind.interfaceapi.handling.EventManager;

/**
 * This interface is implemented by classes, witch want to call events ({@link Event}).
 * To handle the events and listeners, there is the {@link EventManager}
 * 
 * @author Rincewind34
 * @since 2.3.3
 * 
 * @see EventManager
 */
public interface EventBased {
	
	/**
	 * Returns the EventManager handling the listeners and events.
	 * 
	 * @return the EventManager handling the listeners and events
	 */
	public abstract EventManager getEventManager();
	
}
