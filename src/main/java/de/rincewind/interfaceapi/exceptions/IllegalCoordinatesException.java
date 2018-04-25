package de.rincewind.interfaceapi.exceptions;

public class IllegalCoordinatesException extends IllegalArgumentException {

	private static final long serialVersionUID = 758266913349546897L;
	
	public IllegalCoordinatesException(String message) {
		super(message);
	}
	
}
