package de.rincewind.api.handling.events;

import de.rincewind.api.gui.components.Displayable;
import de.rincewind.api.gui.elements.ElementMap;
import de.rincewind.api.gui.elements.util.Point;

public class MapChangeSelectEvent extends ElementEvent<ElementMap> {

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

	public <T extends Displayable> T getClicked() {
		return this.getElement().getItem(this.getClickedPoint());
	}
	
	public <T extends Displayable> T getClicked(Class<T> cls) {
		return this.getElement().getItem(cls, this.getClickedPoint());
	}

}
