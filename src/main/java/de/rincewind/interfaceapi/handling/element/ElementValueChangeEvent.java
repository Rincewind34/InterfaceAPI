package de.rincewind.interfaceapi.handling.element;

import de.rincewind.interfaceapi.gui.elements.abstracts.Element;

public class ElementValueChangeEvent<T extends Element> extends ElementEvent<T> {

	public ElementValueChangeEvent(T element) {
		super(element);
	}

}
