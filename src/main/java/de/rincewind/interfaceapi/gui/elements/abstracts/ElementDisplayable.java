package de.rincewind.interfaceapi.gui.elements.abstracts;

import java.util.function.UnaryOperator;

import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.components.DisplayableDisabled;
import de.rincewind.interfaceapi.gui.elements.util.Icon;

public interface ElementDisplayable extends Element, Displayable, DisplayableDisabled {
	
	public abstract void setIcon(Displayable icon);

	/**
	 * Sets the disabled icon using a modified version of the currently set
	 * enabled icon.<br>
	 * The modifier won't be cached. As soon as the enabled icon changes, this
	 * method need be invoked again to change the disabled icon too.
	 * 
	 * @param modifier
	 *            The function modifying the current enabled icon
	 */
	public abstract void setDisabledIconModified(UnaryOperator<Icon> modifier);
	
}
