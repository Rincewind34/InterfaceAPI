package de.rincewind.interfaceapi.handling.element;

import org.bukkit.entity.Player;

import de.rincewind.interfaceapi.gui.elements.ElementOutput;

public class OutputConsumeEvent extends PlayerElementEvent<ElementOutput> {
	
	public OutputConsumeEvent(ElementOutput element, Player clicker) {
		super(element, clicker);
	}
	
}
