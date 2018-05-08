package de.rincewind.interfaceapi.handling.element;

import de.rincewind.interfaceapi.gui.elements.ElementList;

public class ListChangeSelectEvent extends ElementValueChangeEvent<ElementList> {
	
	private int newIndex;
	
	public ListChangeSelectEvent(ElementList element, int newIndex) {
		super(element);
		
		this.newIndex = newIndex;
	}
	
	public boolean wasUnselect() {
		return this.newIndex == -1;
	}
	
	public int getNewIndex() {
		return this.newIndex;
	}

	public <T> T getClicked() {
		return this.getElement().get(this.newIndex);
	}
	
	public <T> T getClicked(Class<T> cls) {
		return this.getElement().get(cls, this.newIndex);
	}

}
