package de.rincewind.interfaceapi.gui.elements;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.elements.abstracts.ElementDisplayable;

public interface ElementObjectSelector<T> extends ElementDisplayable {
	
	public abstract void setObjectClass(Class<T> objectClass);
	
	public abstract void setConverter(Function<T, Displayable> converter);
	
	public abstract void setSelectableElements(Supplier<Collection<? extends T>> getter);
	
	public abstract void setCanUnselect(boolean value);
	
	public abstract void select(T value);
	
	public abstract void select(T value, boolean fireEvent);
	
	public abstract void unselect();
	
	public abstract boolean isObjectSelected();
	
	public abstract boolean canUnselect();
	
	public abstract T getSelectedObject();
	
	public abstract Class<T> getObjectClass();
	
}
