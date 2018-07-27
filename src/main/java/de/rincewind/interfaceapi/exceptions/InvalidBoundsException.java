package de.rincewind.interfaceapi.exceptions;

import de.rincewind.interfaceapi.gui.util.Bounds;

@SuppressWarnings("serial")
public class InvalidBoundsException extends RuntimeException {

	public InvalidBoundsException(String message) {
		super(message);
	}

	public InvalidBoundsException(Bounds bounds, Class<?> clazz) {
		this("The size (" + bounds.getWidth() + "x" + bounds.getHeight() + ") is undifined in class " + clazz.getName());
	}

}
