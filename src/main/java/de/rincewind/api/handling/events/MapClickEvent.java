package de.rincewind.api.handling.events;

import de.rincewind.api.gui.elements.ElementMap;
import de.rincewind.api.gui.elements.ElementMap.MapItem;
import de.rincewind.api.gui.elements.util.Point;

public class MapClickEvent extends ElementEvent<ElementMap<?>> {
	
	private Point clickedPoint;
	private MapItem<?> clickedItem;
	
	public MapClickEvent(ElementMap<?> element, Point clickedPoint, MapItem<?> clickedItem) {
		super(element);
		
		this.clickedPoint = clickedPoint;
		this.clickedItem = clickedItem;
	}
	
	public Point getClickedPoint() {
		return this.clickedPoint;
	}
	
	public MapItem<?> getClickedItem() {
		return this.clickedItem;
	}
	
}
