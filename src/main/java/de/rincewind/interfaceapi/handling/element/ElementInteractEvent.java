package de.rincewind.interfaceapi.handling.element;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.util.Point;

public class ElementInteractEvent extends PlayerElementEvent<Element> {
	
	private boolean cancelled;
	
	private ClickType type;
	private ItemStack courserItem;

	private Point point;

	public ElementInteractEvent(Element element, Player player, Point point, ClickType type, ItemStack courserItem) {
		super(element, player);

		this.type = type;
		this.point = point;
		this.courserItem = courserItem;

		assert this.courserItem == null || this.courserItem.getType() != Material.AIR : "The courser item is AIR";
	}
	
	public void cancel() {
		this.validateMonitor();
		this.cancelled = true;
	}
	
	public void setCourserItem(ItemStack courserItem) {
		this.validateMonitor();
		this.courserItem = courserItem;
	}

	public boolean isRightClick() {
		return this.type == ClickType.RIGHT || this.type == ClickType.SHIFT_RIGHT;
	}

	public boolean isLeftClick() {
		return this.type == ClickType.LEFT || this.type == ClickType.SHIFT_LEFT;
	}

	public boolean isShiftClick() {
		return this.type == ClickType.SHIFT_LEFT || this.type == ClickType.SHIFT_RIGHT;
	}
	
	public boolean isCourserItemPresent() {
		return this.courserItem != null;
	}
	
	public boolean isCancelled() {
		return this.cancelled;
	}

	public ClickType getClickType() {
		return this.type;
	}

	/**
	 * Cannot be AIR
	 * 
	 * @return
	 */
	public ItemStack getCourserItem() {
		return this.courserItem;
	}

	public Point getPoint() {
		return this.point;
	}

}
