package de.rincewind.plugin.gui.elements;

import lib.securebit.Validate;

import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.ElementBrick;
import de.rincewind.api.handling.events.ButtonPressEvent;
import de.rincewind.api.handling.listener.ButtonPressListener;
import de.rincewind.api.item.ItemCreator;

public class CraftElementBrick extends CraftElementButton implements ElementBrick {
	
	private ItemCreator creator;
	
	public CraftElementBrick(Modifyable handle) {
		super(handle);
		
		this.creator = () -> {
			return CraftElementBrick.this.getIcon().toItem().clone();
		};
	}

	@Override
	public void setCreator(ItemCreator creator) {
		Validate.notNull(creator, "The creator cannot be null!");
		
		this.creator = creator;
	}

	@Override
	public ItemStack createItem() {
		return this.creator.createItem();
	}
	
	public void registerListener() {
		this.getEventManager().registerListener(new ButtonPressListener() {
			
			@Override
			public void onFire(ButtonPressEvent event) {
				event.getPlayer().setItemOnCursor(CraftElementBrick.this.createItem());
			}
		}).addAfter();
	}
	
}
