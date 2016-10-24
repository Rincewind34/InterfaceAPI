package de.rincewind.api.item;

import java.util.function.Predicate;

import org.bukkit.inventory.ItemStack;

@Deprecated
public interface ItemSelector extends Predicate<ItemStack> {
	
	public abstract boolean isMatching(ItemStack item);
	
	@Override
	public default boolean test(ItemStack item) {
		return this.isMatching(item);
	}
	
}
