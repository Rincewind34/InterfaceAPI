package de.rincewind.interfaceapi.gui.elements.util;

import de.rincewind.interfaceplugin.Validate;

public class ElementComponentType<T> {

	private String name;

	private Class<T> type;

	public ElementComponentType(Class<T> type, String name) {
		Validate.notNull(type, "The type cannot be null");
		Validate.notNull(name, "The name cannot be null");
		
		this.name = name;
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "ElementComponent:" + this.name;
	}

	public String getName() {
		return this.name;
	}

	public Class<T> getType() {
		return this.type;
	}

}
