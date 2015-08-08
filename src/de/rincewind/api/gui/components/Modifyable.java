package de.rincewind.api.gui.components;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.elements.abstracts.Element;
import de.rincewind.util.item.ItemLibary;

public abstract interface Modifyable {
	
	public static final ItemStack EMPTY_USED_SLOT = ItemLibary.refactor().renameItem(new ItemStack(Material.WOOL, 1, (byte) 15), "�0EMPTY_USED_ITEM");
	public static final ItemStack INVISIBLE_ELEMENT = ItemLibary.refactor().renameItem(new ItemStack(Material.WOOL, 1, (byte) 15), "�0INVISIBLE_ELEMENT");
	
	/**
	 * @return All elements of this GUI
	 */
	public abstract List<Element> getElements();
	
	/**
	 * 
	 * @param x The x-coordinate
	 * @param y The y-coordinate
	 * @return If the slot at the given point P(x|y) is free (Color.TRANSLUCENT)
	 */
	public abstract boolean hasSpaceAt(int x, int y);
	
	/**
	 * 
	 * Adds an new element to the window
	 * 
	 * @param element The new element
	 * @return The id of the new element in the window
	 */
	public abstract int addElement(Element element);
	
	/**
	 * @param x The x-coordinate
	 * @param y The y-coordinate
	 * @return The itemstack at the given point P(x|y) in the window
	 */
	public abstract ItemStack getItemAt(int x, int y);
	
	/**
	 * 
	 * @param x The y-coordinate
	 * @param y The z-coordinate
	 * @return The element at the given Point(x|y) in the window
	 */
	public abstract Element getElementAt(int x, int y);
	
	/**
	 * Updates all itemstacks in the window
	 */
	public abstract void updateItemMap();
	
	/**
	 * Updates the itemstack for the given element
	 *
	 * @param element The elememt to update
	 */
	public abstract void updateItemMap(Element element);
	
	/**
	 * Updates the itemstack for an element by a given id
	 *
	 * @param id The id of the element to update
	 */
	public abstract void updateItemMap(int id);
	
}
