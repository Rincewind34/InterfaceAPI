package de.rincewind.api.handling.events;

import de.rincewind.api.gui.elements.ElementSelector;

public class ItemSelectEvent extends ElementEvent<ElementSelector> {

	public ItemSelectEvent(ElementSelector element) {
		super(element);
	}

}
