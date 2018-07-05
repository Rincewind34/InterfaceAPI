package de.rincewind.interfaceapi.gui.elements.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.util.Bounds;
import de.rincewind.interfaceapi.gui.util.Point;
import de.rincewind.interfaceapi.gui.util.creators.ElementCreator;
import de.rincewind.interfaceapi.gui.util.creators.ElementCreatorCloseable;
import de.rincewind.interfaceapi.gui.util.creators.ElementCreatorLogging;
import de.rincewind.interfaceplugin.Validate;

public class ElementSet implements Iterable<Element> {

	public static ElementSet wrapCreation(ElementCreator creator, Bounds bounds, BiConsumer<ElementCreator, Bounds> action) {
		return ElementSet.wrapCreation(creator, bounds, action, new HashSet<>());
	}

	public static ElementSet wrapCreation(ElementCreator creator, Bounds bounds, BiConsumer<ElementCreator, Bounds> action, Collection<Element> initial) {
		return ElementSet.wrapCreation(creator, bounds, action, initial, ElementSet::new);
	}

	public static <T extends ElementSet> T wrapCreation(ElementCreator creator, Bounds bounds, BiConsumer<ElementCreator, Bounds> action,
			Function<? super Collection<Element>, T> setCreator) {

		return ElementSet.wrapCreation(creator, bounds, action, new HashSet<>(), setCreator);
	}

	public static <T extends ElementSet> T wrapCreation(ElementCreator creator, Bounds bounds, BiConsumer<ElementCreator, Bounds> action, Collection<Element> initial,
			Function<? super Collection<Element>, T> setCreator) {

		Validate.notNull(creator, "The creator cannot be null");
		Validate.notNull(action, "The action cannot be null");
		Validate.notNull(initial, "The initial collection cannot be null");

		if (!initial.isEmpty()) {
			throw new IllegalArgumentException("The initial collection cannot be filled!");
		}

		ElementCreatorLogging logger = new ElementCreatorLogging(creator);
		ElementCreatorCloseable closeableCreator = new ElementCreatorCloseable(logger);

		action.accept(closeableCreator, bounds);
		closeableCreator.close();

		return setCreator.apply(Collections.unmodifiableCollection(logger.getLog()));
	}

	private final Collection<Element> elements;

	public ElementSet() {
		this(new HashSet<>());
	}

	public ElementSet(Collection<Element> elements) {
		Validate.notNull(elements, "The collection cannot be null");

		this.elements = elements;
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
