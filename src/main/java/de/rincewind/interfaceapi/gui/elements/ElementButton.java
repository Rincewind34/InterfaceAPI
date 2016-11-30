package de.rincewind.interfaceapi.gui.elements;

import de.rincewind.interfaceapi.gui.elements.abstracts.ElementDisplayable;
import de.rincewind.interfaceapi.gui.elements.util.Elements.ElementButtonExtendable;
import de.rincewind.interfaceapi.handling.element.ButtonPressEvent;

/**
 * In this element you can add a listener to run an action on
 * clicking on this element. on that click, the {@link ButtonPressEvent} will be fired.
 * 
 * @author Rincewind34
 * @since 2.3.3
 * 
 * @see ElementButtonExtendable
 * @see ElementBrick
 * @see ElementMultiButton
 * @see ElementCounter
 * @see ElementSwitcher
 */
public interface ElementButton extends ElementDisplayable {
	
}
