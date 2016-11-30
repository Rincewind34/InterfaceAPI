package de.rincewind.interfaceapi.handling.element;

import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.elements.ElementSwitcher;

public class SwitchChangeEvent extends ElementEvent<ElementSwitcher> {
	
	private int newIndex;
	
	public SwitchChangeEvent(ElementSwitcher element, int newIndex) {
		super(element);
		
		this.newIndex = newIndex;
	}
	
	public int getNewIndex() {
		return this.newIndex;
	}
	
	public <T extends Displayable> T getNewSwitch() {
		return this.getElement().getSwitch(this.newIndex);
	}
	
	public <T extends Displayable> T getNewSwitch(Class<T> cls) {
		return this.getElement().getSwitch(cls, this.newIndex);
	}
	
}
