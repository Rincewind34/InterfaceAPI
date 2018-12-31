package de.rincewind.interfaceapi.gui.elements;

import java.util.function.Predicate;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import de.rincewind.interfaceapi.gui.elements.abstracts.ElementSlot;
import de.rincewind.interfaceplugin.Validate;

/**
 * In this element, the user can put items in, and pickup items already dropped
 * in the slot. You are able to inject the item into the cache.
 * 
 * @author Rincewind34
 * @since 2.3.3
 */
public abstract interface ElementInput extends ElementSlot {

	public static Predicate<ItemStack> only(Material type) {
		return (item) -> {
			return item.getType() == type;
		};
	}

	public static Predicate<ItemStack> only(Material type, short data) {
		return (item) -> {
			return item.getType() == type
					&& (((item.getItemMeta() instanceof Damageable) && ((Damageable) item).getDamage() == data) || data == 0);
		};
	}

	public static Predicate<ItemStack> only(ItemStack format) {
		Validate.notNull(format, "The format cannot be null");

		if (format.getType() == Material.AIR) {
			throw new IllegalArgumentException("The format cannot be AIR");
		}

		return (item) -> {
			return item.isSimilar(format);
		};
	}

	public abstract void setMaxStackSize(int maxStackSize, boolean force);

	public abstract void setInputPredicate(Predicate<ItemStack> predicate, boolean force);

	/**
	 * Injects the item, witch is present in the slot. If the slot is empty,
	 * this method will return <code>null</code>. If there is an item in the
	 * slot, the item will be removed and the item will be added to the memory.
	 * 
	 * @return the item, witch is present in the slot
	 */
	public abstract ItemStack popItem();

	public abstract Predicate<ItemStack> getInputPredicate();

	public default void setInputPredicate(Predicate<ItemStack> predicate) {
		this.setInputPredicate(predicate, false);
	}

	public default void setMaxStackSize(int maxStackSize) {
		this.setMaxStackSize(maxStackSize, false);
	}

}
