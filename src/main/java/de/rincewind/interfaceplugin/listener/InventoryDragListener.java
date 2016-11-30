package de.rincewind.interfaceplugin.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;

import de.rincewind.interfaceapi.InterfaceAPI;

public class InventoryDragListener implements Listener {

	@EventHandler(priority = EventPriority.NORMAL)
	public void onClick(InventoryDragEvent e) {
		Player player = (Player) e.getWhoClicked();

		if (InterfaceAPI.getSetup(player).hasMaximizedWindow()) {
			e.setCancelled(true);
		}
	}

}
