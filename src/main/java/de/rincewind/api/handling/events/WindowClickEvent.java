package de.rincewind.api.handling.events;

import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.elements.util.ClickAction;
import de.rincewind.api.gui.elements.util.Point;
import de.rincewind.api.gui.windows.abstracts.WindowContainer;
import de.rincewind.plugin.gui.windows.abstracts.CraftWindowContainer;

public class WindowClickEvent extends WindowEvent<WindowContainer> {

	private boolean cancle;
	private boolean removeItem;
	private boolean leftClick;
	private boolean shiftClick;

	private ClickAction action;

	private int slot;
	private int rawSlot;

	private ItemStack item;

	public WindowClickEvent(WindowContainer window, ClickAction action, int rawSlot, int slot, ItemStack item, boolean leftClick, boolean shiftClick) {
		super(window);

		this.action = action;
		this.slot = slot;
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
		return this.rawSlot == this.slot;
	}

	public ClickAction getAction() {
		return this.action;
	}

	public int getRawSlot() {
		return this.rawSlot;
	}

	public int getInventorySlot() {
		if (this.isInInterface()) {
			throw new RuntimeException("The click was in interface!");
		}

		return this.slot;
	}

	public Point getInterfacePoint() {
		if (!this.isInInterface()) {
			throw new RuntimeException("The click was in the inventory!");
		}

		return ((CraftWindowContainer) this.getWindow()).getPoint(this.slot);
	}

	public ItemStack getItem() {
		if (this.isInInterface()) {
			throw new RuntimeException("The click was in the interface!");
		}
		
		return this.item;
	}

}
