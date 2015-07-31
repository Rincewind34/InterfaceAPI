package de.rincewind.api.listener;

import de.rincewind.api.gui.elements.ElementList;

public interface ElementUnselectListener {
	
	/**
	 * Is called, when the user unselects an element of a ElementList
	 * @param element The ElementList-object
	 */
	void onUnselect(ElementList element);
	
}
