package de.rincewind.interfaceplugin.gui.elements.abstracts;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import de.rincewind.interfaceapi.gui.components.Modifyable;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.ClickBlocker;
import de.rincewind.interfaceapi.gui.elements.util.ElementComponent;
import de.rincewind.interfaceapi.gui.elements.util.ElementComponentType;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceapi.handling.EventManager;
import de.rincewind.interfaceplugin.Validate;
import de.rincewind.interfaceplugin.gui.util.CraftClickBlocker;
import de.rincewind.interfaceplugin.gui.util.CraftEventManager;

public abstract class CraftElement implements Element {
	
	private int id;
	private Modifyable handle;
	
	private boolean visible;
	
	private Point point;
	
	private Object userObject;
	
	private ClickBlocker blocker;
	private EventManager eventManager;
	
	private Map<ElementComponentType<?>, ElementComponent<?>> components;
	
	public CraftElement(Modifyable handle) {
		this.handle = handle;
		this.id = -1;
		this.visible = true;
		this.point = Point.NULL;
		this.components = new HashMap<>();
		this.eventManager = new CraftEventManager();
		
		this.blocker = new CraftClickBlocker();
		this.blocker.lock();
		
		this.registerComponent(Element.ENABLED, true, () -> {
			this.update();
		});
		
		this.registerComponent(Element.WIDTH, 1, () -> {
			this.update();
		});
		
		this.registerComponent(Element.HEIGHT, 1, () -> {
			this.update();
		});

		this.getComponent(Element.ENABLED).setEnabled(false);
		this.getComponent(Element.WIDTH).setEnabled(false);
		this.getComponent(Element.HEIGHT).setEnabled(false);
	}

	@Override
	public void update() {
		this.getHandle().renderElement(this);
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

		this.point = point;
		this.update();
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
	public boolean isVisible() {
		return this.visible;
	}

	@Override
	public boolean isEnabled() {
		return this.getComponent(Element.ENABLED).getValue();
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
	public Icon getIcon(Point point) {
		if (!this.isInside(point)) {
			throw new RuntimeException("The point is not in this element!");
		}
		
		return Icon.AIR;
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
	public Set<Point> getPoints() {
		return Collections.unmodifiableSet(this.getPoint().square(this.getWidth(), this.getHeight()));
	}
	
	@Override
	public <T> void setComponentValue(ElementComponentType<T> type, T value) {
		this.getComponent(type).setValue(value);
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

	public int getId() {
		return this.id;
	}

	public Modifyable getHandle() {
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
	
	protected <T> void registerComponent(ElementComponentType<T> type, T defaultValue, Runnable onChange) {
		this.components.put(type, new ElementComponent<T>(type.getType(), defaultValue, onChange));
	}
	
}
