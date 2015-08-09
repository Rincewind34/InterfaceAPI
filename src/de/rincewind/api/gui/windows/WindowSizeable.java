package de.rincewind.api.gui.windows;

import de.rincewind.api.gui.components.Sizeable;
import de.rincewind.api.gui.windows.abstracts.WindowColorable;

public interface WindowSizeable extends WindowColorable, Sizeable {
	
	public abstract boolean checkSize(int width, int height);
	
}
