package de.rincewind.plugin.gui.windows.abstracts;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.rincewind.api.exceptions.APIException;
import de.rincewind.api.gui.elements.abstracts.Element;
import de.rincewind.api.gui.elements.util.ElementCreator;
import de.rincewind.api.gui.elements.util.Icon;
import de.rincewind.api.gui.elements.util.Point;
import de.rincewind.api.gui.windows.abstracts.WindowEditor;
import de.rincewind.api.handling.events.ElementInteractEvent;
import de.rincewind.api.handling.events.WindowClickEvent;
import de.rincewind.plugin.gui.elements.abstracts.CraftElement;
import lib.securebit.ReflectionUtil;
import lib.securebit.Validate;

public abstract class CraftWindowEditor extends CraftWindowContainer implements WindowEditor {

	private List<Element> elements;

	private Map<Element, List<Point>> cache;

	private ElementCreator creator;

	public CraftWindowEditor() {
		super();

		this.elements = new ArrayList<>();
		this.cache = new HashMap<>();
		this.creator = new ElementCreator(this);

		this.getEventManager().registerListener(WindowClickEvent.class, (event) -> {
			if (event.isInInterface()) {
				Element element = this.getVisibleElementAt(event.getInterfacePoint());

				if (element == null) {
					return;
				}

				if (!element.getBlocker().allows(event.getAction())) {
					event.cancleInteraction();
				}

				element.getEventManager().callEvent(ElementInteractEvent.class, new ElementInteractEvent(element, this.getUser(),
						event.getInterfacePoint().subtract(element.getPoint()), event.getAction(), event.isShiftClick(), event.isLeftClick()));
			}
		}).addAfter();
	}

	@Override
	public List<Element> getElements() {
		return Collections.unmodifiableList(this.elements);
	}

	@Override
	public void addElement(Element element) {
		Validate.notNull(element, "The element cannot be null!");

		if (((CraftElement) element).getId() != -1) {
			throw new APIException("The element is already added in a Window!");
		}

		List<Integer> ids = new ArrayList<Integer>();

		for (Element target : this.elements) {
			ids.add(((CraftElement) target).getId());
		}

		int id = 0;

		while (ids.contains(id)) {
			id++; // Sucht eine freie id von 0 aufwaerts
		}

		this.elements.add(element);
		this.injectId(element, id);

		this.renderElement(element);
	}

	@Override
	public List<Element> getElementsAt(Point point) {
		Validate.notNull(point, "The point cannot be null!");

		List<Element> elements = new ArrayList<>();

		for (Element element : this.elements) {
			if (element.isInside(point)) {
				elements.add(element);
			}
		}

		return elements;
	}

	@Override
	public Element getVisibleElementAt(Point point) {
		Validate.notNull(point, "The point cannot be null!");

		for (Element element : this.getElementsAt(point)) {
			if (element.isVisible()) {
				return element;
			}
		}

		return null;
	}

	@Override
	public void renderAll() {
		for (Element element : this.elements) {
			this.renderElement(element);
		}
	}

	@Override
	public void renderElement(Element element) {
		Validate.notNull(element, "The element cannot be null!");

		if (!this.elements.contains(element)) {
			throw new APIException("The element is not added in this Window!");
		}

		List<Point> points = this.cache.containsKey(element) ? this.cache.get(element) : new ArrayList<>();
		element.iterate((point) -> {
			Point trans = point.add(element.getPoint());

			// if (!craftElement.isVisible()) {
			// if (this.hasVisibleElementAt(trans)) {
			// this.readItemsFrom(this.getVisibleElementAt(trans));
			// return;
			// }
			// }

			if ( /* !this.hasVisibleElementAt(trans) || */ this.getVisibleElementAt(
					trans) == element) { /* Vlt überlappen sich Elemente */
				// this.setItem(trans, craftElement.getItemAt(point));
				this.update(trans);
			}

			points.remove(trans);
		});

		if (!points.isEmpty()) {
			this.update(points);
		}

		this.cache.put(element, element.getPoints());
	}

	@Override
	public ElementCreator elementCreator() {
		return this.creator;
	}

	@Override
	public void clearElements() {
		for (Element element : this.elements) {
			this.removeElement(element);
		}
	}

	@Override
	public void removeElement(Element element) {
		Validate.notNull(element, "The element cannot be null!");

		if (!this.elements.contains(element)) {
			throw new APIException("The element is not added in this Window!");
		}

		this.injectId(element, -1);
		this.update(this.cache.get(element));

		this.cache.remove(element);
		this.elements.remove(element);
	}

	@Override
	public void priorize(Element element) {
		Validate.notNull(element, "The element cannot be null!");

		if (!this.elements.contains(element)) {
			throw new APIException("The element is not added in this Window!");
		}

		List<Element> newList = new ArrayList<>();
		newList.add(element);

		for (Element target : this.elements) {
			if (!target.equals(element)) {
				newList.add(target);
			}
		}

		this.elements = newList;
		this.update();
	}

	@Override
	public boolean hasVisibleElementAt(Point point) {
		Validate.notNull(point, "The point cannot be null!");

		return this.getVisibleElementAt(point) != null;
	}

	@Override
	public Icon getIcon(Point point) {
		Element element = this.getVisibleElementAt(point);

		if (element == null) {
			return null;
		} else {
			return element.getIcon(point.subtract(element.getPoint()));
		}
	}

	private void injectId(Element element, int id) {
		Validate.notNull(element, "The element cannot be null!");

		Field fieldId = ReflectionUtil.getDeclaredField(CraftElement.class, "id");
		ReflectionUtil.setValue(fieldId, element, id);
	}

}
