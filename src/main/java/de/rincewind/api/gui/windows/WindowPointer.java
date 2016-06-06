package de.rincewind.api.gui.windows;

import java.util.function.Consumer;

import de.rincewind.api.gui.elements.util.Point;

public interface WindowPointer extends WindowSizeable {
	
	public abstract Consumer<Point> getAction();
	
	public abstract void invert();
	
}
