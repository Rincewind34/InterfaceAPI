package de.rincewind.api.gui.windows;

import de.rincewind.api.gui.components.Sizeable;
import de.rincewind.api.gui.windows.abstracts.WindowColorable;
import de.rincewind.api.gui.windows.util.Windows;

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
public interface WindowSizeable extends WindowColorable, Sizeable {
	
	/**
	 * Returns <code>true</code> if the size is valid and <code>false</code> if not.
	 * 
	 * @param width to check
	 * @param height to check
	 * 
	 * @return <code>true</code> if the size is valid and <code>false</code> if not
	 */
	public abstract boolean checkSize(int width, int height);
	
}
