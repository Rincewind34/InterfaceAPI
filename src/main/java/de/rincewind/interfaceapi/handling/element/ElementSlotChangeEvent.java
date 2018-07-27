package de.rincewind.interfaceapi.handling.element;

import org.bukkit.inventory.ItemStack;

import de.rincewind.interfaceapi.gui.elements.abstracts.ElementSlot;

public class ElementSlotChangeEvent extends ElementValueChangeEvent<ElementSlot> {

	private ItemStack previousItem;
	private ItemStack item;

	public ElementSlotChangeEvent(ElementSlot element, ItemStack previousItem, ItemStack item) {
		super(element, item);

		this.previousItem = previousItem;
		this.item = item;
	}

	public ItemStack getPreviousItem() {
		return this.previousItem;
	}

	public ItemStack getItem() {
		return this.item;
	}

}
