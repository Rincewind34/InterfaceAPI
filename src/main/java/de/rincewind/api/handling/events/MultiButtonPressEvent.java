package de.rincewind.api.handling.events;

import org.bukkit.entity.Player;

import de.rincewind.api.gui.elements.ElementMultiButton;

public class MultiButtonPressEvent extends PlayerElementEvent<ElementMultiButton> {

	public MultiButtonPressEvent(ElementMultiButton element, Player player) {
		super(element, player);
	}

}
