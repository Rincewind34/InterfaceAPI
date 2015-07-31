package de.rincewind.defaults.exceptions;

@SuppressWarnings("serial")
public class InvalidSizeException extends NullPointerException {
	
	public InvalidSizeException(int width, int heigth, Class<?> clazz) {
		super("The size (" + width + "x" + heigth + ") is undifined in class " + clazz.getName());
	}
	
}
