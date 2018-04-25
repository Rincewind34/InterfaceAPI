package de.rincewind.interfaceapi.gui.windows.abstracts;

import java.util.Set;
import java.util.function.Consumer;

import de.rincewind.interfaceapi.gui.elements.util.Point;

/**
 * This window is InventoryGUI. If you change the name or 
 * something like this, the inventory will be automatically
 * recreated and if this window already has a user, the inventory
 * will be opened completely new.
 * 
 * @author Rincewind34
 * @since 2.3.3
 * 
 * @see WindowEditor
 */
public abstract interface WindowContainer extends WindowNameable {
	
	public abstract Set<Point> getPoints();
	
	public default void iterate(Consumer<Point> action) {
		this.getPoints().forEach(action);
	}
	
	public default boolean isInside(Point point) {
		return this.getPoints().contains(point);
	}
	
}
