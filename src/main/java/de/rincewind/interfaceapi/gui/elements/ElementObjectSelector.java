package de.rincewind.interfaceapi.gui.elements;

import java.util.function.Function;

import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.elements.abstracts.ElementDisplayable;

public interface ElementObjectSelector extends ElementDisplayable {
	
	public abstract void setObjectClass(Class<?> objectClass);
	
	public abstract void setConverter(Function<Object, Displayable> converter);
	
	public abstract void setCanUnselect(boolean value);
	
	public abstract void select(Object value);
	
	public abstract void select(Object value, boolean fireEvent);
	
	public abstract void unselect();
	
	public abstract boolean isObjectSelected();
	
	public abstract boolean canUnselect();
	
	public abstract Class<?> getObjectClass();
	
	public abstract <T> T getSelectedObject();
	
	public default <T> T getSelectedObject(Class<T> cls) {
		return this.getSelectedObject();
	}
	
}
