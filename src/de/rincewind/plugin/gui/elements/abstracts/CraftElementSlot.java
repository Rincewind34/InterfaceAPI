package de.rincewind.plugin.gui.elements.abstracts;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.abstracts.ElementSlot;

public abstract class CraftElementSlot extends CraftElement implements ElementSlot {
	
	private ItemStack content;
	
	public CraftElementSlot(Modifyable handle) {
		super(handle);
		
		this.content = Modifyable.EMPTY_USED_SLOT;
	}
	
	@Override
	public void updateItemMap() {
		this.setItemAt(new Point(0, 0), this.content);
	}

	@Override
	public boolean isEmpty() {
		return this.content == null || this.content.equals(Modifyable.EMPTY_USED_SLOT);
	}
	
	@Override
	public Runnable getRunnable(InventoryClickEvent event) {
		return () -> {
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
		};
	}

	public ItemStack getContent() {
		return this.content;
	}
	
	public void setContent(ItemStack item) {
		this.content = item;
		this.getHandle().updateItemMap(this);
	}
	
}
