package de.rincewind.interfaceapi.gui.elements.util;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.item.ItemLibrary;

/**
 * This is a basic class to modify items.
 * 
 * @author Rincewind34
 * @since 2.3.3
 */
public class Icon implements Displayable, Cloneable {

	public static final Icon AIR = new Icon();

	private ItemStack item;

	private Icon() {
		this.item = new ItemStack(Material.AIR);
	}

	public Icon(Material type) {
		this(type, 0);
	}

	public Icon(Material type, int data) {
		this(type, data, "§7");
	}

	public Icon(Material type, int data, String name) {
		if (type == null) {
			throw new IllegalArgumentException("Type cannot be null");
		}

		if (type == Material.AIR) {
			throw new IllegalArgumentException("Type cannot be AIR");
		}

		if (data < 0 || data > Short.MAX_VALUE) {
			throw new IllegalArgumentException("Data must be in the interval 0 to " + Short.MAX_VALUE);
		}

		if (name == null) {
			throw new IllegalArgumentException("Name cannot be null");
		}

		if (name.isEmpty()) {
			throw new IllegalArgumentException("Name cannot be empty");
		}

		this.item = ItemLibrary.refactor().renameItem(new ItemStack(type, 1, (short) data), name);
		this.item = ItemLibrary.refactor().addAllFlags(this.item);
	}

	public Icon(ItemStack item) {
		if (item == null) {
			throw new IllegalArgumentException("Item cannot be null");
		}

		if (item.getType() == Material.AIR) {
			throw new IllegalArgumentException("Itemtype cannot be AIR");
		}

		this.item = item;
	}

	@Override
	public Icon getIcon() {
		return this;
	}
	
	@Override
	public boolean hasStaticIcon() {
		return true;
	}
	
	@Override
	public boolean equals(Object icon) {
		if (this == icon) {
			return true;
		}
		if (icon == null || this.getClass() != icon.getClass()) {
			return false;
		}

		Icon other = (Icon) icon;
		return this.item.equals(other.item);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.item == null ? 0 : this.item.hashCode());
		return result;
	}
	
	@Override
	public String toString() {
		return this.isAir() ? "Icon#AIR" : "Icon{item=" + this.item.getType() + ";name=" + this.getName() + "}";
	}

	@Override
	public Icon clone() {
		Icon icon = new Icon();
		icon.item = this.item.clone();
		return icon;
	}

	public boolean isAir() {
		return this == Icon.AIR;
	}

	public String getName() {
		if (!this.isAir()) {
			return this.item.getItemMeta().getDisplayName();
		} else {
			return null;
		}
	}

	public Lore getLore() {
		if (this.isAir() || !this.item.getItemMeta().hasLore()) {
			return null;
		} else {
			return new Lore(this.item.getItemMeta().getLore());
		}
	}

	public Icon rename(String name) {
		if (!this.isAir()) {
			this.item = ItemLibrary.refactor().renameItem(this.item, name);
		}

		return this;
	}

	public Icon removeName() {
		return this.rename("§7");
	}

	public Icon describe(List<String> lore) {
		if (!this.isAir()) {
			this.item = ItemLibrary.refactor().loreItem(this.item, lore);
		}

		return this;
	}

	public Icon showInfo(boolean info) {
		if (!this.isAir()) {
			if (info) {
				this.item = ItemLibrary.refactor().removeAllFlags(this.item);
			} else {
				this.item = ItemLibrary.refactor().addAllFlags(this.item);
			}
		}

		return this;
	}

	public Icon enchant() {
		if (!this.isAir()) {
			this.item = ItemLibrary.refactor().enchantItem(this.item, Enchantment.WATER_WORKER, 1, false);
		}

		return this;
	}

	public Icon damage(int damage) {
		if (!this.isAir()) {
			this.item.setDurability((short) damage);
		}

		return this;
	}

	public Icon unenchant() {
		if (!this.isAir()) {
			this.item = ItemLibrary.refactor().unenchantItem(this.item, Enchantment.WATER_WORKER);
		}

		return this;
	}

	public Icon typecast(Material type) {
		if (this.isAir()) {
			throw new UnsupportedOperationException("Cannot typecast Icon#AIR");
		}
		
		if (type == Material.AIR) {
			throw new IllegalArgumentException("Unable to cast into Material#AIR");
		}
		
		this.item.setType(type);
		return this;
	}

	public Icon count(int amount) {
		if (!this.isAir()) {
			this.item.setAmount(amount);
		}

		return this;
	}

	/**
	 * Can never be <code>null</code>.
	 * 
	 * @return the bukkit itemstack
	 */
	public ItemStack toItem() {
		return this.item;
	}

}
