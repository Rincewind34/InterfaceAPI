package de.rincewind.util.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemRefactor {
	
	/**
	 * Renames an item
	 * 
	 * @param item The item to rename
	 * @param name The specified name
	 * @return The renamed item
	 */
	public ItemStack renameItem(ItemStack item, String name) {
		ItemStack clone = item.clone();
		ItemMeta meta = clone.getItemMeta();
		meta.setDisplayName(name);
		clone.setItemMeta(meta);
		return clone;
	}
	
	/**
	 * 
	 * Enchants an item
	 * 
	 * @param item The item to enchant
	 * @param ench The enchantment
	 * @param lvl The level
	 * @param flag The usaveenchat-flag
	 * @return The enchanted item
	 */
	public ItemStack enchantItem(ItemStack item, Enchantment ench, int lvl, boolean flag) {
		ItemStack clone = item.clone();
		ItemMeta meta = clone.getItemMeta();
		meta.addEnchant(ench, lvl, flag);
		clone.setItemMeta(meta);
		return clone;
	}
	
	/**
	 * Unenchants an item
	 * 
	 * @param item The item to unenchant
	 * @param ench The enchantment
	 * @return The unenchanted item
	 */
	public ItemStack unenchantItem(ItemStack item, Enchantment ench) {
		ItemStack clone = item.clone();
		ItemMeta meta = clone.getItemMeta();
		meta.removeEnchant(ench);
		clone.setItemMeta(meta);
		return clone;
	}
	
	/**
	 * Gives an item a lore
	 * 
	 * @param item The item
	 * @param lore The lore
	 * @return The edited item
	 */
	public ItemStack loreItem(ItemStack item, List<String> lore) {
		ItemStack clone = item.clone();
		ItemMeta meta = clone.getItemMeta();
		meta.setLore(lore);
		clone.setItemMeta(meta);
		return clone;
	}
	
	/**
	 * Adds an itemflag to the item
	 * 
	 * @param item The item
	 * @param itemFlag The itemflag
	 * @return The edited item
	 */
	public ItemStack addFlag(ItemStack item, ItemFlag itemFlag) {
		ItemStack clone = item.clone();
		ItemMeta meta = clone.getItemMeta();
		meta.addItemFlags(itemFlag);
		clone.setItemMeta(meta);
		return clone;
	}
	
	/**
	 * Removes an itemflag of an item
	 * 
	 * @param item The item
	 * @param itemFlag The itemflag
	 * @return The edited item
	 */
	public ItemStack removeFlag(ItemStack item, ItemFlag itemFlag) {
		ItemStack clone = item.clone();
		ItemMeta meta = clone.getItemMeta();
		meta.removeItemFlags(itemFlag);
		clone.setItemMeta(meta);
		return clone;
	}
	
	/**
	 * Adds all itemflags
	 * 
	 * @param item The item
	 * @return The edited item
	 */
	public ItemStack addAllFlags(ItemStack item) {
		for (ItemFlag flag : ItemFlag.values()) {
			item = this.addFlag(item, flag);
		}
		
		return item;
	}
	
	/**
	 * Removes all itemflags
	 * 
	 * @param item The item
	 * @return The edited item
	 */
	public ItemStack removeAllFlags(ItemStack item) {
		for (ItemFlag flag : ItemFlag.values()) {
			item = this.removeFlag(item, flag);
		}
		
		return item;
	}
	
	/**
	 * Gives an item a lore
	 * 
	 * @param item The item
	 * @param lore The lore
	 * @return The edited item
	 */
	public ItemStack loreItem(ItemStack item, Lore lore) {
		return this.loreItem(item, lore.asList());
	}
	
	public RefactorSkull skull() {
		return new RefactorSkull();
	}
	
	public RefactorBook book() {
		return new RefactorBook();
	}
	
	public RefactorArmor armor() {
		return new RefactorArmor();
	}
	
	public RefactorPotion potion() {
		return new RefactorPotion();
	}
	
	public static class Lore {
		
		private List<String> lore;
		
		public Lore() {
			this(new ArrayList<String>());
		}
		
		public Lore(List<String> lore) {
			this.lore = lore;
		}
		
		public Lore(Lore lore) {
			this(lore.asList());
		}
		
		public Lore(String... array) {
			this(Arrays.asList(array));
		}
		
		/**
		 * Adds an string to this lore
		 */
		public Lore addElement(String element) {
			this.lore.add(element);
			return new Lore(this.lore);
		}
		
		/**
		 * Convert this lore into a List
		 * 
		 * @return The list
		 */
		public List<String> asList() {
			return this.lore;
		}
		
		/**
		 * Convert this lore into an array
		 * 
		 * @return The array
		 */
		public String[] asArray() {
			return this.lore.toArray(new String[this.lore.size()]);
		}
	}
	
}
