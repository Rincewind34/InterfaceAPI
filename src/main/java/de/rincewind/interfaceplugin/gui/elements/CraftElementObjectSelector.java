package de.rincewind.interfaceplugin.gui.elements;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

import de.rincewind.interfaceapi.InterfaceAPI;
import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.elements.ElementObjectSelector;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.util.Point;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowEditor;
import de.rincewind.interfaceapi.handling.element.ElementInteractEvent;
import de.rincewind.interfaceapi.handling.element.ObjectSelectEvent;
import de.rincewind.interfaceapi.selectors.window.WindowSelector;
import de.rincewind.interfaceplugin.Validate;
import de.rincewind.interfaceplugin.gui.elements.abstracts.CraftElementDisplayable;

public class CraftElementObjectSelector<T> extends CraftElementDisplayable implements ElementObjectSelector<T> {

	public static String DEFAULT_INSTRUCTIONS = "TODO";

	private boolean canUnselect;

	private T selected;
	private Displayable cachedDisplay;

	private final Class<T> objectClass;
	private Supplier<Collection<? extends T>> selectableElements;
	private Function<T, Displayable> converter;

	public CraftElementObjectSelector(WindowEditor handle, Class<T> objectClass) {
		super(handle);
		
		Validate.notNull(objectClass, "The object class cannot be null");

		if (!InterfaceAPI.isWindowSelectorRegistered(objectClass)) {
			throw new IllegalArgumentException("The object class has no registered window selector");
		}

		this.objectClass = objectClass;

		this.converter = Displayable::of;
		this.getComponent(Element.INSTRUCTIONS).setEnabled(true);

		this.getEventManager().registerListener(ElementInteractEvent.class, (event) -> {
			if (event.isLeftClick()) {
				if (event.isShiftClick()) {
					if (this.canUnselect) {
						this.unselect();
					}
				} else {
					WindowSelector<?> window;

					if (this.selectableElements != null) {
						window = InterfaceAPI.getWindowCreator(this.objectClass).newWindow(handle.getPlugin(), this::select, this.selectableElements.get(),
								this.selected);
					} else {
						window = InterfaceAPI.getWindowCreator(this.objectClass).newWindow(handle.getPlugin(), this::select, this.selected);
					}

					InterfaceAPI.getSetup(event.getPlayer()).open(window);
				}
			}
		}).monitor();
	}

	@Override
	public void setConverter(Function<T, Displayable> converter) {
		if (converter == null) {
			this.converter = Displayable::of;
		} else {
			this.converter = converter;
		}
	}
	
	public void setSelectableElements(Supplier<Collection<? extends T>> getter) {
		this.selectableElements = getter;
	}

	@Override
	public void setCanUnselect(boolean value) {
		this.canUnselect = value;
	}

	@Override
	public void select(T value) {
		this.select(value, true);
	}

	@Override
	public void select(T value, boolean fireEvent) {
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
	public Class<T> getObjectClass() {
		return this.objectClass;
	}

	@Override
	public T getSelectedObject() {
		return this.selected;
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
