package de.rincewind.interfaceapi.handling.window;

import org.bukkit.entity.Player;

import de.rincewind.interfaceapi.gui.windows.abstracts.Window;

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
