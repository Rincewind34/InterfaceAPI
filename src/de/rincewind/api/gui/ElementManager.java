package de.rincewind.api.gui;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.inventory.InventoryClickEvent;

import de.rincewind.api.gui.elements.abstracts.Element;

public class ElementManager {
	
	private static Map<Class<? extends Element>, ElementActivation> elements =
			new HashMap<Class<? extends Element>, ElementManager.ElementActivation>();
	
	/**
	 * Register a new ElementActivation for an existing element
	 * @param elementClass The class of the target element
	 * @param activation The activation, which will be executed
	 */
	public static void registerElement(Class<? extends Element> elementClass, ElementActivation activation) {
		ElementManager.elements.put(elementClass, activation);
	}
	
	/**
	 * Runs an ElementActivation for a given element
	 * @param elementClass The class of the target element
	 * @param object The target element
	 * @param event The InventoryClickEvent-object
	 */
	public static boolean runElementActivation(Class<? extends Element> elementClass, Element object, InventoryClickEvent event) {
		return ElementManager.elements.get(elementClass).run(object, event);
	}
	
	/**
	 * @param class1 The class of the target element to check
	 * @return If the element is exists
	 */
	public static boolean containsElement(Class<? extends Element> class1) {
		return ElementManager.elements.containsKey(class1);
	}
	
	/**
	 * @param elementClass The class of the target element
	 * @return The ElementActivation-object for the given element (by class)
	 */
	public static ElementActivation getActivation(Class<? extends Element> elementClass) {
		return ElementManager.elements.get(elementClass);
	}
	
	public static abstract interface ElementActivation {
		
		/**
		 * Runs this ElementActivation for a given element with a given InventoryClickEvent-object
		 * @param element The given element
		 * @param event The InventoryClickEvent
		 */
		public abstract boolean run(Element element, InventoryClickEvent event);
		
	}
	
}
