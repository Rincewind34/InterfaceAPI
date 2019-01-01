package de.rincewind.interfaceplugin.gui.elements;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import de.rincewind.interfaceapi.gui.elements.ElementItem;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.util.Point;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowEditor;
import de.rincewind.interfaceapi.handling.element.ElementInteractEvent;
import de.rincewind.interfaceplugin.gui.elements.abstracts.CraftElementDisplayable;

public class CraftElementItem extends CraftElementDisplayable implements ElementItem {
	
	public CraftElementItem(WindowEditor handle) {
		super(handle);

		this.getComponent(Element.WIDTH).setEnabled(true);
		this.getComponent(Element.HEIGHT).setEnabled(true);
	}
	
	/**
	 * Just for testing
	 */
	void pushAsButton(Player player, ClickType type) {
		this.getEventManager().callEvent(ElementInteractEvent.class,
				new ElementInteractEvent(this, player, Point.NULL, type, null));
	}

}
