package de.rincewind.interfaceplugin.gui.elements;

import de.rincewind.interfaceapi.gui.elements.ElementContentSlot;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowEditor;
import de.rincewind.interfaceplugin.gui.elements.abstracts.CraftElementSlot;

public class CraftElementContentSlot extends CraftElementSlot implements ElementContentSlot {

	public CraftElementContentSlot(WindowEditor handle) {
		super(handle);
	}

	@Override
	public int getMaxStackSize() {
		return 64; // TODO (#1028)
	}
	
}
