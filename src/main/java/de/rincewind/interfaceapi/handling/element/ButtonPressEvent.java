package de.rincewind.interfaceapi.handling.element;

import org.bukkit.entity.Player;

import de.rincewind.interfaceapi.gui.elements.ElementButton;

public class ButtonPressEvent extends PlayerElementEvent<ElementButton> {

	private boolean rightClick;
	private boolean shiftClick;
	
	public ButtonPressEvent(Player player, ElementButton element, boolean rightClick, boolean shiftClick) {
		super(element, player);
		
		this.rightClick = rightClick;
		this.shiftClick = shiftClick;
	}
	
	public boolean isRightClick() {
		return this.rightClick;
	}
	
	public boolean isShiftClick() {
		return this.shiftClick;
	}
	
}
