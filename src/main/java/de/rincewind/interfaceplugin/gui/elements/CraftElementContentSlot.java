package de.rincewind.interfaceplugin.gui.elements;

import de.rincewind.interfaceapi.gui.components.Modifyable;
import de.rincewind.interfaceapi.gui.elements.ElementContentSlot;
import de.rincewind.interfaceplugin.gui.elements.abstracts.CraftElementSlot;

public class CraftElementContentSlot extends CraftElementSlot implements ElementContentSlot {

	public CraftElementContentSlot(Modifyable handle) {
		super(handle);
	}
	
}
