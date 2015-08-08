package de.rincewind.api.gui.elements;

import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.elements.abstracts.ElementSlot;

public abstract interface ElementInput extends ElementSlot {
	
	public abstract boolean canInject();
	
	public abstract void clearMemory();
	
	public abstract int lastIndex();
	
	public abstract ItemStack inject();
	
	public abstract ItemStack fromMemory(int index);
	
}
