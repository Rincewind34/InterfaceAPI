package de.rincewind.defaults.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import de.rincewind.plugin.InterfacePlugin.InterfaceAPI;
import de.rincewind.plugin.gui.CraftWindowManager;

public class InventoryCloseListener implements Listener {
	
	public static List<String> blocked = new ArrayList<String>();
	
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		Player player = (Player) e.getPlayer();
		if (InventoryCloseListener.blocked.contains(player.getName())) {
			return;
		} else if (InterfaceAPI.getWindowManager().hasMaximizedWindow(player)) {
			((CraftWindowManager) InterfaceAPI.getWindowManager()).close(player, InterfaceAPI.getWindowManager().getMaximizedWindow(player));
		}
	}
	
}
