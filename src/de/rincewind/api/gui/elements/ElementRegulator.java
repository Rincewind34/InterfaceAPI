package de.rincewind.api.gui.elements;

import de.rincewind.api.gui.Directionality;
import de.rincewind.api.gui.components.Modifyable;

public class ElementRegulator extends ElementButton {
	
	private Directionality type;
	
	public ElementRegulator(Modifyable handle) {
		super(handle);
		
		this.type = Directionality.HORIZONTAL;
	}

	public Directionality getType() {
		return this.type;
	}
	
}
