package de.rincewind.api.events;

import de.rincewind.api.gui.elements.ElementList;

public class ListUnselectEvent extends DefaultElementEvent<ElementList> {

	public ListUnselectEvent(ElementList element) {
		super(element);
	}

}
