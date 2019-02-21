package de.rincewind.interfaceapi.handling.element;

import de.rincewind.interfaceapi.gui.components.ActionItem;
import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;

public class CustomActionPerformEvent extends ElementEvent<Element> {
	
	private final Displayable item;
	
	public CustomActionPerformEvent(Element element, Displayable item) {
		super(element);
		
		this.item = item;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Displayable & ActionItem> T getItem() {
		return (T) this.item;
	}
	
	public <T> T getItemAsPayload() {
		return Displayable.readPayload(this.item);
	}

}
