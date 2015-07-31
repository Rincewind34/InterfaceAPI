package de.rincewind.api.gui.components;

import de.rincewind.api.gui.windows.util.Status;

public abstract interface Openable {
	
	/**
	 * 
	 * @return The currently active state
	 */
	public abstract Status getStatus();
	
	public abstract void open();
	
	public abstract void close();
	
	public abstract void maximize();
	
	public abstract void minimize();
	
	public abstract void moveBack();
	
}
