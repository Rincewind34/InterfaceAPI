package de.rincewind.interfaceapi.exceptions;

public class SelectorInterfaceException extends RuntimeException {

	private static final long serialVersionUID = 2986537717484930022L;

	public SelectorInterfaceException(String message) {
		super(message);
	}
	
	public SelectorInterfaceException(Throwable cause) {
		super(cause);
	}
	
}
