package de.rincewind.api.gui.elements.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.components.Displayable;
import de.rincewind.api.item.ItemLibary;
import de.rincewind.api.item.ItemRefactor.Lore;

/**
 * This is a basic class to modify items.
 * 
 * @author Rincewind34
 * @since 2.3.3
 */
public class Icon implements Displayable, Cloneable {
	
	public static final Icon AIR = new Icon(Material.AIR);
	
	
	private ItemStack item;
	
	private Icon() {
		
	}
	
	public Icon(Material type) {
		this(type, 0);
	}
	
	public Icon(Material type, int data) {
		this(type, data, " ");
	}
	
	public Icon(Material type, int data, String name) {
		this(ItemLibary.refactor().renameItem(new ItemStack(type, 1, (short) data), name));
	}
	
	public Icon(ItemStack item) {
		this.item = item;
		this.item = ItemLibary.refactor().addAllFlags(this.item);
	}
	
	@Override
	public void setIcon(Icon icon) {
		this.item = icon.item;
	}
	
	@Override
	public Icon getIcon() {
		return this;
	}
	
	@Override
	public Icon clone() {
		Icon icon = new Icon();
		icon.item = this.item.clone();
		return icon;
	}
	
	public Icon rename(String name) {
		this.item = ItemLibary.refactor().renameItem(this.item, name);
		return this;
	}
	
	public Icon describe(Lore lore) {
		this.item = ItemLibary.refactor().loreItem(this.item, lore);
		return this;
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
	
	public Lore getLore() {
		if (!this.item.getItemMeta().hasLore()) {
			return null;
		} else {
			return new Lore(this.item.getItemMeta().getLore());
		}
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
