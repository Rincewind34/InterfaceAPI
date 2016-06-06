package de.rincewind.api.exceptions;

@SuppressWarnings("serial")
public class InvalidSlotException extends NullPointerException {
	
	public InvalidSlotException(int slot, Class<?> clazz) {
		super("The slot " + slot + " is undefined in the class " + clazz.getName());
	}
	
}
