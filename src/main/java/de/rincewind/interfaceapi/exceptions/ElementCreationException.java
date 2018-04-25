package de.rincewind.interfaceapi.exceptions;

public class ElementCreationException extends RuntimeException {

	private static final long serialVersionUID = 3137393269722634056L;
	
	public ElementCreationException(String message) {
		super(message);
	}
	
	public ElementCreationException(Throwable cause) {
		super(cause);
	}
	
}
