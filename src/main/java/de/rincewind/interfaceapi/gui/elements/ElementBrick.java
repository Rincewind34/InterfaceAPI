package de.rincewind.interfaceapi.gui.elements;

import java.util.function.Supplier;

import org.bukkit.inventory.ItemStack;

import de.rincewind.interfaceapi.gui.elements.abstracts.ElementDisplayable;

/**
 * If the user clicks on this element, on the cursor an item will be set.
 * 
 * @author Rincewind34
 * @since 2.3.3
 */
public interface ElementBrick extends ElementDisplayable {
	
	/**
	 * Returns a new created item, witch would get the user on his cursor
	 * if he clicks this element.
	 * 
	 * @return a new created item
	 */
	public abstract ItemStack createItem();
	
	/**
	 * Sets the ItemCreator, to create the item to set on the cursor
	 * 
	 * @param creator to set
	 * 
	 * @throws NullPointerException if the creator is <code>null</code>
	 */
	public abstract void setCreator(Supplier<ItemStack> creator);
	
}
