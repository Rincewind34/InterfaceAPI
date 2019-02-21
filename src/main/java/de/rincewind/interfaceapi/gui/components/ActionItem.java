package de.rincewind.interfaceapi.gui.components;

public interface ActionItem {
	
	public abstract void performCustomAction(Runnable executed);
	
	public abstract String getCustomActionInstructions();
	
}
