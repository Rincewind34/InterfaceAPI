package de.rincewind.interfaceapi.gui.elements;

import java.util.List;

import de.rincewind.interfaceapi.DebugScene;
import de.rincewind.interfaceapi.gui.elements.abstracts.ElementDisplayable;

public interface ElementInfo extends ElementDisplayable {
	
	public abstract void debug(String info, boolean positiv);
	
	public abstract void debug(List<String> info, boolean positiv);
	
	public abstract void debug(DebugScene scene);
	
	public abstract void reset();
	
}
