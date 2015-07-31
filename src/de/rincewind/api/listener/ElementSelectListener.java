package de.rincewind.api.listener;

import de.rincewind.api.gui.elements.ElementList;

public interface ElementSelectListener {
	
	/**
	 * Is called, when the user selects an element of a ElementList
	 * @param element The ElementList-object
	 */
	void onSelect(ElementList element);
	
}
