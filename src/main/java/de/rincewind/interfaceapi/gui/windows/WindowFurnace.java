package de.rincewind.interfaceapi.gui.windows;

import de.rincewind.interfaceapi.gui.windows.abstracts.WindowActivatable;

/**
 * This window is a FurnaceInventory. To set elements in this window, you
 * can use three slots:
 * <ul>
 *   <li>Slot 0: <code>new Point(0, 0)</code> (input-slot)</li>
 *   <li>Slot 1: <code>new Point(0, 2)</code> (fuel-slot)</li>
 *   <li>Slot 2: <code>new Point(1, 1)</code> (output-slot)</li>
 * </ul>
 * 
 * @author Rincewind34
 * @since 2.3.3
 * 
 * @see Windows.WindowFurnaceExtendable
 */
public interface WindowFurnace extends WindowActivatable {
	
}
