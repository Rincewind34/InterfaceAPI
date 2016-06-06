package de.rincewind.plugin.gui.elements;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.ElementButton;
import de.rincewind.api.handling.events.ButtonPressEvent;
import de.rincewind.plugin.gui.elements.abstracts.CraftElementSizeable;

public class CraftElementButton extends CraftElementSizeable implements ElementButton {
	
	public CraftElementButton(Modifyable handle) {
		super(handle);
	}
	
	@Override
	public void handleClick(InventoryClickEvent event) {
		this.getEventManager().callEvent(new ButtonPressEvent(
				(Player) event.getWhoClicked(),
				this, event.isRightClick(),
				event.isShiftClick()));
	}

}
