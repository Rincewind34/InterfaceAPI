package de.rincewind.interfaceapi.gui.elements;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.components.DisplayableDisabled;
import de.rincewind.interfaceapi.gui.components.Modifyable;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.Elements.ElementSwitcherExtendable;
import de.rincewind.interfaceapi.handling.element.SwitchChangeEvent;

/**
 * This element allows the suer to switch through items, added to this element.
 * Its an infinite loop by clicking on the 'switcher'. With right-click you can
 * switch forward and with left-click you can switch backward. By shift-click you
 * can skip one entry and jump 2 items forward or backward.
 * 
 * For a better handling with this element, you can 'save' an object from an specified
 * type in each switch-entry.
 * 
 * This element implements {@link Iterable} to iterate throw all added entries.
 * 
 * @param <T> specifying the object-type
 * 
 * @author Rincewind34
 * @since 2.3.3
 * 
 * @see ElementSwitcherExtendable
 */
public interface ElementSwitcher extends Element, DisplayableDisabled, Iterable<Displayable> {
	
	/**
	 * Switches to the next entry and returns the entry switched to.
	 * The {@link ElementSwitcher#setSwitchId(int)} will be called.
	 * 
	 * @return the entry switched to
	 */
	public abstract Displayable next();
	
	/**
	 * Switches backward and returns the entry switched to.
	 * The {@link ElementSwitcher#setSwitchId(int)} will be called.
	 * 
	 * @return the entry switched to
	 */
	public abstract Displayable back();
	
	/**
	 * Sets the index switched to. If the new index does not match
	 * the frame of 0 and the length of the entrylist, the index will
	 * be adapted.
	 * 
	 * The {@link SwitchChangeEvent} will be called after updating this element
	 * in the handler ({@link Modifyable}).
	 * 
	 * @param index to set
	 */
	public abstract void setSwitchIndex(int index);
	
	public abstract void setSwitchIndex(int index, boolean fireEvent);
	
	/**
	 * Adds a {@link SwitchItem} to this element. If that is the first added
	 * entry, the item will be immediately displayed.
	 * 
	 * @param item to add
	 * 
	 * @throws NullPointerException if the item is <code>null</code>
	 */
	public abstract void addSwitch(Displayable item);
	
	/**
	 * Removes a {@link SwitchItem} from this element. If that was the last added
	 * entry, this element will not be longer displayed.
	 * 
	 * @param item The switch item to remove
	 */
	public abstract void removeSwitch(Displayable item);
	
	/**
	 * Clears all entries from this element. This method does not call {@link Modifyable#readItemsFrom(de.rincewind.interfaceapi.gui.elements.abstracts.Element)}
	 * so you do have to call it by your self to update this element or add a new
	 * entry
	 */
	public abstract void clear();
	
	/**
	 * Returns the size of the entrylist.
	 * 
	 * @return the size of the entrylist
	 */
	public abstract int size();
	
	/**
	 * Returns the index switched to.
	 * 
	 * @return the index switched to
	 */
	public abstract int getSwitchIndex();
	
	public abstract List<Displayable> getSwitches();
	
	public abstract <T extends Displayable> T getCurrentSwitch();
	
	public abstract <T extends Displayable> T getSwitch(int index);
	
	@Override
	public default Iterator<Displayable> iterator() {
		return this.getSwitches().iterator();
	}
	
	public default <T extends Displayable> T getCurrentSwitch(Class<T> cls) {
		return cls.cast(this.getCurrentSwitch());
	}
	
	public default <T extends Displayable> T getSwitch(Class<T> cls, int index) {
		return cls.cast(this.getSwitch(index));
	}
	
	public default <T extends Displayable> List<T> getSwitches(Class<T> cls) {
		return this.getSwitches().stream().map((entry) -> {
			return cls.cast(entry);
		}).collect(Collectors.toList());
	}
	
}
