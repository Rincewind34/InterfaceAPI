package de.rincewind.defaults.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.rincewind.api.gui.ClickAction;
import de.rincewind.api.gui.elements.abstracts.Element;
import de.rincewind.api.gui.windows.Window;
import de.rincewind.api.gui.windows.abstracts.WindowColorable;
import de.rincewind.api.gui.windows.abstracts.WindowContainer;
import de.rincewind.api.gui.windows.abstracts.WindowEditor;
import de.rincewind.plugin.InterfacePlugin.InterfaceAPI;
import de.rincewind.plugin.gui.elements.abstracts.CraftElement;

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
				
				if (window instanceof WindowContainer) {
					WindowContainer containerWindow = (WindowContainer) window;
					
					if (!(containerWindow.getInventory().getSize() > slot)) {
						return;
					}
					
					if (containerWindow instanceof WindowEditor) {
						
						WindowEditor editor = (WindowEditor) window;
						
						int windowX = editor.getPositionX(slot);
						int windowY = editor.getPositionY(slot);
						
						if (!editor.hasSpaceAt(windowX, windowY)) {
						
							Element element = editor.getElementAt(windowX, windowY);
							
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
							}
						}
						
						if (editor instanceof WindowColorable) {
							WindowColorable colorableWindow = (WindowColorable) editor;
							
							if (e.getCurrentItem() != null && e.getCurrentItem().equals(colorableWindow.getColor().asItem())) {
								e.setCancelled(true);
								return;
							}
						}
					}
				}
			}
		}
	}
	
}
