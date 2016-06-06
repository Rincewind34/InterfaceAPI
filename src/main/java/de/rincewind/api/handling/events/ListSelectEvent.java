package de.rincewind.api.handling.events;

import de.rincewind.api.gui.elements.ElementList;

public class ListSelectEvent<T> extends ElementEvent<ElementList<T>> {

	public ListSelectEvent(ElementList<T> element) {
		super(element);
	}

}
