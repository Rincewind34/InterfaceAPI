package de.rincewind.api.gui.elements.abstracts;

import org.bukkit.inventory.ItemStack;

public abstract interface ElementDisplayable extends Element {
	
	public abstract ItemStack getIcon();
	
	public abstract ItemStack getDisabledIcon();
	
	public abstract void setIcon(ItemStack icon);
	
	public abstract void setDisabledIcon(ItemStack icon);
	
}
