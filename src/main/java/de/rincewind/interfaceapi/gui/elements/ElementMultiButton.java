package de.rincewind.interfaceapi.gui.elements;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import de.rincewind.interfaceapi.gui.components.Selectable;
import de.rincewind.interfaceapi.gui.elements.abstracts.ElementDisplayable;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.handling.element.MultiButtonPressEvent;

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
public interface ElementMultiButton extends ElementDisplayable, Selectable, Iterable<Object> {
	
	/**
	 * Sets the selected index of the entry list.
	 * If the new index is equals or greater than the size of the added entries
	 * the index will be set to 0.
	 * 
	 * @param index change to
	 */
	public abstract void setSelectedIndex(int index);
	
	/**
	 * Adds an entry to the list.
	 * 
	 * @param entry adding to the list
	 */
	public abstract void addSwitch(Object entry);
	
	/**
	 * Removes an entry from the list.
	 * 
	 * @param entry removing from the list.
	 */
	public abstract void removeSwitch(Object entry);
	
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
	public abstract int getSelectedIndex();
	
	/**
	 * Sets the format witch is important for the regular lines in the lore.
	 * The first '%s' will be replaced with the raw entry. The default format is
	 * '§f%s'.
	 * 
	 * @param str change the format to
	 * 
	 * @throws NullPointerException if the format is <code>null</code>
	 */
	public abstract void setDefaultFormat(String str);
	
	/**
	 * Sets the format witch is important for the selected line in the lore.
	 * The first '%s' will be replaced with the raw entry. The default format is
	 * '§d%s'.
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
	public abstract void setSelectModifier(Function<Icon, Icon> modifier);
	
	/**
	 * Modifies an item like the button, when it gets selected.
	 * 
	 * @param item to modify
	 * 
	 * @return the modified item
	 * 
	 * @throws NullPointerException if the item is <code>null</code>
	 */
	public abstract Icon modify(Icon item);
	
	public abstract <T> T next();
	
	public abstract <T> T back();
	
	public abstract <T> T getSelected();
	
	public abstract <T> List<T> getSwitches();
	
	@Override
	public default Iterator<Object> iterator() {
		return this.getSwitches().iterator();
	}
	
	public default <T> T getSelected(Class<T> cls) {
		return cls.cast(this.getSelected());
	}
	
	public default <T> List<T> getSwitches(Class<T> cls) {
		return this.getSwitches().stream().map((entry) -> {
			return cls.cast(entry);
		}).collect(Collectors.toList());
	}
	
}
