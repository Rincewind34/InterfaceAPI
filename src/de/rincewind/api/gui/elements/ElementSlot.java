package de.rincewind.api.gui.elements;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.Element.ElementExtendable;
import de.rincewind.api.gui.windows.WindowSizeable;
import de.rincewind.api.gui.windows.abstracts.WindowEditor;

public abstract class ElementSlot extends ElementExtendable {
	
	/**
	 * @param handle The window for this element
	 */
	public ElementSlot(Modifyable handle) {
		super(handle);
	}
	
	@Override
	public void setPosition(int x, int y) {
		super.setPosition(x, y);
		this.setItemAt(0, 0, Modifyable.EMPTY_USED_SLOT);
		super.getHandle().updateItemMap(this);
	}

	/**
	 * @return The itemstasck at this slot
	 */
	protected ItemStack getItem() {
		WindowEditor handle = (WindowEditor) super.getHandle();
		return handle.getInventory().getItem(getSlot());
	}
	
	/**
	 * @return If noting or Material.AIR is on this slot; else false.
	 */
	protected boolean isEmpty() {
		return getItem() == null && getIcon().getType() != Material.AIR;
	}
	
	/**
	 * Sets an itemstack to this slot
	 * @param item The itemstack to set on this slot
	 */
	protected void sendItem(ItemStack item) {
		WindowEditor handle = (WindowEditor) super.getHandle();
		handle.getInventory().setItem(getSlot(), item);
	}
	
	/**
	 * @return The inventory-slot (minecraft format) of this slot
	 */
	protected int getSlot() {
		WindowEditor handle = (WindowEditor) super.getHandle();
		return getX() + getY() * (handle instanceof WindowSizeable ? ((WindowSizeable) handle).getWidth() : 1);
	}
	
}
