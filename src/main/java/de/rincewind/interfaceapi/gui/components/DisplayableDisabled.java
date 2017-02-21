package de.rincewind.interfaceapi.gui.components;

import de.rincewind.interfaceapi.gui.elements.util.Icon;

public interface DisplayableDisabled {
	
	public abstract Icon getDisabledIcon();
	
	public default void setDisabledIcon(Icon icon) {
		throw new UnsupportedOperationException();
	}
	
}
