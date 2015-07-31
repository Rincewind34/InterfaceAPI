package de.rincewind.util.item;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class ItemCreator {
	
	private ItemStack item;
	
	public ItemCreator() {
		this.item = new ItemStack(Material.AIR);
	}
	
	public ItemCreator setType(Material type) {
		this.item.setType(type);
		return this;
	}
	
	@SuppressWarnings("deprecation")
	public ItemCreator setData(byte data) {
		this.item.setData(new MaterialData(this.item.getType(), data));
		return this;
	}
	
	public ItemCreator setAmount(int amount) {
		this.item.setAmount(amount);
		return this;
	}
	
	public ItemCreator setName(String name) {
		this.item = ItemLibary.refactor().renameItem(this.item, name);
		return this;
	}
	
	public ItemCreator setLore(List<String> lore) {
		this.item = ItemLibary.refactor().loreItem(this.item, lore);
		return this;
	}
	
	public ItemCreator enchantItem(Enchantment ench, int lvl, boolean flag) {
		this.item = ItemLibary.refactor().enchantItem(this.item, ench, lvl, flag);
		return this;
	}
	
	public ItemCreator removeFlag(ItemFlag itemFlag) {
		this.item = ItemLibary.refactor().removeFlag(this.item, itemFlag);
		return this;
	}
	
	public ItemCreator removeAllFlags() {
		this.item = ItemLibary.refactor().removeAllFlags(this.item);
		return this;
	}
	
	public ItemStack create() {
		return this.item;
	}
}
