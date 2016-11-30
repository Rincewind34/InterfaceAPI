package de.rincewind.api.gui.elements;

import de.rincewind.api.gui.elements.abstracts.ElementDisplayable;
import de.rincewind.api.gui.elements.util.Elements.ElementButtonExtendable;
import de.rincewind.api.handling.element.ButtonPressEvent;

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
