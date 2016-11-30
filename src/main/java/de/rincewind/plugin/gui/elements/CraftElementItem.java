package de.rincewind.plugin.gui.elements;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.ElementItem;
import de.rincewind.api.gui.elements.abstracts.Element;
import de.rincewind.plugin.gui.elements.abstracts.CraftElementDisplayable;

public class CraftElementItem extends CraftElementDisplayable implements ElementItem {
	
	public CraftElementItem(Modifyable handle) {
		super(handle);

		this.getComponent(Element.WIDTH).setEnabled(true);
		this.getComponent(Element.HEIGHT).setEnabled(true);
	}

}
