package de.rincewind.interfaceapi.exceptions;

import de.rincewind.interfaceapi.gui.util.Bounds;

@SuppressWarnings("serial")
public class InvalidSizeException extends RuntimeException {

	public InvalidSizeException(Bounds bounds, Class<?> clazz) {
		super("The size (" + bounds.getWidth() + "x" + bounds.getHeight() + ") is undifined in class " + clazz.getName());
	}

}
