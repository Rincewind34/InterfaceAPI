package de.rincewind.api.item;

import org.bukkit.inventory.ItemStack;

public abstract interface ItemModifier {
	
	public abstract ItemStack modifyItem(ItemStack item);
	
}
