package de.rincewind.interfaceapi.handling;

import de.rincewind.interfaceapi.gui.components.EventBased;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.windows.abstracts.Window;

/**
 * This class handles the events and listeners to some Objects like {@link Element} or
 * {@link Window}.
 * 
 * There are many events could be fired and each of them has one attendant listener-class
 * ({@link InterfaceListener}).
 * If an event is fired all registered listeners, handling the fired event, will be automatically executed.
 * 
 * @author Rincewind34
 * @since 2.3.3
 * 
 * @see EventBased
 */
public interface EventManager {
	
	public abstract int countRegisteredListeners(Class<? extends Event> eventCls);
	
	public abstract int countRegisteredMonitorListeners(Class<? extends Event> eventCls);
	
	public abstract int countRegisteredPipelineListeners(Class<? extends Event> eventCls);
	
	public abstract int countActiveListeners(Class<? extends Event> eventCls);
	
	public abstract <E extends Event> void callEvent(Class<E> eventCls, E event);
	
	public abstract <E extends Event> ListenerBase registerListener(Class<E> eventCls, InterfaceListener<E> listener);
	
	/**
	 * This class is a dummy to let the user decide if the listener to register has to
	 * be executed before or after all even registered listeners.
	 * To finally register the listener use {@link ListenerBase#addAfter()} and
	 * {@link ListenerBase#addBefore()}. You have to execute one of these methods to
	 * register the listener. Otherwise the listener will never be executed!
	 * 
	 * @author Rincewind34
	 * @since 2.3.3
	 * 
	 * @see EventManager#registerListener(Class, InterfaceListener)
	 */
	public static interface ListenerBase {
		
		public abstract void addAfter();
		
		public abstract void addBefore();
		
		public abstract void monitor();
		
	}

	
}