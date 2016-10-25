package de.rincewind.api.handling.events;

import org.bukkit.entity.Player;

import de.rincewind.api.gui.windows.abstracts.Window;

public class WindowOpenEvent extends WindowEvent<Window> {
	
	private Player player;
	
	public WindowOpenEvent(Player player, Window window) {
		super(window);
		
		this.player = player;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
}
