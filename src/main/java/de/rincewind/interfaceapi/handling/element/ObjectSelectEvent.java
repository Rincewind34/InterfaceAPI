package de.rincewind.interfaceapi.handling.element;

import de.rincewind.interfaceapi.gui.elements.ElementObjectSelector;

public class ObjectSelectEvent extends ElementValueChangeEvent<ElementObjectSelector<?>> {
	
	private Object oldSelection;
	
	public ObjectSelectEvent(ElementObjectSelector<?> element, Object oldSelection) {
		super(element);
		
		this.oldSelection = oldSelection;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getOldSelection() {
		return (T) this.oldSelection;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getNewSelection() {
		return (T) this.getElement().getSelectedObject();
	}
	
	public <T> T getOldSelection(Class<T> cls) {
		return this.getOldSelection();
	}
	
	public <T> T getNewSelection(Class<T> cls) {
		return this.getNewSelection();
	}
	
}
