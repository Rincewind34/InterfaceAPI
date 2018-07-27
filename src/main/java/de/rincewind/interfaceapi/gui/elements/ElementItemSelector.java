package de.rincewind.interfaceapi.gui.elements;

import java.util.function.UnaryOperator;

import org.bukkit.inventory.ItemStack;

import de.rincewind.interfaceapi.gui.elements.abstracts.ElementDisplayable;
import de.rincewind.interfaceplugin.Validate;
import de.rincewind.interfaceplugin.gui.elements.CraftElementItemSelector;

/**
 * To check if a specific item should be selectable, catch the interact event
 * and consume it.
 */
public interface ElementItemSelector extends ElementDisplayable {
	
	public static void setSelectInstructions(String instructions) {
		Validate.notNull(instructions, "The instructions cannot be null");
		
		CraftElementItemSelector.INSTRUCTIONS_SELECT = instructions;
	}
	
	public static void setUnselectInstructions(String instructions) {
		Validate.notNull(instructions, "The instructions cannot be null");
		
		CraftElementItemSelector.INSTRUCTIONS_UNSELECT = instructions;
	}
	
	public static void setCollectInstructions(String instructions) {
		Validate.notNull(instructions, "The instructions cannot be null");
		
		CraftElementItemSelector.INSTRUCTIONS_COLLECT = instructions;
	}
	
	public static void setCloneFunction(UnaryOperator<ItemStack> function) {
		Validate.notNull(function, "The function cannot be null");
		
		CraftElementItemSelector.cloneFunction = function;
	}
	
	public static String getSelectInstructions() {
		return CraftElementItemSelector.INSTRUCTIONS_SELECT;
	}
	
	public static String getUnselectInstructions() {
		return CraftElementItemSelector.INSTRUCTIONS_UNSELECT;
	}
	
	public static String getCollectInstructions() {
		return CraftElementItemSelector.INSTRUCTIONS_COLLECT;
	}
	
	/**
	 * Item is unselectable after selection
	 * 
	 * @param value
	 */
	public abstract void canUnselect(boolean value);
	
	/**
	 * Copies the amount of the item to select
	 * 
	 * @param value
	 */
	public abstract void copyAmount(boolean value);
	
	/**
	 * Is the player allowed to grep the item using the middle mouse key
	 * 
	 * @param value
	 */
	public abstract void canCollect(boolean value);
	
	/**
	 * Display the selected item and not the instructions icon
	 * 
	 * @param value
	 */
	public abstract void displaySelectedItem(boolean value);
	
	public abstract void setSelected(ItemStack item);

	public abstract void setSelected(ItemStack item, boolean fireEvent);

	public abstract boolean canUnselect();
	
	public abstract boolean copyAmount();
	
	public abstract boolean canCollect();
	
	public abstract boolean displaySelectedItem();
	
	public abstract boolean isItemSelected();

	public abstract ItemStack getSelected();
	
}
