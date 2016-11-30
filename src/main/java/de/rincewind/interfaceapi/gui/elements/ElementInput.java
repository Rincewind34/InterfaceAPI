package de.rincewind.interfaceapi.gui.elements;

import java.util.Iterator;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import de.rincewind.interfaceapi.gui.elements.abstracts.ElementSlot;
import de.rincewind.interfaceapi.gui.elements.util.Elements.ElementInputExtendable;

/**
 * In this element, the user can put items in, and pickup items already dropped
 * in the slot. You are able to inject the item into the cache.
 * 
 * @author Rincewind34
 * @since 2.3.3
 * 
 * @see ElementInputExtendable
 */
public abstract interface ElementInput extends ElementSlot, Iterable<ItemStack> {

	/**
	 * Clears the memory of injected items.
	 */
	public abstract void clearMemory();

	/**
	 * Injects the item, witch is present in the slot. If the slot is empty,
	 * this method will return <code>null</code>. If there is an item in the
	 * slot, the item will be removed and the item will be added to the memory.
	 * 
	 * @return the item, witch is present in the slot
	 */
	public abstract ItemStack inject();

	public abstract List<ItemStack> getMemory();
	
	@Override
	public default Iterator<ItemStack> iterator() {
		return this.getMemory().iterator();
	}
	
	public default int getMemorySize() {
		return this.getMemory().size();
	}
	
	public default int lastMemoryIndex() {
		return this.getMemorySize() - 1;
	}
	
	public default ItemStack fromMemory(int index) {
		return this.getMemory().get(index);
	}
	
}
