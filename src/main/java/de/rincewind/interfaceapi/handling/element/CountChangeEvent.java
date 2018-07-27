package de.rincewind.interfaceapi.handling.element;

import de.rincewind.interfaceapi.gui.elements.ElementCounter;

public class CountChangeEvent extends ElementValueChangeEvent<ElementCounter> {
	
	private int previousCount;
	
	public CountChangeEvent(ElementCounter element, int previousCount) {
		super(element, element.getCount());
		
		this.previousCount = previousCount;
	}
	
	public int getPreviousCount() {
		return this.previousCount;
	}

}
