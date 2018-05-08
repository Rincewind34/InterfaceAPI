package de.rincewind.interfaceapi.handling;

import java.util.List;

import de.rincewind.interfaceapi.exceptions.APIException;
import de.rincewind.interfaceapi.gui.components.EventBased;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.windows.abstracts.Window;
import de.rincewind.interfaceplugin.Validate;
import de.rincewind.interfaceplugin.gui.util.CraftEventManager;

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
	
	public abstract <E extends Event> List<InterfaceListener<E>> getRegisteredListeners(Class<E> eventClass);
	
	public abstract <E extends Event> ListenerBase<E> registerListener(Class<E> eventCls, InterfaceListener<E> listener);
	
	public abstract <E extends Event> void callEvent(Class<E> eventCls, E event);
	
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
	public static class ListenerBase<E extends Event> {
		
		private Class<E> eventCls;
		private InterfaceListener<E> listener;
		
		private CraftEventManager manager;
		
		private boolean added;
		
		public ListenerBase(Class<E> eventCls, InterfaceListener<E> listener, CraftEventManager manager) {
			Validate.notNull(listener, "The listener cannot be null!");
			Validate.notNull(manager, "The eventManager cannot be null!");
			
			this.eventCls = eventCls;
			this.listener = listener;
			this.manager = manager;
			this.added = false;
		}
		
		/**
		 * Adds the listener to register after all even registered listeners.
		 */
		public void addAfter() {
			if (this.added) {
				throw new APIException("The listenerbase was already added!");
			}
			
			this.manager.addListenerAfter(this.eventCls, this.listener);
			this.added = true;
		}
		
		/**
		 * Adds the listener to register before all even registered listeners.
		 */
		public void addBefore() {
			if (this.added) {
				throw new APIException("The listenerbase was already added!");
			}
			
			this.manager.addListenerBefore(this.eventCls, this.listener);
			this.added = true;
		}
		
	}
	
}