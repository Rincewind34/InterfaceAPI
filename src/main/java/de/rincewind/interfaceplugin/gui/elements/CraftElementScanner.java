package de.rincewind.interfaceplugin.gui.elements;

import de.rincewind.interfaceapi.gui.components.Modifyable;
import de.rincewind.interfaceapi.gui.elements.ElementScanner;
import de.rincewind.interfaceapi.item.ScanResult;
import de.rincewind.interfaceplugin.gui.elements.abstracts.CraftElementSlot;

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
