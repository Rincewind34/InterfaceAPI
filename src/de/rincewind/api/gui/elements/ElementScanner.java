package de.rincewind.api.gui.elements;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.util.item.ScanResult;

public class ElementScanner extends ElementSlot {
	
	/**
	 * @param handle The window for this element
	 */
	public ElementScanner(Modifyable handle) {
		super(handle);
		
		super.setBlocked(getX(), getY(), false);
	}
	
	/**
	 * @return The ScanResult for this scan
	 */
	public ScanResult scan() {
		if (super.isEmpty()) {
			return null;
		} else {
			return new ScanResult(getItem());
		}
	}
	
}
