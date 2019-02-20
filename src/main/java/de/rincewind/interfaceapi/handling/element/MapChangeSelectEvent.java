package de.rincewind.interfaceapi.handling.element;

import de.rincewind.interfaceapi.gui.elements.ElementMap;

public class MapChangeSelectEvent extends ElementValueChangeEvent<ElementMap> {

	private int newIndex;

	public MapChangeSelectEvent(ElementMap element, int newIndex) {
		super(element, element.getSelected());

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
