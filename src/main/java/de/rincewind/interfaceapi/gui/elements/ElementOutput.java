package de.rincewind.interfaceapi.gui.elements;

import org.bukkit.inventory.ItemStack;

import de.rincewind.interfaceapi.gui.elements.abstracts.ElementSlot;
import de.rincewind.interfaceapi.gui.elements.util.ClickAction;
import de.rincewind.interfaceapi.gui.elements.util.ClickBlocker;
import de.rincewind.interfaceapi.gui.elements.util.Elements.ElementOutputExtendable;

/**
 * This element allows you to output an item into the slot
 * and let the user get it. The {@link ClickBlocker} of this element
 * will be unlocked and the ClickAction {@link ClickAction#PLACE} will be
 * added, to inhibit, that the user can place items in the slot. 
 * 
 * @author Rincewind34
 * @since 2.3.3
 * 
 * @see ElementOutputExtendable
 */
public abstract interface ElementOutput extends ElementSlot {
	
	/**
	 * Calls {@link ElementOutput#output(ItemStack, boolean)} to output
	 * an item.
	 * 
	 * @param item to output
	 */
	public abstract void output(ItemStack item);
	
	/**
	 * Outputs an item into the slot. If the given flag is <code>false</code>,
	 * this method will check, if there is already an item in this slot and will
	 * not replace the item with a new one.
	 * 
	 * @param item to output
	 * @param flag to control a force-output
	 * 
	 * @throws NullPointerException if the item is null
	 */
	public abstract void output(ItemStack item, boolean flag);

}
