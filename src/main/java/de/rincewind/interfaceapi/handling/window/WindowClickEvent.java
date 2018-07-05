package de.rincewind.interfaceapi.handling.window;

import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import de.rincewind.interfaceapi.gui.elements.util.ClickAction;
import de.rincewind.interfaceapi.gui.util.Point;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowContainer;
import de.rincewind.interfaceplugin.gui.windows.abstracts.CraftWindowContainer;

public class WindowClickEvent extends WindowEvent<WindowContainer> {

	private boolean cancel;
	private boolean removeItem;
	private boolean isInInterface;

	private ClickAction action;
	private ClickType type;

	private int slot;

	private ItemStack courserItem;
	private ItemStack clickedItem;

	public WindowClickEvent(WindowContainer window, ClickAction action, boolean isInInterface, int slot, ItemStack courserItem, ItemStack clickedItem,
			ClickType type) {
		super(window);

		this.action = action;
		this.slot = slot;
		this.courserItem = courserItem;
		this.clickedItem = clickedItem;
		this.type = type;
		this.isInInterface = isInInterface;

		assert this.courserItem == null || this.courserItem.getType() != Material.AIR : "The courser item is AIR";
		assert this.clickedItem == null || this.clickedItem.getType() != Material.AIR : "The clicked item is AIR";
	}

	public void cancelInteraction() {
		this.validateMonitor();
		this.cancel = true;
	}

	public void removeClickedItem() {
		this.validateMonitor();
		this.removeItem = true;
	}
	
	public void setCourserItem(ItemStack courserItem) {
		this.validateMonitor();
		this.courserItem = courserItem;
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

	public ItemStack getCourserItem() {
		return this.courserItem;
	}

	public ItemStack getClickedItem() {
		return this.clickedItem;
	}

}
