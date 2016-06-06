package de.rincewind.api.handling.events;

import de.rincewind.api.gui.elements.abstracts.Element;

public class ElementEvent<T extends Element> extends Event<T> {
	
	private T element;
	
	public ElementEvent(T element) {
		this.element = element;
	}
	
	public T getElement() {
		return this.element;
	}
	
}
