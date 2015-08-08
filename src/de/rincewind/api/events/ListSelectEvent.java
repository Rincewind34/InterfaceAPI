package de.rincewind.api.events;

import de.rincewind.api.gui.elements.ElementList;

public class ListSelectEvent extends DefaultElementEvent<ElementList> {

	public ListSelectEvent(ElementList element) {
		super(element);
	}

}
