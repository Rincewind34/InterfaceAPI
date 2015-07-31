package de.rincewind.api.gui.components;

public abstract interface Nameable {
	
	/**
	 * 
	 * @return The current name
	 */
	public abstract String getName();
	
	/**
	 * 
	 * Sets the name
	 * 
	 * @param name The new name
	 */
	public abstract boolean setName(String name);
	
}
