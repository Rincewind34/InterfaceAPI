package de.rincewind.interfaceapi.handling.element;

import org.bukkit.entity.Player;

import de.rincewind.interfaceapi.gui.elements.ElementMultiButton;

public class MultiButtonPressEvent extends PlayerElementEvent<ElementMultiButton> {

	public MultiButtonPressEvent(ElementMultiButton element, Player player) {
		super(element, player);
	}

}
