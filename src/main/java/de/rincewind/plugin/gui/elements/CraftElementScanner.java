package de.rincewind.plugin.gui.elements;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.ElementScanner;
import de.rincewind.api.item.ScanResult;
import de.rincewind.plugin.gui.elements.abstracts.CraftElementSlot;

public class CraftElementScanner extends CraftElementSlot implements ElementScanner {
	
	public CraftElementScanner(Modifyable handle) {
		super(handle);
		
		this.getBlocker().unlock();
	}
	
	@Override
	public ScanResult scan() {
		if (this.isEmpty()) {
			return null;
		} else {
			return new ScanResult(this.getContent());
		}
	}

}
