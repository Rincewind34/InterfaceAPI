package de.rincewind.interfaceapi.handling.element;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.ClickAction;
import de.rincewind.interfaceapi.gui.util.Point;

public class ElementStackChangeEvent extends PlayerElementEvent<Element> {

	private boolean cancelled;

	private ClickAction action;

	private ItemStack slotItem;
	private ItemStack courserItem;

	private Point point;

	public ElementStackChangeEvent(Element element, Player player, Point point, ClickAction action, ItemStack courserItem,
			ItemStack slotItem) {
		super(element, player);

		this.action = action;
		this.point = point;
		this.courserItem = courserItem;
		this.slotItem = slotItem;

		assert this.courserItem == null || this.courserItem.getType() != Material.AIR : "The courser item is AIR";
		assert this.slotItem == null || this.slotItem.getType() != Material.AIR : "The slot item is AIR";
	}

	public void cancel() {
		this.cancelled = true;
	}

	public void setSlotItem(ItemStack slotItem) {
		this.validateMonitor();
		this.slotItem = slotItem;
	}

	public void setCourserItem(ItemStack courserItem) {
		this.validateMonitor();
		this.courserItem = courserItem;
	}

	public boolean remainItemsOnCourser() {
		return this.courserItem != null;
	}

	public boolean remainItemsInSlot() {
		return this.slotItem != null;
	}

	public boolean isCancelled() {
		return this.cancelled;
	}

	public ClickAction getAction() {
		return this.action;
	}

	public Point getPoint() {
		return this.point;
	}

	/**
	 * Cannot be AIR
	 * 
	 * @return
	 */
	public ItemStack getCourserItem() {
		return this.courserItem;
	}

	/**
	 * Cannot be AIR
	 * 
	 * @return
	 */
	public ItemStack getSlotItem() {
		return this.slotItem;
	}
}
