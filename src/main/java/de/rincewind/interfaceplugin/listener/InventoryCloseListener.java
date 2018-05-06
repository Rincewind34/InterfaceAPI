package de.rincewind.interfaceplugin.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import de.rincewind.interfaceapi.InterfaceAPI;

public class InventoryCloseListener implements Listener {
	
	public static List<String> blocked = new ArrayList<>();
	
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		Player player = (Player) e.getPlayer();
		
		if (InventoryCloseListener.blocked.remove(player.getName())) {
			return;
		} else if (InterfaceAPI.getSetup(player).hasMaximizedWindow()) {
			InterfaceAPI.getSetup(player).close(InterfaceAPI.getSetup(player).getMaximizedWindow());
		}
	}
	
}
