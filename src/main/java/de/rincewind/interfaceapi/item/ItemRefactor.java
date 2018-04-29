package de.rincewind.interfaceapi.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.Lists;

import de.rincewind.interfaceapi.item.refactor.RefactorArmor;
import de.rincewind.interfaceapi.item.refactor.RefactorBook;
import de.rincewind.interfaceapi.item.refactor.RefactorPotion;
import de.rincewind.interfaceapi.item.refactor.RefactorSkull;

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
			this(new ArrayList<>());
		}
		
		public Lore(List<String> lore) {
			this.lore = lore;
		}
		
		public Lore(Lore lore) {
			this(lore.asList());
		}
		
		public Lore(String... array) {
			this(Lists.newArrayList(array));
		}
		
		public Lore split() {
			return this.split("\\n");
		}
		
		public Lore split(String spliter) {
			List<String> result = new ArrayList<>();
			
			for (String element : this.lore) {
				result.addAll(Arrays.asList(element.split(Pattern.quote(spliter))));
			}
			
			this.lore = result;
			return this;
		}
		
		public Lore color(String color) {
			return this.color(color, this.lore.size() - 1);
		}
		
		public Lore color(String color, int end) {
			return this.color(color, 0, end);
		}
		
		public Lore color(String color, int begin, int end) {
			for (int i = begin; i <= end; i++) {
				this.lore.set(i, color + this.lore.get(i));
			}
			
			return this;
		}
		
		public Lore moveIn(int amount) {
			return this.moveIn(amount, this.lore.size() - 1);
		}
		
		public Lore moveIn(int amount, int end) {
			return this.moveIn(amount, 0, end);
		}
		
		public Lore moveIn(int amount, int begin, int end) {
			StringBuilder builder = new StringBuilder();
			
			for (int i = 0; i < amount; i++) {
				builder.append(" ");
			}
			
			String movement = builder.toString();
			
			for (int i = begin; i <= end; i++) {
				this.lore.set(i, movement + this.lore.get(i));
			}
			
			return this;
		}
		
		public Lore add(String element) {
			this.lore.add(element);
			return this;
		}
		
		public String getLine(int i) {
			return this.lore.get(i);
		}
		
		public List<String> asList() {
			return this.lore;
		}
		
		public String[] asArray() {
			return this.lore.toArray(new String[this.lore.size()]);
		}
	}
	
}
