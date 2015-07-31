package de.rincewind.api.gui.components;

public abstract interface Locatable {
	
	/**
	 * @return The x-coordinate
	 */
	public abstract int getX();
	
	/**
	 * 
	 * @return The y-coordinate
	 */
	public abstract int getY();
	
	/**
	 * 
	 * Set the position in a window to P(x|y)
	 * 
	 * @param x The x-coordinate
	 * @param y die y-coordinate
	 */
	public abstract void setPosition(int x, int y);
	
}
