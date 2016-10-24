package de.rincewind.api.item;

import java.util.function.Function;

import org.bukkit.inventory.ItemStack;

@Deprecated
public abstract interface ItemModifier extends Function<ItemStack, ItemStack> {
	
	public abstract ItemStack modifyItem(ItemStack item);
	
	@Override
	public default ItemStack apply(ItemStack item) {
		return this.modifyItem(item);
	}
	
}
