package de.rincewind.defaults.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.rincewind.api.gui.elements.Element;
import de.rincewind.api.gui.elements.ElementManager;
import de.rincewind.api.gui.windows.Window;
import de.rincewind.api.gui.windows.abstracts.WindowColorable;
import de.rincewind.api.gui.windows.abstracts.WindowContainer;
import de.rincewind.api.gui.windows.abstracts.WindowEditor;
import de.rincewind.api.gui.windows.abstracts.WindowNameable;
import de.rincewind.plugin.InterfacePlugin.InterfaceAPI;

public class InventoryClickListener implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		
		if (e.getCurrentItem() != null) {
			int slot = e.getRawSlot();
			
			if (InterfaceAPI.getWindowManager().hasMaximizedWindow(player)) {
				Window window = InterfaceAPI.getWindowManager().getMaximizedWindow(player);
				
				if (window instanceof WindowNameable) {
					if (window instanceof WindowContainer) {
						WindowContainer containerWindow = (WindowContainer) window;
						
						int size = containerWindow.getInventory().getSize();
						if(!(size > slot)) return;
						
						if (containerWindow instanceof WindowEditor) {
							
							WindowEditor editor = (WindowEditor) window;
							
							int windowX = editor.getPositionX(slot);
							int windowY = editor.getPositionY(slot);
							
							if(!editor.hasSpaceAt(windowX, windowY)) {
							
								Element element = editor.getElementAt(windowX, windowY);
								
								if(element == null) return;
								
								int elementX = windowX - element.getX();
								int elementY = windowY - element.getY();;
								
								if(element.isPointBlocked(elementX, elementY, e.getAction())) e.setCancelled(true);
								
								if(ElementManager.containsElement(element.getClass())) {
									e.setCancelled(ElementManager.runElementActivation(element.getClass(),
											element,
											e));
								}
								
								return;
							}
							
							if(editor instanceof WindowColorable) {
								
								WindowColorable colorableWindow = (WindowColorable) editor;
								
								if(e.getCurrentItem() != null && e.getCurrentItem().equals(colorableWindow.getColor().asItem())) {
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
	
}
