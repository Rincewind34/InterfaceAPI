package de.rincewind.api.gui.elements;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.abstracts.Element;

public class ElementBrick extends ElementButton {

	private static Map<Player, ElementBrick> users = new HashMap<Player, ElementBrick>();
	
	private Element toCreate;
	
	public ElementBrick(Modifyable handle) {
		super(handle);
		
		this.addActionListener(new ElementActionListener() {
			
			@Override
			public void onRightClick(InventoryClickEvent event, Element element) {
				this.click(event, element);
			}
			
			@Override
			public void onLeftClick(InventoryClickEvent event, Element element) {
				this.click(event, element);
			}
			
			private void click(InventoryClickEvent event, Element element) {
				if (!ElementBrick.isUsedBy((Player) event.getWhoClicked())) {
					event.getWhoClicked().setItemOnCursor(getIcon().clone());
					ElementBrick.setUsed((Player) event.getWhoClicked(), true, ElementBrick.this);
				}
			}
			
		});
	}
	
	public void setToCreate(Element element) {
		this.toCreate = element;
	}
	
	public Element getToCreate() {
		return this.toCreate;
	}
	
	public static ElementBrick getBrick(Player player) {
		if (!ElementBrick.isUsedBy(player)) {
			return ElementBrick.users.get(player);
		} else {
			return null;
		}
	}
	
	public static boolean isUsedBy(Player player) {
		return ElementBrick.users.containsKey(player);
	}
	
	public static void setUsed(Player player, boolean used, ElementBrick brick) {
		if(used && !isUsedBy(player)) {
			if(brick != null) {
				ElementBrick.users.put(player, brick);
			}
		} else if(!used && isUsedBy(player)) {
			ElementBrick.users.remove(player);
		}
	}
	
}
