package de.rincewind.interfaceapi.gui.components;

import de.rincewind.interfaceapi.gui.elements.util.Icon;

class SimpleDisplay implements Displayable, UserMemory {
	
	private Icon icon;
	private Object userObject;
	
	public SimpleDisplay() {
		this(Icon.AIR);
	}
	
	public SimpleDisplay(Icon icon) {
		this(icon, null);
	}
	
	public SimpleDisplay(Icon icon, Object userObject) {
		assert icon != null : "The icon cannot be null";
		
		this.icon = icon;
		this.userObject = userObject;
	}
	
	@Override
	public void setUserObject(Object userObject) {
		this.userObject = userObject;
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
	public Icon getIcon() {
		return this.icon;
	}

	@Override
	public boolean hasStaticIcon() {
		return true;
	}
	
}
