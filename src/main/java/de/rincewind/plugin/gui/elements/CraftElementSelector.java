package de.rincewind.plugin.gui.elements;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.ElementSelector;
import de.rincewind.api.gui.elements.util.Icon;
import de.rincewind.api.gui.elements.util.Point;
import de.rincewind.api.gui.windows.abstracts.Window;
import de.rincewind.api.handling.events.ItemSelectEvent;
import de.rincewind.api.handling.events.WindowClickEvent;
import de.rincewind.api.item.ItemSelector;
import de.rincewind.plugin.gui.elements.abstracts.CraftElementDisplayable;

public class CraftElementSelector extends CraftElementDisplayable implements ElementSelector {

	private boolean emptySlot;

	private boolean isSelecting;
	private boolean canUnselect;
	private boolean isPullingOnlyOne;

	private ItemStack selected;

	private ItemSelector selector;

	public CraftElementSelector(Modifyable handle) {
		super(handle);

		this.emptySlot = false;
		this.isSelecting = false;
		this.canUnselect = false;
		this.isPullingOnlyOne = false;
		this.selected = null;
		this.selector = (item) -> {
			return true;
		};
	}

	@Override
	public void startSelecting() {
		this.isSelecting = true;
	}

	@Override
	public void stopSelecting() {
		this.isSelecting = false;
	}

	@Override
	public void pullOnlyOne(boolean value) {
		this.isPullingOnlyOne = value;
	}

	@Override
	public void canUnselect(boolean value) {
		this.canUnselect = value;
	}

	@Override
	public void setSelected(ItemStack item) {
		this.setSelected(item, true);
	}

	@Override
	public void setSelected(ItemStack item, boolean fireEvent) {
		if (item != null && this.isPullingOnlyOne && item.getAmount() > 1) {
			ItemStack select = item.clone();
			select.setAmount(1);
			this.setSelected(select);
		}

		this.selected = item;
		this.getHandle().readItemsFrom(this);

		if (fireEvent) {
			this.getEventManager().callEvent(ItemSelectEvent.class, new ItemSelectEvent(this));
		}
	}

	@Override
	public void registerTarget(Window window) {
		window.getEventManager().registerListener(WindowClickEvent.class, (event) -> {
			if (CraftElementSelector.this.isSelecting) {
				if (!event.isInInterface() && event.getItem() != null && CraftElementSelector.this.selector.isMatching(event.getItem())) {
					CraftElementSelector.this.setSelected(event.getItem());
					event.cancleInteraction();
				}
			}
		}).addAfter();
	}

	@Override
	public void setSelector(ItemSelector selector) {
		this.selector = selector;
	}

	@Override
	public boolean isPullingOnlyOne() {
		return this.isPullingOnlyOne;
	}

	@Override
	public boolean canUnselect() {
		return this.canUnselect;
	}

	@Override
	public boolean isSelecting() {
		return this.isSelecting;
	}

	@Override
	public ItemStack getSelected() {
		return this.selected;
	}

	@Override
	public void handleClick(InventoryClickEvent event) {
		if (this.canUnselect) {
			this.setSelected(null);
		}
	}

	@Override
	public void updateItemMap() {
		if (this.selected == null) {
			if (this.emptySlot) {
				this.setItemAt(Point.atNull(), Modifyable.EMPTY_USED_SLOT);
				this.updateItemMap(Point.atNull(), false);
			} else {
				super.updateItemMap();
			}
		} else {
			this.setItemAt(Point.atNull(), this.selected);
			this.updateItemMap(Point.atNull(), false);
		}
	}

	@Override
	public void setIcon(Icon icon) {
		if (icon == null) {
			this.emptySlot = true;
			super.setIcon(new Icon(Modifyable.EMPTY_USED_SLOT));
		} else {
			this.emptySlot = false;
			super.setIcon(icon);
		}
	}

}
