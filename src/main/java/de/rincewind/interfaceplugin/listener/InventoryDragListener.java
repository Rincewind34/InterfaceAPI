package de.rincewind.interfaceplugin.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;

import de.rincewind.interfaceapi.InterfaceAPI;
import de.rincewind.interfaceapi.gui.windows.abstracts.Window;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowContainer;

public class InventoryDragListener implements Listener {

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onClick(InventoryDragEvent event) {
		Player player = (Player) event.getWhoClicked();

		if (InterfaceAPI.getSetup(player).hasMaximizedWindow()) {
			Window window = InterfaceAPI.getSetup(player).getMaximizedWindow();
			
			if (window instanceof WindowContainer) {
				for (int index = 0; index < ((WindowContainer) window).getPoints().size(); index++) {
					if (event.getInventorySlots().contains(index)) {
						event.setCancelled(true);
						return;
					}
				}
			}
		}
	}

}
