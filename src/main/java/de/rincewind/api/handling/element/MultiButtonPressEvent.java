package de.rincewind.api.handling.element;

import org.bukkit.entity.Player;

import de.rincewind.api.gui.elements.ElementMultiButton;

public class MultiButtonPressEvent extends PlayerElementEvent<ElementMultiButton> {

	public MultiButtonPressEvent(ElementMultiButton element, Player player) {
		super(element, player);
	}

}
