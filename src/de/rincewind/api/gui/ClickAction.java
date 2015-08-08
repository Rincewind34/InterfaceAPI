package de.rincewind.api.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.event.inventory.InventoryAction;

public enum ClickAction {
	
	PICKUP(Arrays.asList(InventoryAction.PICKUP_ALL, InventoryAction.PICKUP_HALF, InventoryAction.PICKUP_ONE, InventoryAction.PICKUP_SOME)), 
	PLACE(Arrays.asList(InventoryAction.PLACE_ALL, InventoryAction.PLACE_ONE, InventoryAction.PLACE_SOME));
	
	private List<InventoryAction> actions;
	
	private ClickAction(List<InventoryAction> actions) {
		this.actions = actions;
	}
	
	public List<InventoryAction> getActions() {
		return Collections.unmodifiableList(this.actions);
	}
	
	public static List<InventoryAction> getBlockableActions() {
		List<InventoryAction> actions = new ArrayList<>();
		
		for (ClickAction action : ClickAction.values()) {
			actions.addAll(action.getActions());
		}
		
		return actions;
	}
	
}
