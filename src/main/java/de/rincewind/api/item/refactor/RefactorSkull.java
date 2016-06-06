package de.rincewind.api.item.refactor;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class RefactorSkull {
	
	public ItemStack setOwner(ItemStack item, String name) {
		ItemStack clone = item.clone();
		SkullMeta meta = (SkullMeta) clone.getItemMeta();
		meta.setOwner(name);
		clone.setItemMeta(meta);
		return clone;
	}
	
}
