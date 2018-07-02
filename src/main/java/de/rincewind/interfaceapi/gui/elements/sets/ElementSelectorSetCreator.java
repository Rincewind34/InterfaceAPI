package de.rincewind.interfaceapi.gui.elements.sets;

import java.util.Collection;

import de.rincewind.interfaceapi.gui.elements.abstracts.Element;

public abstract class ElementSelectorSetCreator<T> extends ElementSetCreator {
	
	@Override
	protected abstract ElementSelectorSet<T> newElementSet(Collection<Element> collection);
	
}
