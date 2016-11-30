package de.rincewind.api.gui.windows;

import de.rincewind.api.exceptions.InvalidSlotException;
import de.rincewind.api.gui.windows.abstracts.WindowEditor;
import de.rincewind.api.gui.windows.util.Windows.WindowEnchanterExtendable;

/**
 * This window is a BrewingInventory. To set elements in this window, you
 * can use four slots:
 * <ul>
 *   <li>Slot 0: <code>new Point(0, 1)</code> (left potion-slot)</li>
 *   <li>Slot 1: <code>new Point(1, 1)</code> (middle potion-slot)</li>
 *   <li>Slot 2: <code>new Point(2, 1)</code> (right potion-slot)</li>
 *   <li>Slot 3: <code>new Point(1, 0)</code> (catalyst)</li>
 * </ul>
 * 
 * Specially in this window you can set the enchantment-offers. The slot
 * at the top has the ID 0. That one in the middle 1, and ID 2 on the bottom.
 * 
 * @author Rincewind34
 * @since 2.3.3
 * 
 * @see WindowEnchanterExtendable
 */
public abstract interface WindowEnchanter extends WindowEditor {
	
	/**
	 * Sets the enchantment-offer in a specified slot. To update the
	 * slots invoke {@link WindowEnchanter#update()}.
	 * 
	 * @param slot to change
	 * @param lvl to set
	 * 
	 * @throws InvalidSlotException if the slot is smaller than 0 or greater than 2
	 */
	public abstract void setOffer(int slot, int lvl);
	
	/**
	 * Updates the offers to the user. If the window does not have an
	 * user, this method will do nothing.
	 */
	public abstract void updateLevels();
	
	/**
	 * Returns the currently set offer. The default-offer for all slots
	 * is 0.
	 * 
	 * @param slot to get
	 * 
	 * @return the currently set offer
	 */
	public abstract int getOffer(int slot);
	
}
