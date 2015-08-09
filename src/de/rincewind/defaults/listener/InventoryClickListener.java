package de.rincewind.defaults.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.rincewind.api.gui.ClickAction;
import de.rincewind.api.gui.components.Locatable.Point;
import de.rincewind.api.gui.elements.abstracts.Element;
import de.rincewind.api.gui.windows.Window;
import de.rincewind.api.gui.windows.abstracts.WindowColorable;
import de.rincewind.plugin.InterfacePlugin.InterfaceAPI;
import de.rincewind.plugin.gui.elements.abstracts.CraftElement;
import de.rincewind.plugin.gui.windows.abstracts.CraftWindowContainer;
import de.rincewind.plugin.gui.windows.abstracts.CraftWindowEditor;

public class InventoryClickListener implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		
		if (e.getCurrentItem() != null) {
			int slot = e.getRawSlot();
			
			if (InterfaceAPI.getWindowManager().hasMaximizedWindow(player)) {
				if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
					e.setCancelled(true);
				}
				
				Window window = InterfaceAPI.getWindowManager().getMaximizedWindow(player);
				
				if (window instanceof CraftWindowContainer) {
					
					CraftWindowContainer containerWindow = (CraftWindowContainer) window;
					
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
							Element element = editor.getElementAt(point);
							
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
								((CraftElement) element).getRunnable(e).run();
								editor.readItemsFrom(element);
							}
						}
					}
				}
			}
		}
	}
	
}
