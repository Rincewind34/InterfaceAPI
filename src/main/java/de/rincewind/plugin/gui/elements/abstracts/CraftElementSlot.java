package de.rincewind.plugin.gui.elements.abstracts;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.abstracts.ElementSlot;
import de.rincewind.api.gui.elements.util.Point;
import de.rincewind.api.item.ItemLibary;

public abstract class CraftElementSlot extends CraftElement implements ElementSlot {
	
	private ItemStack content;
	
	public CraftElementSlot(Modifyable handle) {
		super(handle);
		
		this.content = Modifyable.EMPTY_USED_SLOT;
	}
	
	@Override
	public void updateItemMap() {
		if (this.isEnabled()) {
			this.setItemAt(Point.atNull(), this.content);
		} else {
			this.setItemAt(Point.atNull(), ItemLibary.refactor().renameItem(new ItemStack(Material.BARRIER), " "));
		}
	}

	@Override
	public boolean isEmpty() {
		return this.content == null || this.content.equals(Modifyable.EMPTY_USED_SLOT);
	}
	
	@Override
	public void handleClick(InventoryClickEvent event) {
		if (event.isCancelled()) {
			return;
		} else {
			event.setCancelled(true);
		}
		
		if (event.getWhoClicked().getItemOnCursor() != null && event.getWhoClicked().getItemOnCursor().getType() != Material.AIR) {
			this.setContent(event.getWhoClicked().getItemOnCursor());
			event.getWhoClicked().setItemOnCursor(null);
		} else {
			event.getWhoClicked().setItemOnCursor(this.content);
			this.setContent(Modifyable.EMPTY_USED_SLOT);
		}
		
		System.out.println("DEBUG: (ElementSlot) Content: " + this.getContent() + ", Item: " + this.getItemAt(Point.atNull()));
	}
	
	@Override
	public ItemStack getCurrentContent() {
		return this.getContent().clone();
	}
	
	@Override
	public void setEnabled(boolean enable) {
		super.setEnabled(enable);
		
		if (enable) {
			this.getBlocker().unlock();
		} else {
			this.getBlocker().lock();
		}
	}

	public ItemStack getContent() {
		return this.content;
	}
	
	public void setContent(ItemStack item) {
		this.content = item;
		this.getHandle().readItemsFrom(this);
	}
	
}