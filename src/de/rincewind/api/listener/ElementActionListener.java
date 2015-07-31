package de.rincewind.api.listener;

import org.bukkit.event.inventory.InventoryClickEvent;

import de.rincewind.api.gui.elements.Element;

public abstract interface ElementActionListener {
	
	/**
	 * Is called, when the user right clicks at the element
	 * @param event The InventoryClickEvent
	 * @param element The clicked element
	 */
	public abstract void onRightClick(InventoryClickEvent event, Element element);
	
	/**
	 * Is called, when the user left clicks at the element
	 * @param event The InventoryClickEvent
	 * @param element The clicked element
	 */
	public abstract void onLeftClick(InventoryClickEvent event, Element e);
	
}
