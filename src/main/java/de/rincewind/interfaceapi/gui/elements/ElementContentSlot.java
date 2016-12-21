package de.rincewind.interfaceapi.gui.elements;

import org.bukkit.inventory.ItemStack;

import de.rincewind.interfaceapi.gui.elements.abstracts.ElementSlot;

public interface ElementContentSlot extends ElementSlot {
	
	public abstract void setContent(ItemStack item);
	
}
