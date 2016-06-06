package de.rincewind.api.handling.events;

import de.rincewind.api.gui.elements.ElementList;

public class ListUnselectEvent<T> extends ElementEvent<ElementList<T>> {

	public ListUnselectEvent(ElementList<T> element) {
		super(element);
	}

}
