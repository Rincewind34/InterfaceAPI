package de.rincewind.interfaceapi.gui.elements.util;

import java.util.Objects;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.elements.util.lore.EmptyLore;
import de.rincewind.interfaceapi.gui.elements.util.lore.Lore;
import de.rincewind.interfaceapi.gui.elements.util.lore.SimpleLore;
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
		this.amount = 0;
		this.damage = 0;
		this.type = Material.AIR;
		this.name = null;
		this.lore = EmptyLore.INSTANCE;
	}

	public Icon(Material type) {
		this(type, "§7");
	}

	public Icon(Material type, String name) {
		this.item = ItemLibrary.refactor().renameItem(new ItemStack(Material.BEDROCK), "Blank icon");

		this.amount = 1;
		this.name = "§7";
		this.showInfo = true;
		this.lore = Lore.create();
		this.typecast(type);
		this.rename(name);
	}

	public Icon(ItemStack item) {
		if (item == null) {
			throw new IllegalArgumentException("Item cannot be null");
		}

		if (item.getType() == Material.AIR) {
			throw new IllegalArgumentException("Itemtype cannot be Material#AIR");
		}

		this.item = item.clone();
		ItemMeta meta = this.item.getItemMeta();

		this.type = item.getType();
		this.damage = item.getDurability();
		this.amount = item.getAmount();
		this.enchantet = meta.hasEnchants();
		this.name = meta.hasDisplayName() ? meta.getDisplayName() : "§7";
		this.lore = meta.hasLore() ? Lore.create(meta.getLore()) : Lore.create();

		for (ItemFlag flag : ItemFlag.values()) {
			if (!meta.hasItemFlag(flag)) {
				this.showInfo = true;
				break;
			}
		}
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
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (this.getClass() != obj.getClass()) {
			return false;
		}

		Icon other = (Icon) obj;

		// TODO try to avoid using toItem
		return this.toItem().equals(other.toItem());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		// TODO try to avoid using toItem
		result = prime * result + this.toItem().hashCode();
		return result;
	}

	@Override
	public String toString() {
		return this.isAir() ? "Icon#AIR" : "Icon{type=" + this.type + ";name=" + this.name + "}";
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
		icon.dirty = this.dirty;
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
		if (!this.isAir() && !Objects.equals(this.name, name)) {
			if (name != null && name.isEmpty()) {
				throw new IllegalArgumentException("Name cannot be empty");
			}

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

		if (this.isAir()) {
			return this.item;
		}

		SimpleLore lore = (SimpleLore) this.lore;

		if (!this.dirty) {
			if (this.lore != null && lore.isDirty()) {
				return this.item = ItemLibrary.refactor().loreItem(this.item, lore.toList());
			} else {
				return this.item;
			}
		} else {
			if (this.item == null) {
				this.item = new ItemStack(this.type, this.amount, (short) this.damage);
			} else {
				this.item.setType(this.type);
				this.item.setAmount(this.amount);
				this.item.setDurability((short) this.damage);
			}
			
			if (this.name != null) {
				this.item = ItemLibrary.refactor().renameItem(this.item, this.name);
			}

			if (this.lore != null) {
				this.item = ItemLibrary.refactor().loreItem(this.item, lore.toList());
			}

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
