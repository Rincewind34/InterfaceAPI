package de.rincewind.api.gui.util;

import java.util.List;

import de.rincewind.api.exceptions.APIException;
import de.rincewind.api.gui.components.EventBased;
import de.rincewind.api.gui.elements.abstracts.Element;
import de.rincewind.api.gui.windows.abstracts.Window;
import de.rincewind.api.handling.InterfaceListener;
import de.rincewind.api.handling.events.Event;
import de.rincewind.api.handling.listener.Listener;
import de.rincewind.plugin.gui.util.CraftEventManager;
import lib.securebit.Validate;

/**
 * This class handles the events and listeners to some Objects like {@link Element} or
 * {@link Window}.
 * 
 * There are many events could be fired and each of them has one attendant listener-class
 * ({@link Listener}).
 * If an event is fired all registered listeners, handling the fired event, will be automatically executed.
 * 
 * @author Rincewind34
 * @since 2.3.3
 * 
 * @see EventBased
 */
@SuppressWarnings("deprecation")
public interface EventManager {
	
	/**
	 * Returns all registered listeners in the order they
	 * may executed on an incoming event.
	 * If there is no listener registered, this method will return
	 * an empty List.
	 * 
	 * @return all registered listeners
	 */
	@Deprecated
	public abstract List<Listener<?>> getListeners();
	
	/**
	 * Returns all registered listeners matching to a given event-class in 
	 * the order they may executed on the incoming event.
	 * If there is no registered listener matching to the given event-class,
	 * this method will return an empty List.
	 * 
	 * @param eventClass to get the matching listeners
	 * 
	 * @return all registered listeners matching to a given event-class
	 * 
	 * @throws NullPointerException if the event-class is <b>null</b>
	 */
	@Deprecated
	public abstract List<Listener<?>> getListeners(Class<? extends Event<?>> eventClass);
	
	public abstract <E extends Event<?>> List<InterfaceListener<E>> getRegisteredListeners(Class<E> eventClass);
	
	/**
	 * Returns a created ListenerBase-instance to register a given listener.
	 * 
	 * @param listener to register
	 * 
	 * @return a created ListenerBase-instance to register a given listener
	 * 
	 * @throws NullPointerException if the listener is <b>null</b>
	 */
	@Deprecated
	public abstract <E extends Event<?>> ListenerBase<E> registerListener(Listener<E> listener);
	
	public abstract <E extends Event<?>> ListenerBase<E> registerListener(Class<E> eventCls, InterfaceListener<E> listener);
	
	/**
	 * Executing all registered listeners matching to the fired event.
	 * 
	 * @param event fired
	 * 
	 * @throws NullPointerException if the event is <b>null</b>
	 */
	@Deprecated
	public abstract void callEvent(Object event);
	
	public abstract <E extends Event<?>> void callEvent(Class<E> eventCls, E event);
	
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
	 * @see EventManager#registerListener(Listener)
	 */
	public static class ListenerBase<E extends Event<?>> {
		
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