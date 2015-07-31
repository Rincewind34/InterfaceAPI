package de.rincewind.api.gui.elements;

import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.components.Activatable;
import de.rincewind.api.gui.components.Locatable;
import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.plugin.gui.CraftElement;

public abstract interface Element extends Locatable, Activatable {
	
	/**
	 * 
	 * @return If this element is visible
	 */
	public abstract boolean isVisible();
	
	/**
	 * 
	 * @param x The x-coordinate
	 * @param y die y-coordinate
	 * @return If this element has an item at the location P(x|y)
	 */
	public abstract boolean hasItemAt(int x, int y);
	
	/**
	 * 
	 * @param element The element to compare with this
	 * @return If this element is the same as an other element. Do not use this method to compare two Elements in diffrent windows
	 */
	public abstract boolean isSimilar(Element element);
	
	/**
	 * 
	 * @param x The x-coordinate
	 * @param y The y-coordinate
	 * @return If at the Location
	 */
	public abstract boolean isLocationBlocked(int x, int y);
	
	/**
	 * 
	 * @param x  The x-coordinate
	 * @param y The y-coordinate
	 * @param action The bukkit InventoryAction
	 * @return If an action is blocked at this location P(x|y)
	 */
	public abstract boolean isPointBlocked(int x, int y, InventoryAction action);
	
	/**
	 * @return The icon, which is visibile, when the element is enabled
	 */
	public abstract ItemStack getIcon();
	
	/**
	 * @return The icon, which is visible, when the element is disabled
	 */
	public abstract ItemStack getDisabledIcon();
	
	/**
	 * 
	 * @param x The x-coordinate
	 * @param y The y-coordinate
	 * @return The item, which is located in this element at the Point P(x|y)
	 */
	public abstract ItemStack getItemAt(int x, int y);
	
	/**
	 * Executed, when a window adds this element.
	 * 
	 * @param window The specified window
	 */
	public abstract void onAdd();
	
	/**
	 * Set a new icon for this element.
	 * 
	 * @param icon The new icon for this element
	 */
	public abstract void setIcon(ItemStack icon);
	
	/**
	 * 
	 * Set a new icon for this element, which is visible, when this element is disabled.
	 * 
	 * @param icon The new disabled icon for this element
	 */
	public abstract void setDisabledIcon(ItemStack icon);
	
	/**
	 * 
	 * Shows/Hides this element
	 * 
	 * @param visible If true, this element will be shown. Else, this element is hidden, but exists.
	 */
	public abstract void setVisible(boolean visible);
	
	/**
	 * Blocks / Enables all InventoryActions for the slot at Point P(x|y)
	 * All previous settings will be overriden
	 * 
	 * @param x The x-coordinate
	 * @param y The y-coordinate
	 * @param toBlock Set to true, if you want to block all InventoryActions; else false.
	 */
	public abstract void setBlocked(int x, int y, boolean toBlock);
	
	/**
	 * Blocks an specified InventoryAction for the slot at Point P(x|y)
	 * 
	 * @param x The y-coordinate
	 * @param y The x-coordinate
	 * @param action The InventoryAction to set blocked
	 */
	public abstract void setBlocked(int x, int y, InventoryAction action);
	
//	/**
//	 * @return A kopie of this element without id
//	 */
//	Element clone();
	
	public static class ElementExtendable extends CraftElement {

		/**
		 * @param handle The window for this element
		 */
		public ElementExtendable(Modifyable handle) {
			super(handle);
		}

		@Override
		public void onAdd() {
			
		}

	}
}
