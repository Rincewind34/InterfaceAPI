package de.rincewind.api.exceptions;

@SuppressWarnings("serial")
public class InvalidSizeException extends RuntimeException {
	
	public InvalidSizeException(int width, int heigth, Class<?> clazz) {
		super("The size (" + width + "x" + heigth + ") is undifined in class " + clazz.getName());
	}
	
}
