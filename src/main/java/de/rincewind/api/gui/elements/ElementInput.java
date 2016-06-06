package de.rincewind.api.gui.elements;

import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.elements.abstracts.ElementSlot;
import de.rincewind.api.gui.elements.util.Elements.ElementInputExtendable;

/**
 * In this element, the user can put items in, and pickup items already dropped in the
 * slot. You are able to inject the item into the cache.
 * 
 * @author Rincewind34
 * @since 2.3.3
 * 
 * @see ElementInputExtendable
 */
public abstract interface ElementInput extends ElementSlot { //TODO implement iterable
	
	/**
	 * Calls {@link ElementSlot#isEmpty()} to check, if there is an item
	 * to inject.
	 * 
	 * @return {@link ElementSlot#isEmpty()}
	 */
	public abstract boolean canInject();
	
	//TODO public boolean isIndexValid(int index);
	
	//TODO public int getMemorySize();
	
	/**
	 * Clears the memory of injected items.
	 */
	public abstract void clearMemory();
	
	/**
	 * Returns the index of the latest injected item. If there
	 * was no item injected already, the result will be <code>-1</code>.
	 * 
	 * @return the index of the latest injected item
	 */
	public abstract int lastIndex();
	
	/**
	 * Injects the item, witch is present in the slot. If the slot is empty,
	 * this method will return <code>null</code>.
	 * If there is an item in the slot, the item will be removed and the item will
	 * be added to the memory.
	 * 
	 * @return the item, witch is present in the slot
	 */
	public abstract ItemStack inject();
	
	/**
	 * Reads an item from the memory using its index and returns it.
	 * 
	 * @param index to get the item
	 * 
	 * @return the read item
	 * 
	 * @throws ArrayIndexOutOfBoundsException if the index is invalid
	 */
	public abstract ItemStack fromMemory(int index);
	
}
