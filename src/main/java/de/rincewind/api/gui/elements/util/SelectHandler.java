package de.rincewind.api.gui.elements.util;

import java.util.List;

import de.rincewind.api.gui.components.Selectable;

public interface SelectHandler {
	
	public abstract List<Selectable> getEntries();
	
	public abstract Selectable getSelected();
	
	public abstract void unselect();
	
}
