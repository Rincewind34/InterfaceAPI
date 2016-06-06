package de.rincewind.plugin.gui.elements;

import org.bukkit.event.inventory.InventoryClickEvent;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.ElementItem;
import de.rincewind.plugin.gui.elements.abstracts.CraftElementSizeable;

public class CraftElementItem extends CraftElementSizeable implements ElementItem {
	
	public CraftElementItem(Modifyable handle) {
		super(handle);
	}

	@Override
	public void handleClick(InventoryClickEvent event) {
		
	}

}
