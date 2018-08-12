package de.rincewind.interfaceapi.handling.element;

import de.rincewind.interfaceapi.gui.elements.ElementSwitcher;

public class SwitchChangeEvent extends ElementValueChangeEvent<ElementSwitcher> {

	private int previousIndex;
	private int newIndex;
	
	public SwitchChangeEvent(ElementSwitcher element, int previousIndex, int newIndex) {
		super(element, element.get(newIndex));
		
		this.previousIndex = previousIndex;
		this.newIndex = newIndex;
	}
	
	public int getPreviousIndex() {
		return this.previousIndex;
	}
	
	public int getNewIndex() {
		return this.newIndex;
	}
	
	public <T> T getPreviousSwitch() {
		return this.getElement().get(this.previousIndex);
	}
	
	public <T> T getNewSwitch() {
		return this.getElement().get(this.newIndex);
	}
	
}
