package de.rincewind.api.handling.events;

import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.windows.abstracts.WindowContainer;

public class WindowClickEvent extends WindowEvent<WindowContainer> {
	
	private boolean cancle;
	private boolean removeItem;
	private boolean leftClick;
	private boolean shiftClick;
	
	private int rawSlot;
	
	private ItemStack item;
	
	public WindowClickEvent(WindowContainer window, int rawSlot, ItemStack item, boolean leftClick, boolean shiftClick) {
		super(window);
		
		this.rawSlot = rawSlot;
		this.item = item;
		this.leftClick = leftClick;
		this.shiftClick = shiftClick;
	}
	
	public void cancleInteraction() {
		this.cancle = true;
	}
	
	public void removeClickedItem() {
		this.removeItem = true;
	}
	
	public boolean isLeftClick() {
		return this.leftClick;
	}
	
	public boolean isRightClick() {
		return !this.leftClick;
	}
	
	public boolean isShiftClick() {
		return this.shiftClick;
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
	
	public int getSlot() {
		if (this.isInInterface()) {
			return this.getRawSlot();
		} else {
			return this.getRawSlot() - this.getWindow().getBukkitSize();
		}
	}
	
	public ItemStack getItem() {
		return this.item;
	}

}
