package de.rincewind.interfaceapi.gui.windows;

import java.util.function.Consumer;

import de.rincewind.interfaceapi.gui.util.Point;

public interface WindowPointer extends WindowSizeable {
	
	public abstract Consumer<Point> getAction();
	
	public abstract void invert();
	
}
