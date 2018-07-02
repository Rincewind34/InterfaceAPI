package de.rincewind.interfaceapi.gui.elements.sets;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceplugin.Validate;

public class ElementSet implements Iterable<Element> {
	
	private final Collection<Element> elements;
	
	public ElementSet(Collection<Element> elements) {
		Validate.notNull(elements, "The collection cannot be null");
		
		this.elements = Collections.unmodifiableCollection(elements);
	}
	
	@Override
	public Iterator<Element> iterator() {
		return this.elements.iterator();
	}
	
	public void updateElements() {
		for (Element element : this) {
			element.update();
		}
	}
	
	public void moveElements(Point offset) {
		this.moveElements(offset.getX(), offset.getY());
	}
	
	public void moveElements(int offsetX, int offsetY) {
		for (Element element : this) {
			element.setPoint(element.getPoint().add(offsetX, offsetY));
		}
	}
	
	public void setElementsEnabled(boolean value) {
		for (Element element : this) {
			if (element.isElementComponentEnabled(Element.ENABLED)) {
				element.setComponentValue(Element.ENABLED, value);
			}
		}
	}
	
	public void setElementsVisible(boolean value) {
		for (Element element : this) {
			element.setVisible(value);
		}
	}
	
	public Collection<Element> getElements() {
		return this.elements;
	}
	
}
