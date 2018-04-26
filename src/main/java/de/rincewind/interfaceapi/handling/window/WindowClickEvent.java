package de.rincewind.interfaceapi.handling.window;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import de.rincewind.interfaceapi.gui.elements.util.ClickAction;
import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowContainer;
import de.rincewind.interfaceplugin.gui.windows.abstracts.CraftWindowContainer;

public class WindowClickEvent extends WindowEvent<WindowContainer> {

	private boolean cancel;
	private boolean removeItem;
	private boolean isInInterface;

	private ClickAction action;
	private ClickType type;

	private int slot;

	private ItemStack item;

	public WindowClickEvent(WindowContainer window, ClickAction action, boolean isInInterface, int slot, ItemStack item, ClickType type) {
		super(window);

		this.action = action;
		this.slot = slot;
		this.item = item;
		this.type = type;
		this.isInInterface = isInInterface;
	}

	public void cancelInteraction() {
		if (this.isInInterface) {
			throw new RuntimeException("The click was in interface!");
		}
		
		this.cancel = true;
	}

	public void removeClickedItem() {
		this.removeItem = true;
	}

	public boolean isCancelled() {
		return this.cancel;
	}

	public boolean removeItem() {
		if (this.isInInterface) {
			throw new RuntimeException("The click was in interface!");
		}
		
		return this.removeItem;
	}

	public boolean isInInterface() {
		return this.isInInterface;
	}

	public ClickAction getAction() {
		return this.action;
	}

	public ClickType getType() {
		return this.type;
	}

	public int getInventorySlot() {
		if (this.isInInterface) {
			throw new RuntimeException("The click was in interface!");
		}

		return this.slot;
	}

	public Point getInterfacePoint() {
		if (!this.isInInterface) {
			throw new RuntimeException("The click was in the inventory!");
		}

		return ((CraftWindowContainer) this.getWindow()).getPoint(this.slot);
	}

	public ItemStack getItem() {
		if (this.isInInterface) {
			throw new RuntimeException("The click was in the interface!");
		}

		return this.item;
	}

}
