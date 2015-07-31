package de.rincewind.api.gui.elements;

import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.components.Modifyable;

public class ElementOutput extends ElementSlot {

	/**
	 * @param handle The window for this element
	 */
	public ElementOutput(Modifyable handle) {
		super(handle);
		
		super.setBlocked(getX(), getY(), false);
		
		for (InventoryAction action : InventoryAction.values()) {
			if (action == InventoryAction.PICKUP_ONE ||
					action == InventoryAction.PICKUP_HALF ||
					action == InventoryAction.PICKUP_SOME ||
					action == InventoryAction.PICKUP_ALL) continue;
			super.setBlocked(getX(), getY(), action);
		}
	}
	
	/**
	 * Sends an itemstack to this element; but only if this slot is empty
	 * @param item The itemstack to send
	 */
	public void output(ItemStack item) {
		this.output(item, false);
	}
	
	public boolean hasSpace() {
		return super.isEmpty();
	}
	
	/**
	 * Sends an itemstack to this element; but only if this slot is empty
	 * @param item The itemstack to send
	 * @param flag If true and there is already an item; this will be overriden
	 */
	public void output(ItemStack item, boolean flag) {
		if (!flag && !super.isEmpty()){
			return;
		} else {
			super.sendItem(item);
		}
	}

}
