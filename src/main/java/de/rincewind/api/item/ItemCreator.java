package de.rincewind.api.item;

import java.util.function.Supplier;

import org.bukkit.inventory.ItemStack;

@Deprecated
public abstract interface ItemCreator extends Supplier<ItemStack> {
	
	public abstract ItemStack createItem();
	
	@Override
	public default ItemStack get() {
		return this.createItem();
	}
	
}
