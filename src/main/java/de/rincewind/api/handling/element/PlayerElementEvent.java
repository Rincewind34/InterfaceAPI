package de.rincewind.api.handling.element;

import org.bukkit.entity.Player;

import de.rincewind.api.gui.elements.abstracts.Element;

public class PlayerElementEvent<T extends Element> extends ElementEvent<T> {

	private Player player;
	
	public PlayerElementEvent(T element, Player player) {
		super(element);
		
		this.player = player;
	}
	
	public Player getPlayer() {
		return this.player;
	}

}
