package de.rincewind.interfaceplugin.gui.elements;

import de.rincewind.interfaceapi.gui.elements.ElementItem;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowEditor;
import de.rincewind.interfaceplugin.gui.elements.abstracts.CraftElementDisplayable;

public class CraftElementItem extends CraftElementDisplayable implements ElementItem {
	
	public CraftElementItem(WindowEditor handle) {
		super(handle);

		this.getComponent(Element.WIDTH).setEnabled(true);
		this.getComponent(Element.HEIGHT).setEnabled(true);
	}

}
