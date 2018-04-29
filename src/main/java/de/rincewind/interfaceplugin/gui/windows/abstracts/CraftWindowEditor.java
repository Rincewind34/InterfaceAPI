package de.rincewind.interfaceplugin.gui.windows.abstracts;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import de.rincewind.interfaceapi.exceptions.ElementEditorException;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.ElementCreator;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowEditor;
import de.rincewind.interfaceapi.handling.element.ElementInteractEvent;
import de.rincewind.interfaceapi.handling.window.WindowClickEvent;
import de.rincewind.interfaceplugin.ReflectionUtil;
import de.rincewind.interfaceplugin.Validate;
import de.rincewind.interfaceplugin.gui.elements.abstracts.CraftElement;

public abstract class CraftWindowEditor extends CraftWindowContainer implements WindowEditor {

	private ElementCreator creator;

	private List<Element> elements;

	public CraftWindowEditor() {
		this.elements = new ArrayList<>();
		this.creator = new ElementCreator(this);

		this.getEventManager().registerListener(WindowClickEvent.class, (event) -> {
			if (event.isInInterface()) {
				Element element = this.getVisibleElementAt(event.getInterfacePoint());

				if (element == null) {
					event.cancelInteraction();
					return;
				}

				if (!element.getBlocker().allows(event.getAction())) {
					event.cancelInteraction();
				}

				element.getEventManager().callEvent(ElementInteractEvent.class, new ElementInteractEvent(element, this.getUser(),
						event.getInterfacePoint().subtract(element.getPoint()), event.getType(), event.getItem()));
			}
		}).addAfter();
	}

	@Override
	public void addElement(Element element) {
		Validate.notNull(element, "The element cannot be null!");

		if (((CraftElement) element).getId() != -1) {
			throw new ElementEditorException("The element is already added in a Window!");
		}

		List<Integer> ids = new ArrayList<Integer>();

		for (Element target : this.elements) {
			ids.add(((CraftElement) target).getId());
		}

		int id = 0;

		while (ids.contains(id)) {
			id++;
		}

		this.elements.add(element);
		this.injectId(element, id);

		this.renderElement(element);
	}

	@Override
	public void renderAll() {
		for (Element element : this.elements) {
			this.renderElement(element, false);
		}
		
		this.updateInventory();
	}

	@Override
	public void renderElement(Element element) {
		Validate.notNull(element, "The element cannot be null!");

		if (!this.elements.contains(element)) {
			throw new ElementEditorException("The element is not added in this Window!");
		}

		this.renderElement(element, true);
	}

	@Override
	public void clearElements() {
		for (Element element : this.elements) {
			this.removeElement(element, false);
		}
		
		this.updateInventory();
	}

	@Override
	public void removeElement(Element element) {
		Validate.notNull(element, "The element cannot be null!");

		if (!this.elements.contains(element)) {
			throw new ElementEditorException("The element is not added in this Window!");
		}

		this.removeElement(element, true);
	}

	@Override
	public void priorize(Element element) {
		Validate.notNull(element, "The element cannot be null!");

		if (!this.elements.contains(element)) {
			throw new ElementEditorException("The element is not added in this Window!");
		}

		this.elements.remove(element);
		this.elements.add(0, element);
		this.renderElement(element);
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
			return Icon.AIR;
		} else {
			return element.getIcon(point.subtract(element.getPoint()));
		}
	}

	@Override
	public ElementCreator elementCreator() {
		return this.creator;
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
	public List<Element> getElements() {
		return Collections.unmodifiableList(this.elements);
	}

	@Override
	public Set<Element> getElementsAt(Point point) {
		Validate.notNull(point, "The point cannot be null!");

		return Collections.unmodifiableSet(this.elements.stream().filter((element) -> {
			return element.isInside(point.subtract(element.getPoint()));
		}).collect(Collectors.toSet()));
	}
	
	@Override
	public Set<Point> getOccupiedPoints(Element element) {
		Validate.notNull(element, "The element cannot be null!");
		
		if (!this.elements.contains(element)) {
			throw new ElementEditorException("The element is not added in this Window!");
		}
		
		Set<Point> result = new HashSet<>();
		
		for (Point target : element.getPoints()) {
			target = target.add(element.getPoint());
			
			if (this.getVisibleElementAt(target) == element) {
				result.add(target);
			}
		}
		
		return result;
	}
	
	@Override
	public void renderFrame() {
		super.renderFrame();
	}
	
	@Override
	public void renderPoint(Point point) {
		super.renderPoint(point);
	}
	
	@Override
	public void renderPoints(Iterable<Point> points) {
		super.renderPoints(points);
	}
	
	private void injectId(Element element, int id) {
		Field fieldId = ReflectionUtil.getDeclaredField(CraftElement.class, "id");
		ReflectionUtil.setValue(fieldId, element, id);
	}
	
	private void renderElement(Element element, boolean update) {
		this.renderPoints(element.getPoints().stream().map((point) -> {
			return point.add(element.getPoint());
		}).filter((point) -> {
			return this.isInside(point);
		}).collect(Collectors.toSet()));
		
		if (update) {
			this.updateInventory();
		}
	}
	
	private void removeElement(Element element, boolean update) {
		Set<Point> points = element.getPoints();
		
		this.injectId(element, -1);
		this.elements.remove(element);
		
		this.renderPoints(points.stream().map((point) -> {
			return point.add(element.getPoint());
		}).collect(Collectors.toSet()));
		
		if (update) {
			this.updateInventory();
		}
	}

}
