package de.rincewind.api.gui.windows.abstracts;

import de.rincewind.api.gui.components.Nameable;
import de.rincewind.api.gui.windows.Window.WindowExtendable;
import de.rincewind.plugin.gui.components.CraftNameable;

public abstract class WindowNameable extends WindowExtendable implements Nameable {

	private Nameable nameable;
	
	/**
	 * WindowNameable: You can set a name for the window
	 */
	public WindowNameable() {
		this.nameable = new CraftNameable("GUI");
	}
	
	@Override
	public String getName() {
		return nameable.getName();
	}
	
	@Override
	public boolean setName(String name) {
		if (!this.nameable.setName(name)) {
			return false;
		}
		return true;
	}
	
}
