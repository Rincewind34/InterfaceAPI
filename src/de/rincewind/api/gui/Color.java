package de.rincewind.api.gui;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum Color {
	
	WHITE((byte) 0),
	ORANGE((byte) 1),
	MAGENTA((byte) 2),
	LIGHT_BLUE((byte) 3),
	YELLOW((byte) 4),
	LIME((byte) 5),
	PINK((byte) 6),
	GRAY((byte) 7),
	LIGHT_GRAY((byte) 8),
	CYAN((byte) 9),
	PURPLE((byte) 10),
	BLUE((byte) 11),
	BROWN((byte) 12),
	GREEN((byte) 13),
	RED((byte) 14),
	BLACK((byte) 15),
	
	/**
	 * No glasspanes (AIR)
	 */
	TRANSLUCENT((byte) -1); //Durchsichtig
	
	private byte data;
	
	private Color(byte data) {
		this.data = data;
	}
	
	/**
	 * Get the glaspane
	 * @return The classpane in the selected color
	 */
	public ItemStack asItem() {
		if (data == -1) {
			return new ItemStack(Material.AIR);
		} else {
			return this.rename(new ItemStack(Material.STAINED_GLASS_PANE, 1, data));
		}
	}
	
	private ItemStack rename(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(" ");
		item.setItemMeta(meta);
		return item;
	}
	
}
