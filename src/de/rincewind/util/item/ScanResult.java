package de.rincewind.util.item;

import org.bukkit.inventory.ItemStack;

import de.rincewind.util.item.categorys.Categorys;

public class ScanResult {
	
	private ItemStack item;
	
	public ScanResult(ItemStack item) {
		this.item = item;
	}
	
	/**
	 * 
	 * @return The scaned item
	 */
	public ItemStack getResult() {
		return item;
	}
	
	/**
	 * 
	 * @param key The categorykey
	 * @return If the MaterialData of this item is present in the category
	 */
	public boolean isInCategory(String key) {
		if (!Categorys.containsKey(key)) {
			return false;
		} else {
			return Categorys.get(key).containsMaterial(item.getData());
		}
	}
	
}
