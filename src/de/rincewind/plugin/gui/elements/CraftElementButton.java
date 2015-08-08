package de.rincewind.plugin.gui.elements;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.rincewind.api.events.ButtonPressEvent;
import de.rincewind.api.gui.EventManager;
import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.ElementButton;
import de.rincewind.plugin.gui.elements.abstracts.CraftElementSizeable;

public class CraftElementButton extends CraftElementSizeable implements ElementButton {
	
	private EventManager manager;
	
	public CraftElementButton(Modifyable handle) {
		super(handle);
		
		this.manager = new EventManager();
	}
	
	@Override
	public EventManager getEventManager() {
		return this.manager;
	}
	
	@Override
	public Runnable getRunnable(InventoryClickEvent event) {
		return () -> {
			this.manager.callEvent(new ButtonPressEvent((Player) event.getWhoClicked(), this, event.isRightClick(), event.isShiftClick()));
		};
	}

	@Override
	public void onAdd() {
		
	}

}
