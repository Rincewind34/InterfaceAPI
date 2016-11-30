package de.rincewind.interfaceapi.gui.windows;

import de.rincewind.interfaceapi.exceptions.InvalidSizeException;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowColorable;
import de.rincewind.interfaceapi.gui.windows.util.Windows;

/**
 * This window is a ChestInventory or something like this, depends on this size.
 * The valid sizes are:
 * <ul>
 *   <li>3 x 3 (Dropper/Dispenser)</li>
 *   <li>5 x 1 (Hopper)</li>
 *   <li>9 x (i e {1, 2, 3, 4, 5, 6})</li>
 * </ul>
 * 
 * To set elements in this window, this point P(0, 0) is in the upper right corner.
 * 
 * @author Rincewind34
 * @since 2.3.3
 * 
 * @see Windows.WindowSizeableExtendable
 */
public interface WindowSizeable extends WindowColorable {
	
	/**
	 * Returns <code>true</code> if the size is valid and <code>false</code> if not.
	 * 
	 * @param width to check
	 * @param height to check
	 * 
	 * @return <code>true</code> if the size is valid and <code>false</code> if not
	 */
	public abstract boolean checkSize(int width, int height);
	
	/**
	 * Returns the width of this object.
	 * 
	 * @return the width of this object
	 */
	public abstract int getWidth();
	
	/**
	 * Returns the height of this object.
	 * 
	 * @return the height of this object
	 */
	public abstract int getHeight();
	
	/**
	 * Sets the size of this element.
	 * 
	 * @param width to set
	 * @param higth to set
	 * 
	 * @throws InvalidSizeException if the size is invalid for this object
	 */
	public abstract void setSize(int width, int higth);
	
}
