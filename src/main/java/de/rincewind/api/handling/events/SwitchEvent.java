package de.rincewind.api.handling.events;

import de.rincewind.api.gui.elements.ElementSwitcher;

public class SwitchEvent extends ElementEvent<ElementSwitcher<?>> {

	public SwitchEvent(ElementSwitcher<?> element) {
		super(element);
	}

}
