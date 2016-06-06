package de.rincewind.api.gui.components;

import de.rincewind.api.exceptions.InvalidSizeException;
import de.rincewind.api.gui.elements.abstracts.ElementSizeable;
import de.rincewind.api.gui.elements.util.Point;
import de.rincewind.api.gui.windows.WindowSizeable;

/**
 * This interface is implemented when your are able to change the size of that object.
 * 
 * @author Rincewind34
 * @since 2.3.3
 * 
 * @see ElementSizeable
 * @see WindowSizeable
 */
public interface Sizeable {
	
	/**
	 * Returns the width of this object.
	 * 
	 * @return the width of this object
	 */
	public abstract int getWidth();
	
	/**
	 * Returns the height of this object.
	 * 
	 * @return the height of this object
	 */
	public abstract int getHeight();
	
	/**
	 * Returns <code>true</code> if a specified point is in the frame of the width and
	 * the height and <code>false</code> if not.
	 * 
	 * @param point to check
	 * 
	 * @return <code>true</code> if a specified point is in the frame of the width and
	 * 			the height and <code>false</code> if not
	 * 
	 * @throws NullPointerException if the point is <code>null</code>
	 */
	public abstract boolean isInside(Point point);
	
	/**
	 * Sets the size of this element.
	 * 
	 * @param width to set
	 * @param higth to set
	 * 
	 * @throws InvalidSizeException if the size is invalid for this object
	 */
	public abstract void setSize(int width, int higth);
	
}
