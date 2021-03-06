package de.rincewind.interfaceplugin.gui.elements.abstracts;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import de.rincewind.interfaceapi.exceptions.ElementComponentException;
import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.ClickBlocker;
import de.rincewind.interfaceapi.gui.elements.util.ElementComponent;
import de.rincewind.interfaceapi.gui.elements.util.ElementComponent.PositiveNumberElementComponent;
import de.rincewind.interfaceapi.gui.elements.util.ElementComponentType;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.util.Point;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowEditor;
import de.rincewind.interfaceapi.handling.EventManager;
import de.rincewind.interfaceapi.handling.element.ElementInteractEvent;
import de.rincewind.interfaceapi.handling.element.ElementStackChangeEvent;
import de.rincewind.interfaceapi.handling.element.ElementValueChangeEvent;
import de.rincewind.interfaceplugin.Validate;
import de.rincewind.interfaceplugin.gui.util.CraftClickBlocker;
import de.rincewind.interfaceplugin.gui.util.CraftEventManager;

public abstract class CraftElement implements Element {
	
	protected final static void clearInstructions(Displayable displayable) {
		if (displayable != Icon.AIR && displayable.hasStaticIcon()) {
			// Remove possibly set instructions
			displayable.getIcon().getLore().setEnd(null);
		}
	}

	private boolean visible;

	private Point point;

	private Object userObject;

	private ClickBlocker blocker;
	private EventManager eventManager;

	private Map<ElementComponentType<?>, ElementComponent<?>> components;

	private final WindowEditor handle;

	public CraftElement(WindowEditor handle) {
		Validate.notNull(handle, "The handle cannot be null");

		this.handle = handle;
		this.visible = true;
		this.point = Point.NULL;
		this.components = new HashMap<>();
		this.eventManager = new CraftEventManager();
		this.blocker = new CraftClickBlocker();

		this.blocker.lock();

		this.registerComponent(Element.ENABLED, new ElementComponent<>(Boolean.class, true, (oldValue, newValue) -> {
			this.update();
			this.onEnabledChange();
		}));

		this.registerComponent(Element.INSTRUCTIONS, new ElementComponent<>(Boolean.class, true, (oldValue, newValue) -> {
			this.update();
		}));

		this.registerComponent(Element.WIDTH, new PositiveNumberElementComponent<>(Integer.class, 1, (oldValue, newValue) -> {
			this.update();
		}));

		this.registerComponent(Element.HEIGHT, new PositiveNumberElementComponent<>(Integer.class, 1, (oldValue, newValue) -> {
			this.update();
		}));

		this.getComponent(Element.ENABLED).setEnabled(false);
		this.getComponent(Element.WIDTH).setEnabled(false);
		this.getComponent(Element.HEIGHT).setEnabled(false);
		this.getComponent(Element.INSTRUCTIONS).setEnabled(false);

		this.getEventManager().registerListener(ElementInteractEvent.class, (event) -> {
			if (!this.isEnabled()) {
				event.cancel();
				event.consume();
			}
		}).addAfter();

		this.getEventManager().registerListener(ElementStackChangeEvent.class, (event) -> {
			if (!this.getBlocker().allows(event.getAction())) {
				event.cancel();
				event.consume();
			}
		}).addAfter();
	}

	@Override
	public void update() {
		this.handle.renderElement(this);
	}

	@Override
	public void priorize() {
		this.handle.priorize(this);
	}

	@Override
	public void setPoint(Point point) {
		Validate.notNull(point, "The point cannot be null!");

		if (this.point.equals(point)) {
			return;
		}

		Set<Point> old = this.point.square(this.getWidth(), this.getHeight());

		this.point = point;

		this.update();
		this.handle.renderPoints(old);
	}

	@Override
	public void setVisible(boolean visible) {
		if (this.visible == visible) {
			return;
		}

		this.visible = visible;
		this.update();
	}

	@Override
	public void setBlocker(ClickBlocker blocker) {
		Validate.notNull(blocker, "The blocker cannot be null!");

		this.blocker = blocker;
	}

	@Override
	public void setUserObject(Object obj) {
		this.userObject = obj;
	}

	@Override
	public void dependsOn(Element element) {
		element.getEventManager().registerListener(ElementValueChangeEvent.class, (event) -> {
			this.update();
		}).monitor();
	}

	@Override
	public boolean isVisible() {
		return this.visible;
	}

	@Override
	public boolean isEnabled() {
		return this.getComponent(Element.ENABLED).getValue();
	}

	@Override
	public boolean showInstructions() {
		return this.getComponent(Element.INSTRUCTIONS).getValue();
	}

	@Override
	public boolean isElementComponentEnabled(ElementComponentType<?> component) {
		return this.getComponent(component).isEnabled();
	}

	@Override
	public int getWidth() {
		return this.getComponent(Element.WIDTH).getValue();
	}

	@Override
	public int getHeight() {
		return this.getComponent(Element.HEIGHT).getValue();
	}

	@Override
	public Point getPoint() {
		return this.point;
	}

	@Override
	public final Icon getIcon(Point point) {
		Validate.notNull(point, "The point cannot be null");

		if (!this.isInside(point)) {
			throw new IllegalArgumentException("The point is not in this element");
		}

		Icon icon = this.getIcon0(point);

		assert icon != null : "The calculated icon at " + point + " is null";
		return icon;
	}

	@Override
	public ClickBlocker getBlocker() {
		return this.blocker;
	}

	@Override
	public EventManager getEventManager() {
		return this.eventManager;
	}

	@Override
	public <T> void setComponentValue(ElementComponentType<T> type, T value) {
		try {
			this.getComponent(type).setValue(value);
		} catch (Exception exception) {
			throw new ElementComponentException("Failed to set component " + type + " to " + value + " in " + this.getClass().getName(), exception);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getUserObject() {
		return (T) this.userObject;
	}

	@Override
	public <T> T getUserObject(Class<T> cls) {
		return cls.cast(this.userObject);
	}

	public WindowEditor getHandle() {
		return this.handle;
	}

	@SuppressWarnings("unchecked")
	public <T> ElementComponent<T> getComponent(ElementComponentType<T> type) {
		for (ElementComponentType<?> target : this.components.keySet()) {
			if (target == type) {
				return (ElementComponent<T>) this.components.get(target);
			}
		}

		return null;
	}

	public void onElementAdded() {

	}

	public void onElementRemoved() {

	}

	protected void updateEnabled() {
		if (this.isEnabled()) {
			this.update();
		}
	}

	protected void onEnabledChange() {

	}

	protected abstract Icon getIcon0(Point point);

	protected <T> void registerComponent(ElementComponentType<T> type, ElementComponent<T> component) {
		this.components.put(type, component);
	}

	protected final Icon updateInstructions(Icon icon, String instructions) {
		if (this.showInstructions()) {
			icon.getLore().setEnd(instructions);
		} else {
			icon.getLore().setEnd(null);
		}

		return icon;
	}

}
