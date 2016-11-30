package de.rincewind.api.handling.element;

import de.rincewind.api.gui.elements.abstracts.Element;
import de.rincewind.api.handling.Event;

public class ElementEvent<T extends Element> extends Event<T> {
	
	private T element;
	
	public ElementEvent(T element) {
		this.element = element;
	}
	
	public T getElement() {
		return this.element;
	}
	
}
