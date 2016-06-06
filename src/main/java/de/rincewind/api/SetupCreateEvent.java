package de.rincewind.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import de.rincewind.api.setup.Setup;

public class SetupCreateEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	
	private Player player;
	private Setup setup;
	
	public SetupCreateEvent(Setup setup, Player player) {
		this.player = player;
		this.setup = setup;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public Setup getSetup() {
		return this.setup;
	}
	
	@Override
	public HandlerList getHandlers() {
		return SetupCreateEvent.handlers;
	}
	
	public static HandlerList getHandlerList() {
		return SetupCreateEvent.handlers;
	}

}
