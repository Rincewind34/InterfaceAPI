package de.rincewind.api.gui.elements;

import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.util.Elements.ElementSwitcherExtendable;
import de.rincewind.api.gui.elements.util.Icon;
import de.rincewind.api.handling.events.SwitchEvent;

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
public interface ElementSwitcher<T> extends ElementButton, Iterable<ElementSwitcher.SwitchItem<T>> {
	
	/**
	 * Switches to the next entry and returns the entry switched to.
	 * The {@link ElementSwitcher#setSwitchId(int)} will be called.
	 * 
	 * @return the entry switched to
	 */
	public abstract SwitchItem<T> next();
	
	/**
	 * Switches backward and returns the entry switched to.
	 * The {@link ElementSwitcher#setSwitchId(int)} will be called.
	 * 
	 * @return the entry switched to
	 */
	public abstract SwitchItem<T> back();
	
	/**
	 * Returns the current switched entry.
	 * 
	 * @return the current switched entry
	 */
	public abstract SwitchItem<T> getSwitch();
	
	/**
	 * Sets the index switched to. If the new index does not match
	 * the frame of 0 and the length of the entrylist, the index will
	 * be adapted.
	 * 
	 * The {@link SwitchEvent} will be called after updating this element
	 * in the handler ({@link Modifyable}).
	 * 
	 * @param index to set
	 */
	public abstract void setSwitchId(int index); //TODO change to setSwitchIndex(int)
	
	public abstract void setSwitchId(int index, boolean fireEvent); //TODO change to setSwitchIndex(int, boolean)
	
	/**
	 * Adds a {@link SwitchItem} to this element. If that is the first added
	 * entry, the item will be immediately displayed.
	 * 
	 * @param item to add
	 * 
	 * @throws NullPointerException if the item is <code>null</code>
	 */
	public abstract void addSwitch(SwitchItem<T> item);
	
	/**
	 * Removes a {@link SwitchItem} from this element. If that was the last added
	 * entry, this element will not be longer displayed.
	 * 
	 * @param item
	 */
	public abstract void removeSwitch(SwitchItem<T> item);
	
	/**
	 * Clears all entries from this element. This method does not call {@link Modifyable#readItemsFrom(de.rincewind.api.gui.elements.abstracts.Element)}
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
	
	/**
	 * This is the class, you can add to the {@link ElementSwitcher}. You have to
	 * add an {@link ItemStack} / {@link Icon}, witch will be displayed, when this
	 * instance is matched, and a value with an specified type.
	 * 
	 * The value is only for you something to save in each entry.
	 * 
	 * @param <S> specifying value type. It is automaticly the same, you set in the root
	 * 				class {@link ElementSwitcher} as the parameter <T>.
	 * 
	 * @author Rincewind34
	 * @since 2.3.3
	 * 
	 * @see ElementSwitcher
	 */
	public static class SwitchItem<S> {
		
		private ItemStack item;
		private S save;
		
		public SwitchItem(ItemStack item, S save) {
			this.item = item;
			this.save = save;
		}
		
		public SwitchItem(Icon icon, S save) {
			this(icon.toItem(), save);
		}
		
		public ItemStack getItem() {
			return this.item;
		}
		
		public S getSave() {
			return this.save;
		}
		
	}
	
}