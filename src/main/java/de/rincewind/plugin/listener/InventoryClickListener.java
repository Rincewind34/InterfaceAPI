package de.rincewind.plugin.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.rincewind.api.InterfaceAPI;
import de.rincewind.api.gui.elements.abstracts.Element;
import de.rincewind.api.gui.elements.util.ClickAction;
import de.rincewind.api.gui.elements.util.Point;
import de.rincewind.api.gui.windows.WindowAnvil;
import de.rincewind.api.gui.windows.abstracts.Window;
import de.rincewind.api.gui.windows.abstracts.WindowColorable;
import de.rincewind.api.handling.events.AnvilNameEvent;
import de.rincewind.api.handling.events.WindowClickEvent;
import de.rincewind.plugin.gui.elements.abstracts.CraftElement;
import de.rincewind.plugin.gui.windows.abstracts.CraftWindowContainer;
import de.rincewind.plugin.gui.windows.abstracts.CraftWindowEditor;

public class InventoryClickListener implements Listener {

	@EventHandler(priority = EventPriority.NORMAL)
	public void onClick(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();

		if (e.getCurrentItem() != null) {
			int slot = e.getRawSlot();

			if (InterfaceAPI.getSetup(player).hasMaximizedWindow()) {
				Window window = InterfaceAPI.getSetup(player).getMaximizedWindow();

				if (window instanceof CraftWindowContainer) {
					CraftWindowContainer containerWindow = (CraftWindowContainer) window;

					WindowClickEvent event = new WindowClickEvent(containerWindow, slot, e.getCurrentItem(), e.isLeftClick(), e.isShiftClick());
					containerWindow.getEventManager().callEvent(WindowClickEvent.class, event);

					if (event.isCancelled()) {
						e.setCancelled(true);
					}

					if (event.removeItem() && !event.isInInterface()) {
						player.getInventory().setItem(e.getSlot(), null);
					}

					if (!(containerWindow.getBukkitSize() > slot)) {
						return;
					}

					if (containerWindow instanceof CraftWindowEditor) {
						CraftWindowEditor editor = (CraftWindowEditor) window;

						if (editor instanceof WindowColorable) {
							WindowColorable colorableWindow = (WindowColorable) editor;

							if (e.getCurrentItem() != null && e.getCurrentItem().equals(colorableWindow.getColor().asItem())) {
								e.setCancelled(true);
								return;
							}
						}

						Point point = editor.getPoint(slot);

						if (!editor.hasSpaceAt(point)) {
							Element element = editor.getVisibleElementAt(point);

							if (element == null) {
								return;
							}

							// ==== ClickBlocker ==== //

							if (!ClickAction.getBlockableActions().contains(e.getAction())) {
								e.setCancelled(true);
							}

							if (element.getBlocker().isLocked()) {
								e.setCancelled(true);
							} else {
								for (ClickAction action : element.getBlocker().getBlocked()) {
									if (action.getActions().contains(e.getAction())) {
										e.setCancelled(true);
										break;
									}
								}
							}

							// ==== ClickBlocker ==== //

							if (element.isEnabled()) {
								((CraftElement) element).handleClick(e);
								editor.readItemsFrom(element);
							}
						}
					}
				} else if (window instanceof WindowAnvil) {
					e.setCancelled(true);

					if (slot == 2) {
						window.getEventManager().callEvent(AnvilNameEvent.class,
								new AnvilNameEvent((WindowAnvil) window, ((WindowAnvil) window).getInsertedName()));
					}

					InterfaceAPI.getSetup(player).close(window);
				}
			}
		}
	}

}
