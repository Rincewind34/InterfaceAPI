package de.rincewind.api.item;

import org.bukkit.inventory.ItemStack;

public interface ItemSelector {
	
	public abstract boolean isMatching(ItemStack item);
	
}
