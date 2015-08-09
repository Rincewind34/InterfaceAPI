package de.rincewind.api.gui.windows;

import de.rincewind.api.gui.windows.abstracts.WindowEditor;

public abstract interface WindowEnchanter extends WindowEditor {

	public abstract void setOffer(int slot, int lvl);
	
	public abstract void update();
	
	public abstract int getOffer(int slot);
	
}
