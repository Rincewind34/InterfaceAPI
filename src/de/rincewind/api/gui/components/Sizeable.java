package de.rincewind.api.gui.components;

public interface Sizeable {
	
	/**
	 * 
	 * @return the width
	 */
	public abstract int getWidth();

	/**
	 * 
	 * @return the heigth
	 */
	public abstract int getHeight();
	
	/**
	 * 
	 * @param x The x-coordinate
	 * @param y The y-coordinate
	 * @return If the Location P(x;y) is inside of the sizeable component
	 */
	public abstract boolean isInside(int x, int y);
	
	/**
	 * 
	 * Sets the size
	 * 
	 * @param width The target width
	 * @param higth The target heigth
	 */
	public abstract void setSize(int width, int higth);
	
}
