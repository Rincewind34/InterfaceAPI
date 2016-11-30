package de.rincewind.api.handling.events;

import org.bukkit.entity.Player;

import de.rincewind.api.gui.elements.ElementOutput;

public class OutputConsumeEvent extends PlayerElementEvent<ElementOutput> {
	
	public OutputConsumeEvent(ElementOutput element, Player clicker) {
		super(element, clicker);
	}
	
}
