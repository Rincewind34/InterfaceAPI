package de.rincewind.plugin.gui.elements;

import java.util.function.Supplier;

import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.ElementBrick;
import de.rincewind.api.handling.events.ButtonPressEvent;
import lib.securebit.Validate;

public class CraftElementBrick extends CraftElementButton implements ElementBrick {

	private Supplier<ItemStack> creator;

	public CraftElementBrick(Modifyable handle) {
		super(handle);

		this.creator = () -> {
			return CraftElementBrick.this.getIcon().toItem().clone();
		};
	}

	@Override
	public void setCreator(Supplier<ItemStack> creator) {
		Validate.notNull(creator, "The creator cannot be null!");

		this.creator = creator;
	}

	@Override
	public ItemStack createItem() {
		return this.creator.get();
	}

	public void registerListener() {
		this.getEventManager().registerListener(ButtonPressEvent.class, (event) -> {
			event.getPlayer().setItemOnCursor(CraftElementBrick.this.createItem());
		}).addAfter();
	}

}
