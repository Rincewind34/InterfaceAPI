package de.rincewind.api.events;

import de.rincewind.api.gui.elements.abstracts.Element;

public class DefaultElementEvent<T extends Element> {
	
	private T element;
	
	public DefaultElementEvent(T element) {
		this.element = element;
	}
	
	public T getElement() {
		return this.element;
	}
	
}
