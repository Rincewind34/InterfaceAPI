package de.rincewind.api.handling.events;

import org.bukkit.entity.Player;

import de.rincewind.api.gui.elements.ElementOutput;

public class OutputConsumeEvent extends ElementEvent<ElementOutput> {
	
	private Player clicker;
	
	public OutputConsumeEvent(ElementOutput element, Player clicker) {
		super(element);
		
		this.clicker = clicker;
	}
	
	public Player getPlayer() {
		return this.clicker;
	}
	
}
