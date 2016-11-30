package de.rincewind.interfaceplugin.gui.elements;

import org.bukkit.entity.Player;

import de.rincewind.interfaceapi.gui.components.Modifyable;
import de.rincewind.interfaceapi.gui.elements.ElementButton;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.handling.element.ButtonPressEvent;
import de.rincewind.interfaceapi.handling.element.ElementInteractEvent;
import de.rincewind.interfaceplugin.gui.elements.abstracts.CraftElementDisplayable;

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
