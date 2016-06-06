package de.rincewind.api.handling.events;

import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.windows.abstracts.WindowContainer;

public class WindowClickEvent extends WindowEvent<WindowContainer> {
	
	private boolean cancle;
	private boolean removeItem;
	
	private int rawSlot;
	
	private ItemStack item;
	
	public WindowClickEvent(WindowContainer window, int rawSlot, ItemStack item) {
		super(window);
		
		this.rawSlot = rawSlot;
		this.item = item;
	}
	
	public void cancleInteraction() {
		this.cancle = true;
	}
	
	public void removeClickedItem() {
		this.removeItem = true;
	}
	
	public boolean isCancelled() {
		return this.cancle;
	}
	
	public boolean removeItem() {
		return this.removeItem;
	}
	
	public boolean isInInterface() {
		return this.getWindow().getBukkitSize() > this.rawSlot;
	}
	
	public int getRawSlot() {
		return this.rawSlot;
	}
	
	public ItemStack getItem() {
		return this.item;
	}

}
