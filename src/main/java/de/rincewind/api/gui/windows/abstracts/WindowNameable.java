package de.rincewind.api.gui.windows.abstracts;

/**
 * In this window you are able to set the name of this window.
 * 
 * @author Rincewind34
 * @since 2.3.3
 * 
 * @see WindowContainer
 */
public abstract interface WindowNameable extends Window {
	
	/**
	 * Returns the current name of the window.
	 * The default name of a window is {@value WindowDefaults#NAME}.
	 * 
	 * @return the current name of the window.
	 */
	public abstract String getName();
	
	/**
	 * Sets the name of this window. If the new name
	 * is equals the old name, this method will do nothing.
	 * 
	 * @param name to set
	 * 
	 * @throws NullPointerException if the name is null.
	 */
	public abstract void setName(String name);
	
}
