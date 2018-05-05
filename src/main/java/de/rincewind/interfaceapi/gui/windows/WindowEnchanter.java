package de.rincewind.interfaceapi.gui.windows;

import de.rincewind.interfaceapi.exceptions.InvalidSlotException;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowEditor;

/**
 * 
 * @author Rincewind34
 * @since 2.3.3
 */
public abstract interface WindowEnchanter extends WindowEditor {
	
	/**
	 * Sets the enchantment-offer in a specified slot. To update the
	 * slots invoke {@link WindowEnchanter#updateLevels()}.
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
