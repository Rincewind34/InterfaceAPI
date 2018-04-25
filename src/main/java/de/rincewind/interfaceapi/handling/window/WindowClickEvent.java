package de.rincewind.interfaceapi.handling.window;

import org.bukkit.inventory.ItemStack;

import de.rincewind.interfaceapi.gui.elements.util.ClickAction;
import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowContainer;
import de.rincewind.interfaceplugin.gui.windows.abstracts.CraftWindowContainer;

public class WindowClickEvent extends WindowEvent<WindowContainer> {

	private boolean cancel;
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

	public void cancelInteraction() {
		this.cancel = true;
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
		return this.cancel;
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
