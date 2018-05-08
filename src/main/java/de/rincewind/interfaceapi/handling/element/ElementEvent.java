package de.rincewind.interfaceapi.handling.element;

import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.handling.Event;

public class ElementEvent<T extends Element> extends Event {
	
	private T element;
	
	public ElementEvent(T element) {
		this.element = element;
	}
	
	public T getElement() {
		return this.element;
	}
	
}
