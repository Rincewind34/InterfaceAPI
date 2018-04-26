package de.rincewind.interfaceapi.handling.element;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.Point;

public class ElementInteractEvent extends ElementEvent<Element> {

	private boolean leftClick;
	private boolean shiftClick;

	private ClickType type;
	private ItemStack courserItem;

	private Point point;
	private Player player;

	public ElementInteractEvent(Element element, Player player, Point point, ClickType type, ItemStack courserItem) {
		super(element);

		this.type = type;
		this.point = point;
	}

	public boolean isLeftClick() {
		return this.leftClick;
	}

	public boolean isShiftClick() {
		return this.shiftClick;
	}

	public ClickType getType() {
		return this.type;
	}

	public ItemStack getCourserItem() {
		return this.courserItem;
	}

	public Point getPoint() {
		return this.point;
	}

	public Player getPlayer() {
		return this.player;
	}

}
