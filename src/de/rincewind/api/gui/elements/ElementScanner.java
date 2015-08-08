package de.rincewind.api.gui.elements;

import de.rincewind.api.gui.elements.abstracts.ElementSlot;
import de.rincewind.util.item.ScanResult;

public abstract interface ElementScanner extends ElementSlot {
	
	public abstract ScanResult scan();
	
}
