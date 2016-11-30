package de.rincewind.interfaceapi.gui.elements.util;

public class ElementComponentType<T> {

	private String name;

	private Class<T> type;

	public ElementComponentType(Class<T> type, String name) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return this.name;
	}

	public Class<T> getType() {
		return this.type;
	}

}
