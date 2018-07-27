package de.rincewind.interfaceapi.handling.element;

import de.rincewind.interfaceapi.gui.elements.ElementSwitcher;

public class SwitchChangeEvent extends ElementValueChangeEvent<ElementSwitcher> {
	
	private int newIndex;
	
	public SwitchChangeEvent(ElementSwitcher element, int newIndex) {
		super(element, element.getCurrent());
		
		this.newIndex = newIndex;
	}
	
	public int getNewIndex() {
		return this.newIndex;
	}
	
	public <T> T getNewSwitch() {
		return this.getElement().get(this.newIndex);
	}
	
}
