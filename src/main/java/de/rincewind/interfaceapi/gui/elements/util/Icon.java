package de.rincewind.interfaceapi.gui.elements.util;

import java.util.Objects;

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
public final class Icon implements Displayable, Cloneable {

	public static final Icon AIR = new Icon();

	private boolean dirty;
	private boolean enchantet;
	private boolean showInfo;

	private int damage;
	private int amount;

	private Material type;

	private String name;
	private Lore lore;

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
		this.typecast(type);
		this.damage(data);
		this.rename(name);
	}

	public Icon(ItemStack item) {
		if (item == null) {
			throw new IllegalArgumentException("Item cannot be null");
		}

		if (item.getType() == Material.AIR) {
			throw new IllegalArgumentException("Itemtype cannot be Material#AIR");
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
		if (this.isAir()) {
			return Icon.AIR;
		}

		Icon icon = new Icon();
		icon.enchantet = this.enchantet;
		icon.showInfo = this.showInfo;
		icon.amount = this.amount;
		icon.damage = this.damage;
		icon.type = this.type;
		icon.name = this.name;
		icon.item = this.item != null ? this.item.clone() : null;
		icon.lore = this.lore != null ? this.lore.clone() : null;
		return icon;
	}

	public boolean isAir() {
		return this == Icon.AIR;
	}

	public boolean isDirty() {
		return this.dirty;
	}

	public boolean isShowInfo() {
		return this.showInfo;
	}

	public boolean isEnchantet() {
		return this.enchantet;
	}

	public int getAmount() {
		return this.amount;
	}

	public int getDamage() {
		return this.damage;
	}

	public Material getType() {
		return this.type;
	}

	public String getName() {
		return this.name;
	}

	public Lore getLore() {
		return this.lore;
	}

	public Icon rename(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Name cannot be null");
		}

		if (name.isEmpty()) {
			throw new IllegalArgumentException("Name cannot be empty");
		}
		
		if (!this.isAir() && !this.name.equals(name)) {
			this.name = name;
			this.dirty = true;
		}

		return this;
	}

	public Icon removeName() {
		return this.rename("§7");
	}

	public Icon describe(Lore lore) {
		if (!this.isAir()) {
			if (!Objects.equals(this.lore, lore)) {
				this.dirty = true;
			}

			// Set new lore instance, even on equals
			this.lore = lore;
		}

		return this;
	}

	public Icon showInfo(boolean info) {
		if (!this.isAir() && this.showInfo != info) {
			this.showInfo = info;
			this.dirty = true;
		}

		return this;
	}

	public Icon enchant() {
		if (!this.isAir() && !this.enchantet) {
			this.enchantet = true;
			this.dirty = true;
		}

		return this;
	}

	public Icon damage(int damage) {
		if (!this.isAir() && this.damage != damage) {
			if (damage < 0 || damage > Short.MAX_VALUE) {
				throw new IllegalArgumentException("Data must be in the interval 0 to " + Short.MAX_VALUE);
			}

			this.damage = damage;
			this.dirty = false;
		}

		return this;
	}

	public Icon unenchant() {
		if (!this.isAir() && this.enchantet) {
			this.enchantet = false;
			this.dirty = true;
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

		if (this.type != type) {
			this.type = type;
			this.dirty = true;
		}

		return this;
	}

	public Icon count(int amount) {
		if (!this.isAir() && this.amount != amount) {
			this.amount = amount;
			this.dirty = true;
		}

		return this;
	}

	/**
	 * Can never be <code>null</code>.
	 * 
	 * @return the bukkit itemstack
	 */
	public ItemStack toItem() {
		assert !(this.isAir() && this.dirty) : "Icon#AIR is dirty";
		
		if (!this.dirty) {
			if (this.lore != null && this.lore.isDirty()) {
				return this.item = ItemLibrary.refactor().loreItem(this.item, this.lore.toList());
			} else {
				return this.item;
			}
		} else {
			this.item = new ItemStack(this.type, this.amount, (short) this.damage);
			this.item = ItemLibrary.refactor().loreItem(this.item, this.lore.toList());
			this.item = ItemLibrary.refactor().renameItem(this.item, this.name);
			
			if (!this.showInfo) {
				this.item = ItemLibrary.refactor().addAllFlags(this.item);
			}
			
			if (this.enchantet) {
				this.item = ItemLibrary.refactor().enchantItem(this.item, Enchantment.WATER_WORKER, 1, false);
			}
			
			this.dirty = false;
			return this.item;
		}
	}

}
