package de.rincewind.interfaceapi.exceptions;

@SuppressWarnings("serial")
public class ItemCreatorException extends Exception{
	
	public ItemCreatorException() {
		super("Das Item wird bereits verwendet!");
	}
	
}
