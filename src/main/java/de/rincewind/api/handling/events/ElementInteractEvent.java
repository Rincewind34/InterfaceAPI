package de.rincewind.api.handling.events;

import org.bukkit.entity.Player;

import de.rincewind.api.gui.elements.abstracts.Element;
import de.rincewind.api.gui.elements.util.ClickAction;
import de.rincewind.api.gui.elements.util.Point;

public class ElementInteractEvent extends ElementEvent<Element> {

	private boolean leftClick;
	private boolean shiftClick;

	private ClickAction action;

	private Point point;
	private Player player;
	
	public ElementInteractEvent(Element element, Player player, Point point, ClickAction action, boolean shiftClick, boolean leftClick) {
		super(element);

		this.leftClick = leftClick;
		this.shiftClick = shiftClick;
		this.action = action;
		this.point = point;
	}

	public boolean isLeftClick() {
		return this.leftClick;
	}

	public boolean isShiftClick() {
		return this.shiftClick;
	}

	public ClickAction getAction() {
		return this.action;
	}

	public Point getPoint() {
		return this.point;
	}
	
	public Player getPlayer() {
		return this.player;
	}

}
