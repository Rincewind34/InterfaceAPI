package de.rincewind.api.handling.events;

import org.bukkit.entity.Player;

import de.rincewind.api.gui.windows.abstracts.Window;

public class WindowOpenEvent extends WindowEvent<Window> {
	
	private Player owner;
	
	public WindowOpenEvent(Window window, Player owner) {
		super(window);
		
		this.owner = owner;
	}
	
	public Player getOwner() {
		return this.owner;
	}

}
