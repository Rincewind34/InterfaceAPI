package de.rincewind.interfaceapi.item;

import org.bukkit.inventory.ItemStack;

public class ItemLibrary {
	
	public static ItemRefactor refactor() {
		return new ItemRefactor();
	}
	
	public static ItemSerializer serialier() {
		return new ItemSerializer();
	}
	
	@Deprecated
	public static ItemNBTModifier nbtModifier(ItemStack item) {
		return new ItemNBTModifier(item);
	}
	
}
