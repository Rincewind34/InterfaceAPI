package de.rincewind.api.adapter;

import org.bukkit.event.inventory.InventoryClickEvent;

import de.rincewind.api.gui.elements.Element;
import de.rincewind.api.listener.ElementActionListener;

public class ElementActionAdapter implements ElementActionListener {

	@Override
	public void onRightClick(InventoryClickEvent event, Element element) {
		this.onClick(event, element);
	}

	@Override
	public void onLeftClick(InventoryClickEvent event, Element element) {
		this.onClick(event, element);
	}
	
	public void onClick(InventoryClickEvent event, Element element) {
		
	}

}
