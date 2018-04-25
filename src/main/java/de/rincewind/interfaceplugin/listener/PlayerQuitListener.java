package de.rincewind.interfaceplugin.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.rincewind.interfaceapi.InterfaceAPI;

public class PlayerQuitListener implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onQuit(PlayerQuitEvent event) {
		InterfaceAPI.cleanUpSetup(event.getPlayer());
	}
	
}
