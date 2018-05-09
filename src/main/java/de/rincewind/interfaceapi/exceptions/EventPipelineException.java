package de.rincewind.interfaceapi.exceptions;

public class EventPipelineException extends Exception {

	private static final long serialVersionUID = 5395179504507599157L;
	
	public EventPipelineException() {
		
	}
	
	public EventPipelineException(String message) {
		super(message);
	}
	
	public EventPipelineException(Throwable cause) {
		 super(cause);
	}
	
}
