package de.rincewind.interfaceplugin.gui.elements;

import java.util.function.Function;

import de.rincewind.interfaceapi.InterfaceAPI;
import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.elements.ElementObjectSelector;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowEditor;
import de.rincewind.interfaceapi.gui.windows.selectors.WindowSelector;
import de.rincewind.interfaceapi.handling.element.ElementInteractEvent;
import de.rincewind.interfaceapi.handling.element.ObjectSelectEvent;
import de.rincewind.interfaceplugin.Validate;
import de.rincewind.interfaceplugin.gui.elements.abstracts.CraftElementDisplayable;

public class CraftElementObjectSelector extends CraftElementDisplayable implements ElementObjectSelector {
	
	public static String DEFAULT_INSTRUCTIONS = "TODO";

	private boolean canUnselect;

	private Object selected;
	private Displayable cachedDisplay;

	private Class<?> objectClass;
	private Function<Object, Displayable> converter;

	public CraftElementObjectSelector(WindowEditor handle) {
		super(handle);

		this.converter = Displayable::of;
		this.getComponent(Element.INSTRUCTIONS).setEnabled(true);

		this.getEventManager().registerListener(ElementInteractEvent.class, (event) -> {
			if (event.isLeftClick()) {
				if (event.isShiftClick()) {
					if (this.canUnselect) {
						this.unselect();
					}
				} else {
					WindowSelector<?> window = InterfaceAPI.newSelector(null, handle.getPlugin(), null, (selected) -> {
						this.select(selected);
					});
					
					InterfaceAPI.getSetup(event.getPlayer()).open(window);
				}
			}
		}).monitor();
	}

	@Override
	public void setObjectClass(Class<?> objectClass) {
		Validate.notNull(objectClass, "The object class cannot be null");

		if (!InterfaceAPI.isWindowSelectorRegistered(objectClass)) {
			throw new IllegalArgumentException("The object class has no registered window selector");
		}

		if (this.selected != null && !objectClass.isInstance(this.selected)) {
			throw new IllegalArgumentException("The new object class is not assignable to currently selected value");
		}

		this.objectClass = objectClass;
	}

	@Override
	public void setConverter(Function<Object, Displayable> converter) {
		if (converter == null) {
			this.converter = Displayable::of;
		} else {
			this.converter = converter;
		}
	}

	@Override
	public void setCanUnselect(boolean value) {
		this.canUnselect = value;
	}

	@Override
	public void select(Object value) {
		this.select(value, true);
	}

	@Override
	public void select(Object value, boolean fireEvent) {
		if (this.objectClass == null) {
			throw new IllegalStateException("The object class is null");
		}

		if (!this.objectClass.isInstance(value)) {
			throw new IllegalArgumentException("The value cannot be assigned to current object class");
		}

		Object oldValue = this.selected;
		this.selected = value;
		
		if (this.selected != null) {
			this.cachedDisplay = this.converter.apply(this.selected);
		} else {
			this.cachedDisplay = null;
		}
		
		if (fireEvent) {
			this.getEventManager().callEvent(ObjectSelectEvent.class, new ObjectSelectEvent(this, oldValue));
		}
	}

	@Override
	public void unselect() {
		this.select(null);
	}
	
	@Override
	public boolean isObjectSelected() {
		return this.selected != null;
	}
	
	@Override
	public boolean canUnselect() {
		return this.canUnselect;
	}

	@Override
	public Class<?> getObjectClass() {
		return this.objectClass;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getSelectedObject() {
		return (T) this.selected;
	}
	
	@Override
	protected String currentInstructions() {
		return CraftElementObjectSelector.DEFAULT_INSTRUCTIONS;
	}

	@Override
	protected Icon getIcon0(Point point) {
		if (this.isEnabled() && this.selected != null) {
			return this.cachedDisplay.getIcon();
		}
		
		return super.getIcon0(point);
	}
	
}
