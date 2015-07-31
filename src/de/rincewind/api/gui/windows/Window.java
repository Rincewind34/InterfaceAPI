package de.rincewind.api.gui.windows;

import org.bukkit.entity.Player;

import de.rincewind.api.gui.components.Openable;
import de.rincewind.api.gui.windows.util.Status;
import de.rincewind.plugin.gui.CraftWindow;

public abstract interface Window extends Openable {
	
	/**
	 * 
	 * @return The current state of this window
	 */
	public abstract Status getStatus();
	
	/**
	 * 
	 * @return The user of this user
	 */
	public abstract Player getUser();
	
	public abstract static class WindowExtendable extends CraftWindow {

		public WindowExtendable() {
			super();
		}
		
	}
	
}
