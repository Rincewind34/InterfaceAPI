package de.rincewind.interfaceapi.gui.windows;

import de.rincewind.interfaceapi.gui.windows.abstracts.WindowActivatable;

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
 * @author Rincewind34
 * @since 2.3.3
 */
public interface WindowBrewing extends WindowActivatable {

}
