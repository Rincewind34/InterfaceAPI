package de.rincewind.api.gui.components;

import de.rincewind.api.gui.Color;

public abstract interface Colorable {
	
	/**
	 * 
	 * Sets a glasspane to all free cells of a window
	 * 
	 * @param color The color for the glasspanes
	 */
	public abstract boolean setColor(Color color);
	
	/**
	 * @return The color of the glasspanes to fill
	 */
	public abstract Color getColor();
	
	
	
}
