package de.rincewind.api.exceptions;

@SuppressWarnings("serial")
public class APIException extends RuntimeException {
	
	public APIException(String error) {
		super(error);
	}
	
}
