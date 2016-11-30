package de.rincewind.interfaceapi.gui.components;

public interface UserMemory {
	
	public abstract void setUserObject(Object obj);
	
	public abstract <T> T getUserObject();
	
	public abstract <T> T getUserObject(Class<T> cls);
	
}
