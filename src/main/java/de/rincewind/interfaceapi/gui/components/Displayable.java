package de.rincewind.interfaceapi.gui.components;

import de.rincewind.interfaceapi.gui.elements.util.Icon;

public interface Displayable {
	
	public abstract Icon getIcon();
	
	public default void setIcon(Icon icon) {
		throw new UnsupportedOperationException();
	}
	
}
