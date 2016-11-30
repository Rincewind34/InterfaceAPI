package de.rincewind.plugin.gui.elements;

import org.bukkit.entity.Player;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.ElementButton;
import de.rincewind.api.gui.elements.abstracts.Element;
import de.rincewind.api.handling.element.ButtonPressEvent;
import de.rincewind.api.handling.element.ElementInteractEvent;
import de.rincewind.plugin.gui.elements.abstracts.CraftElementDisplayable;

public class CraftElementButton extends CraftElementDisplayable implements ElementButton {
	
	public CraftElementButton(Modifyable handle) {
		super(handle);
		
		this.getComponent(Element.WIDTH).setEnabled(true);
		this.getComponent(Element.HEIGHT).setEnabled(true);
		
		this.getEventManager().registerListener(ElementInteractEvent.class, (event) -> {
			this.getEventManager().callEvent(ButtonPressEvent.class,
					new ButtonPressEvent((Player) event.getPlayer(), this, !event.isLeftClick(), event.isShiftClick()));
		}).addAfter();
	}
	
}
