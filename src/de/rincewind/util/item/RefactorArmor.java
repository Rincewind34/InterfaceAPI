package de.rincewind.util.item;

import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class RefactorArmor {
	
	public ItemStack color(ItemStack item, Color color) {
		ItemStack clone = item.clone();
		LeatherArmorMeta meta = (LeatherArmorMeta) clone.getItemMeta();
		meta.setColor(color);
		item.setItemMeta(meta);
		return item;
	}
	
}
