package de.rincewind.api.gui.windows.abstracts;

import de.rincewind.api.gui.util.Color;
import de.rincewind.api.gui.windows.WindowSizeable;

/**
 * In this window you can set a background color, witch replaces
 * all empty slots.
 * 
 * @author Rincewind34
 * @since 2.3.3
 * 
 * @see WindowSizeable
 * @see WindowActivatable
 * @see Color
 */
public abstract interface WindowColorable extends WindowEditor {
	
	/**
	 * Returns the current set color. The default color is
	 * <code>WindowDefaults#COLOR</code>.
	 * 
	 * @return the current set color
	 */
	public abstract Color getColor();
	
	/**
	 * Sets the color of this window.
	 * 
	 * @param color to set
	 * 
	 * @throws NullPointerException if the color is <code>null</code>
	 */
	public abstract void setColor(Color color);
	
}
