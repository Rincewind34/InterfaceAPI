package de.rincewind.plugin.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.rincewind.api.InterfaceAPI;

public class PlayerQuitListener implements Listener {
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		InterfaceAPI.getSetup(event.getPlayer()).closeAll();
	}
	
}
