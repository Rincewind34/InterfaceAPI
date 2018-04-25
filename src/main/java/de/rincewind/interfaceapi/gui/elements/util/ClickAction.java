package de.rincewind.interfaceapi.gui.elements.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.event.inventory.InventoryAction;

/**
 * This class is a reimplementation of the {@link InventoryAction}. Each enum-field does contain a
 * list of inventory-actions matching to that field.
 * 
 * @author Rincewind34
 * @since 2.3.3
 * 
 * @see ClickBlocker
 */
public enum ClickAction {
	
	/**
	 * Contains:
	 * <ul>
	 *   <li>{@link InventoryAction#PICKUP_ALL}</li>
	 *   <li>{@link InventoryAction#PICKUP_HALF}</li>
	 *   <li>{@link InventoryAction#PICKUP_ONE}</li>
	 *   <li>{@link InventoryAction#PICKUP_SOME}</li>
	 * </ul>
	 */
	PICKUP(Arrays.asList(
			InventoryAction.PICKUP_ALL,
			InventoryAction.PICKUP_HALF,
			InventoryAction.PICKUP_ONE,
			InventoryAction.PICKUP_SOME)),
	
	/**
	 * Contains:
	 * <ul>
	 *   <li>{@link InventoryAction#PLACE_ALL}</li>
	 *   <li>{@link InventoryAction#PLACE_ONE}</li>
	 *   <li>{@link InventoryAction#PLACE_SOME}</li>
	 * </ul>
	 */
	PLACE(Arrays.asList(
			InventoryAction.PLACE_ALL,
			InventoryAction.PLACE_ONE,
			InventoryAction.PLACE_SOME));
	
	private List<InventoryAction> actions;
	
	private ClickAction(List<InventoryAction> actions) {
		this.actions = actions;
	}
	
	/**
	 * Returns the action-list.
	 * 
	 * @return the action-list
	 */
	public List<InventoryAction> getActions() {
		return Collections.unmodifiableList(this.actions);
	}
	
	public static ClickAction getAction(InventoryAction action) {
		for (ClickAction target : ClickAction.values()) {
			if (target.actions.contains(action)) {
				return target;
			}
		}
		
		return null;
	}
	
	/**
	 * Returns a list of all {@link InventoryAction}'s contained by the enum-fields. 
	 * 
	 * @return a list of all {@link InventoryAction}'s contained by the enum-fields
	 */
	public static Set<InventoryAction> getBlockableActions() {
		Set<InventoryAction> actions = new HashSet<>();
		
		for (ClickAction action : ClickAction.values()) {
			actions.addAll(action.actions);
		}
		
		return actions;
	}
	
}
