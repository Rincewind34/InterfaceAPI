package de.rincewind.interfaceapi.handling;

import de.rincewind.interfaceapi.gui.components.EventBased;

/**
 * This class handles the events and listeners to some objects extending the
 * interface {@link EventBased}.<br>
 * <br>
 * There are many events could be fired and each of them has one attendant
 * listener-class ({@link InterfaceListener}). If an event is fired all
 * registered listeners, handling the fired event, will be automatically
 * executed.
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

	public abstract <E extends Event> ListenerBase registerListener(Class<E> eventCls, Runnable listener);

	public abstract <E extends Event> ListenerBase registerListener(Class<E> eventCls, InterfaceListener<? super E> listener);

	/**
	 * This class is a dummy to let the user decide if the listener to register
	 * has to be executed before or after all already registered listeners. To
	 * finally register the listener use {@link #addAfter()},
	 * {@link #addBefore()} or {@link #monitor()}. You have to execute one of
	 * these methods to finally register the listener. Otherwise the listener
	 * will never be called!<br>
	 * <br>
	 * More information for event pipeline: {@link EventManager}
	 * 
	 * @author Rincewind34
	 * @since 2.3.3
	 * 
	 * @see EventManager#registerListener(Class, InterfaceListener)
	 */
	public static interface ListenerBase {

		/**
		 * Adds the concerning listener after all working listeners already
		 * registered in the pipeline.<br>
		 * <br>
		 * More information for event pipeline: {@link EventManager}
		 */
		public abstract void addAfter();

		/**
		 * Adds the concerning listener before all working listeners already
		 * registered in the pipeline.<br>
		 * <br>
		 * More information for event pipeline: {@link EventManager}
		 */
		public abstract void addBefore();

		/**
		 * Registers the concerning listener to monitor the if the event is
		 * called. The listener is not allowed to change any values (including
		 * cancel the event if supported) while monitoring the event. <br>
		 * The registered listeners monitoring the event are called after all
		 * working listeners. The order the monitor listeners are called is
		 * undefined.<br>
		 * <br>
		 * More information for event pipeline: {@link EventManager}
		 */
		public abstract void monitor();

	}

}