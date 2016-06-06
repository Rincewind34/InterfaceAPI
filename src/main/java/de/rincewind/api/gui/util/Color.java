package de.rincewind.api.gui.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum Color {
	
	WHITE(0),
	ORANGE(1),
	MAGENTA(2),
	LIGHT_BLUE(3),
	YELLOW(4),
	LIME(5),
	PINK(6),
	GRAY(7),
	LIGHT_GRAY(8),
	CYAN(9),
	PURPLE(10),
	BLUE(11),
	BROWN(12),
	GREEN(13),
	RED(14),
	BLACK(15),
	
	TRANSLUCENT(-1);
	
	private byte data;
	
	private Color(int data) {
		this.data = (byte) data;
	}
	
	public ItemStack asItem() {
		if (this.data == -1) {
			return new ItemStack(Material.AIR);
		} else {
			return this.rename(new ItemStack(Material.STAINED_GLASS_PANE, 1, this.data));
		}
	}
	
	private ItemStack rename(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(" ");
		item.setItemMeta(meta);
		return item;
	}
	
}
