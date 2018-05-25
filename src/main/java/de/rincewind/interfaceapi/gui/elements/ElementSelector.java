package de.rincewind.interfaceapi.gui.elements;

import org.bukkit.inventory.ItemStack;

import de.rincewind.interfaceapi.gui.elements.abstracts.ElementDisplayable;

/**
 * To check if a specific item should be selectable, catch the interact event
 * and consume it.
 */
public interface ElementSelector extends ElementDisplayable {

	public abstract void canUnselect(boolean value);
	
	public abstract void copyAmount(boolean value);

	public abstract void setSelected(ItemStack item);

	public abstract void setSelected(ItemStack item, boolean fireEvent);

	public abstract boolean canUnselect();
	
	public abstract boolean copyAmount();

	public abstract ItemStack getSelected();
	
}
