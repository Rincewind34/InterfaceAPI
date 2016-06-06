package de.rincewind.api.gui.elements;

import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.components.Selectable;
import de.rincewind.api.gui.elements.util.ElementDefaults;
import de.rincewind.api.handling.events.MultiButtonPressEvent;
import de.rincewind.api.item.ItemModifier;

/**
 * In this button, you can run multiple action on one button.
 * 
 * On left-clicking this button, this element will be 'selected'. In the lore
 * will be displayed all actions you are able to run. By an second left-click
 * the selected line will be executed. On a right-click, you can scroll through
 * the lore, to select the action you want to run.
 * 
 * When an action is executed the {@link MultiButtonPressEvent} will be called.
 * 
 * @author Rincewind34
 * @since 2.3.3
 */
public interface ElementMultiButton extends ElementButton, Selectable, Iterable<String> {
	
	/**
	 * Selects the next added entry in the lore. If the currently selected
	 * entry is the last one in the lore, the first entry will be selected.
	 * 
	 * @return the new selected entry
	 */
	public abstract String next();
	
	/**
	 * Selects the added entry before the currently selected in the lore. If the currently selected
	 * entry is the first one in the lore, the last entry will be selected.
	 * 
	 * @return the new selected entry
	 */
	public abstract String back();
	
	/**
	 * Returns the currently selected entry.
	 * 
	 * @return the currently selected entry
	 */
	public abstract String getSwitch();
	
	/**
	 * Sets the selected index of the entrylist.
	 * If the new index is equals or greater than the size of the added entries
	 * the index will be set to 0.
	 * 
	 * @param index change to
	 */
	public abstract void setSwitchId(int index); //TODO refact to setSwitchIndex(int)
	
	/**
	 * Adds an entry to the list.
	 * 
	 * @param entry adding to the list
	 */
	public abstract void addSwitch(String entry);
	
	/**
	 * Removes an entry from the list.
	 * 
	 * @param entry removing from the list.
	 */
	public abstract void removeSwitch(String entry);
	
	/**
	 * Clears the entrylist of this element.
	 */
	public abstract void clear();
	
	/**
	 * Returns the count of the added entries.
	 * 
	 * @return the count of the added entries
	 */
	public abstract int size();
	
	/**
	 * Returns the currently selected index from the list.
	 * 
	 * @return the currently selected index from the list
	 */
	public abstract int getSwitchId(); //TODO refact to getSwitchIndex()
	
	/**
	 * Sets the format witch is important for the regular lines in the lore.
	 * The first '%s' will be replaced with the raw entry. The default format is
	 * '§f%s' ({@link ElementDefaults#MULTI_BUTTON_DEFAULT_FORMAT}).
	 * 
	 * @param str change the format to
	 * 
	 * @throws NullPointerException if the format is <code>null</code>
	 */
	public abstract void setDefaultFormat(String str);
	
	/**
	 * Sets the format witch is important for the selected line in the lore.
	 * The first '%s' will be replaced with the raw entry. The default format is
	 * '§d%s' ({@link ElementDefaults#MULTI_BUTTON_SELECTED_FORMAT}).
	 * 
	 * @param str change the format to
	 * 
	 * @throws NullPointerException if the format is <code>null</code>
	 */
	public abstract void setSelectFormat(String str);
	
	/**
	 * Sets the item modifier, witch modifies the icon to make visible, 
	 * that the button is selected. By the default value, the button will
	 * be enchanted.
	 * 
	 * @param modifier to set
	 * 
	 * @throws NullPointerException if the modifier is <code>null</code>
	 */
	public abstract void setSelectModifier(ItemModifier modifier);
	
	/**
	 * Modifies an item like the button, when it gets selected.
	 * 
	 * @param item to modify
	 * 
	 * @return the modified item
	 * 
	 * @throws NullPointerException if the item is <code>null</code>
	 */
	public abstract ItemStack modify(ItemStack item);
	
}
