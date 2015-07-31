package de.rincewind.api.gui.components;

public abstract interface Activatable {
	
	/**
	 * Activates this element
	 */
	public abstract void enable();

	/**
	 * Disables this component
	 */
	public abstract void disable();
	
	/**
	 * Sets the enable of this component
	 */
	public abstract void setEnabled(boolean enable);
	
	/**
	 * 
	 * @return If this component is active
	 */
	public abstract boolean isEnabled();
	
}
