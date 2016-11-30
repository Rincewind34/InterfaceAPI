package de.rincewind.api.handling.element;

import de.rincewind.api.gui.components.Displayable;
import de.rincewind.api.gui.elements.ElementList;

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
