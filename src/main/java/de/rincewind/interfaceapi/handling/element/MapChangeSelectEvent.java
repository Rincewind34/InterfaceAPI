package de.rincewind.interfaceapi.handling.element;

import de.rincewind.interfaceapi.gui.elements.ElementMap;
import de.rincewind.interfaceapi.gui.util.Point;

public class MapChangeSelectEvent extends ElementValueChangeEvent<ElementMap> {

	private int newIndex;

	public MapChangeSelectEvent(ElementMap element, int newIndex) {
		super(element);

		this.newIndex = newIndex;
	}
	
	public boolean wasUnselect() {
		return this.newIndex == -1;
	}
	
	public int getNewIndex() {
		return this.newIndex;
	}

	public Point getClickedPoint() {
		return this.getElement().getPoint(this.newIndex);
	}

	public <T> T getClicked() {
		return this.getElement().get(this.getClickedPoint());
	}
	
	public <T> T getClicked(Class<T> cls) {
		return this.getElement().get(cls, this.getClickedPoint());
	}

}
