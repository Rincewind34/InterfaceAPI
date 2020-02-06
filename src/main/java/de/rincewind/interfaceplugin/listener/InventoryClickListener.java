package de.rincewind.interfaceplugin.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.rincewind.interfaceapi.InterfaceAPI;
import de.rincewind.interfaceapi.gui.elements.util.ClickAction;
import de.rincewind.interfaceapi.gui.windows.WindowAnvil;
import de.rincewind.interfaceapi.gui.windows.abstracts.Window;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowContainer;
import de.rincewind.interfaceapi.handling.window.AnvilNameEvent;
import de.rincewind.interfaceapi.handling.window.WindowClickEvent;
import de.rincewind.interfaceapi.util.InterfaceUtils;

public class InventoryClickListener implements Listener {

	@EventHandler(priority = EventPriority.NORMAL)
	public void onClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();

		if (InterfaceAPI.getSetup(player).hasMaximizedWindow()) {
			Window window = InterfaceAPI.getSetup(player).getMaximizedWindow();

			if (window instanceof WindowContainer) {
				WindowContainer containerWindow = (WindowContainer) window;
				ClickAction action = ClickAction.getAction(event.getAction());

				WindowClickEvent windowEvent = new WindowClickEvent(containerWindow, action,
						event.getClickedInventory() == event.getView().getTopInventory(), event.getSlot(),
						InterfaceUtils.normalize(event.getWhoClicked().getItemOnCursor()),
						InterfaceUtils.normalize(event.getCurrentItem()), event.getClick());

				containerWindow.getEventManager().callEvent(WindowClickEvent.class, windowEvent);

				// TODO (#1033)
				//				if (InterfaceUtils.normalize(event.getWhoClicked().getItemOnCursor()) != InterfaceUtils
				//						.normalize(windowEvent.getCourserItem())) {
				//					event.getWhoClicked().setItemOnCursor(windowEvent.getCourserItem());
				//				}

				if (windowEvent.isCancelled()) {
					event.setCancelled(true);
				}

				if (windowEvent.removeItem()) {
					assert !windowEvent.isInInterface() : "The item will be removed from interface";

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
