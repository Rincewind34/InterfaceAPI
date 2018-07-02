package de.rincewind.interfaceapi.gui.elements.sets;

import java.util.Collection;

import de.rincewind.interfaceapi.gui.elements.abstracts.Element;

public abstract class ElementSelectorSet<T> extends ElementSet {
	
	public ElementSelectorSet(Collection<Element> elements) {
		super(elements);
	}

	public abstract T getSelectedValue();
	
}
