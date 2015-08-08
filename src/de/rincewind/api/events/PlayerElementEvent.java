package de.rincewind.api.events;

import org.bukkit.entity.Player;

import de.rincewind.api.gui.elements.abstracts.Element;

public class PlayerElementEvent<T extends Element> extends DefaultElementEvent<T> {

	private Player player;
	
	public PlayerElementEvent(T element, Player player) {
		super(element);
		
		this.player = player;
	}
	
	public Player getPlayer() {
		return this.player;
	}

}
