package de.rincewind.interfaceapi.gui.components;

import de.rincewind.interfaceapi.gui.elements.util.Icon;

public interface Displayable {
	
	public static Icon validate(Displayable icon) {
		return icon == null ? Icon.AIR : icon.getIcon();
	}
	
	public abstract Icon getIcon();
	
}
