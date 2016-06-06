package de.rincewind.api.gui.elements;

import java.util.List;

import de.rincewind.api.DebugScene;
import de.rincewind.api.gui.elements.abstracts.ElementDisplayable;

public interface ElementInfo extends ElementDisplayable {
	
	public abstract void debug(String info, boolean positiv);
	
	public abstract void debug(List<String> info, boolean positiv);
	
	public abstract void debug(DebugScene scene);
	
	public abstract void reset();
	
}
