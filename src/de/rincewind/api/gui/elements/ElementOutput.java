package de.rincewind.api.gui.elements;

import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.elements.abstracts.ElementSlot;

public abstract interface ElementOutput extends ElementSlot {

	public abstract void output(ItemStack item);
	
	public abstract void output(ItemStack item, boolean flag);

}
