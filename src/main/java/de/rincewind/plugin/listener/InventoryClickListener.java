package de.rincewind.plugin.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.rincewind.api.InterfaceAPI;
import de.rincewind.api.gui.elements.util.ClickAction;
import de.rincewind.api.gui.windows.WindowAnvil;
import de.rincewind.api.gui.windows.abstracts.Window;
import de.rincewind.api.gui.windows.abstracts.WindowContainer;
import de.rincewind.api.handling.window.AnvilNameEvent;
import de.rincewind.api.handling.window.WindowClickEvent;

public class InventoryClickListener implements Listener {

	@EventHandler(priority = EventPriority.NORMAL)
	public void onClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();

		if (event.getCurrentItem() != null) {
			if (InterfaceAPI.getSetup(player).hasMaximizedWindow()) {
				Window window = InterfaceAPI.getSetup(player).getMaximizedWindow();
				ClickAction action = ClickAction.getAction(event.getAction());
				
				if (action == null) {
					event.setCancelled(true);
					return;
				}
				
				if (window instanceof WindowContainer) {
					WindowContainer containerWindow = (WindowContainer) window;

					WindowClickEvent windowEvent = new WindowClickEvent(containerWindow, action, event.getRawSlot(), event.getSlot(), event.getCurrentItem(),
							event.isLeftClick(), event.isShiftClick());
					containerWindow.getEventManager().callEvent(WindowClickEvent.class, windowEvent);

					if (event.isCancelled() || !ClickAction.getBlockableActions().contains(event.getAction())) {
						event.setCancelled(true);
					}

					if (windowEvent.removeItem() && !windowEvent.isInInterface()) {
						player.getInventory().setItem(event.getSlot(), null);
					}
				} else if (window instanceof WindowAnvil) {
					event.setCancelled(true);

					if (event.getRawSlot() == 2) {
						window.getEventManager().callEvent(AnvilNameEvent.class,
								new AnvilNameEvent((WindowAnvil) window, ((WindowAnvil) window).getInsertedName()));
					}

					InterfaceAPI.getSetup(player).close(window);
				}
			}
		}
	}

}
