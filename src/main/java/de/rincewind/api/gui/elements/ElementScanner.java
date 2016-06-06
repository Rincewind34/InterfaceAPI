package de.rincewind.api.gui.elements;

import de.rincewind.api.gui.elements.abstracts.ElementSlot;
import de.rincewind.api.gui.elements.util.ClickBlocker;
import de.rincewind.api.gui.elements.util.Elements.ElementScannerExtendable;
import de.rincewind.api.item.ScanResult;

/**
 * The element allows you to scan the item laying in the slot.
 * In the constructor the {@link ClickBlocker} of this element
 * will be automatically unlocked with {@link ClickBlocker#unlock()}.
 * 
 * @author Rincewind34
 * @since 2.3.3
 * 
 * @see ElementScannerExtendable
 */
public abstract interface ElementScanner extends ElementSlot {
	
	/**
	 * Scans the item laying in the slot and returns the {@link ScanResult}.
	 * If the slot is empty, this method will return <code>null</code>.
	 * 
	 * @return the {@link ScanResult}
	 */
	public abstract ScanResult scan();
	
}
