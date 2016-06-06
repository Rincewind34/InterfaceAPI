package de.rincewind.api.gui.components;

import de.rincewind.api.gui.elements.ElementList;
import de.rincewind.api.gui.elements.ElementMultiButton;

/**
 * This interface is implemented when an object can be selected and unselected.
 * 
 * @author Rincewind34
 * @since 2.3.3
 * 
 * @see ElementList
 * @see ElementMultiButton
 */
public interface Selectable {
	
	/**
	 * Selects the object.
	 */
	public abstract void select();
	
	/**
	 * Unselects the object.
	 */
	public abstract void unselect();
	
	/**
	 * Returns <code>true</code> if this object is selected and <code>false</code>
	 * if not.
	 * 
	 * @return <code>true</code> if this object is selected and <code>false</code>
	 * 			if not
	 */
	public abstract boolean isSelected();
	
	//TODO public abstract boolean canSelect();
	
}
