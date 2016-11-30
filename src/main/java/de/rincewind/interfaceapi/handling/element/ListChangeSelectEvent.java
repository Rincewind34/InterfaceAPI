package de.rincewind.interfaceapi.handling.element;

import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.elements.ElementList;

public class ListChangeSelectEvent extends ElementEvent<ElementList> {
	
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

	public <T extends Displayable> T getClicked() {
		return this.getElement().getItem(this.newIndex);
	}
	
	public <T extends Displayable> T getClicked(Class<T> cls) {
		return this.getElement().getItem(cls, this.newIndex);
	}

}
