package de.rincewind.api.gui.elements.util;

import de.rincewind.api.gui.components.Displayable;
import de.rincewind.api.gui.components.UserMemory;

public class SimpleDisplay implements Displayable, UserMemory {
	
	private Icon icon;
	private Object userObject;
	
	public SimpleDisplay() {
		this(Icon.AIR);
	}
	
	public SimpleDisplay(Icon icon) {
		this(icon, null);
	}
	
	public SimpleDisplay(Icon icon, Object userObject) {
		this.icon = icon;
		this.userObject = userObject;
	}
	
	@Override
	public void setUserObject(Object obj) {
		this.userObject = obj;
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

	@Override
	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	@Override
	public Icon getIcon() {
		return this.icon;
	}
	
}
