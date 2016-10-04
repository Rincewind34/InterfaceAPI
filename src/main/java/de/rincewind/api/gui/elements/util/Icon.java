package de.rincewind.api.gui.elements.util;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import de.rincewind.api.item.ItemLibary;
import de.rincewind.api.item.ItemRefactor.Lore;

/**
 * This is a basic class to modify items.
 * 
 * @author Rincewind34
 * @since 2.3.3
 */
public class Icon {
	
	private ItemStack item;
	
	public Icon(Material type) {
		this(type, 0);
	}
	
	public Icon(Material type, int data) {
		this(type, data, " ");
	}
	
	public Icon(Material type, int data, String name) {
		this(ItemLibary.refactor().renameItem(new ItemStack(type, 1, (short) data), name));
	}
	
	@Deprecated
	public Icon(Material type, short data) {
		this(type, (int) data);
	}
	
	@Deprecated
	public Icon(Material type, short data, String name) {
		this(type, (int) data, name);
	}
	
	public Icon(ItemStack item) {
		this.item = item;
		this.item = ItemLibary.refactor().addAllFlags(this.item);
	}
	
	
	public Icon rename(String name) {
		this.item = ItemLibary.refactor().renameItem(this.item, name);
		return this;
	}
	
	public Icon describe(Lore lore) {
		this.item = ItemLibary.refactor().loreItem(this.item, lore);
		return this;
	}
	
	@Deprecated
	public Icon describe(List<String> lore) {
		return this.describe(new Lore(lore));
	}
	
	public Icon showInfo(boolean info) {
		if (info) {
			this.item = ItemLibary.refactor().removeAllFlags(this.item);
		} else {
			this.item = ItemLibary.refactor().addAllFlags(this.item);
		}
		
		return this;
	}
	
	public Icon enchant() {
		this.item = ItemLibary.refactor().enchantItem(this.item, Enchantment.WATER_WORKER, 1, false);
		return this;
	}
	
	public Icon damage(int damage) {
		this.item.setDurability((short) damage);
		return this;
	}
	
	public String getName() {
		return this.item.getItemMeta().getDisplayName();
	}
	
	public Icon unenchant() {
		this.item = ItemLibary.refactor().unenchantItem(this.item, Enchantment.WATER_WORKER);
		return this;
	}
	
	public Icon typecast(Material type) {
		this.item.setType(type);
		return this;
	}
	
	public Icon count(int amount) {
		this.item.setAmount(amount);
		return this;
	}
	
	public ItemStack toItem() {
		return this.item;
	}
	
}
