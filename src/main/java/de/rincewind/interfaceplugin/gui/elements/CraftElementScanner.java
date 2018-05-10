package de.rincewind.interfaceplugin.gui.elements;

import de.rincewind.interfaceapi.gui.elements.ElementScanner;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowEditor;
import de.rincewind.interfaceapi.item.ScanResult;
import de.rincewind.interfaceplugin.gui.elements.abstracts.CraftElementSlot;

public class CraftElementScanner extends CraftElementSlot implements ElementScanner {
	
	public CraftElementScanner(WindowEditor handle) {
		super(handle);
		
		this.getBlocker().unlock();
	}
	
	@Override
	public int getMaxStackSize() {
		return 64; // TODO
	}
	
	@Override
	public ScanResult scan() {
		if (this.isEmpty()) {
			return null;
		} else {
			return new ScanResult(this.getCurrentContent());
		}
	}

}
