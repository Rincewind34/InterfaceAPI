package de.rincewind.plugin.gui.elements;

import java.util.ArrayList;
import java.util.List;

import lib.securebit.Validate;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import de.rincewind.api.events.ListSelectEvent;
import de.rincewind.api.events.ListUnselectEvent;
import de.rincewind.api.gui.Color;
import de.rincewind.api.gui.Directionality;
import de.rincewind.api.gui.EventManager;
import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.ElementList;
import de.rincewind.plugin.gui.elements.abstracts.CraftElementSizeable;
import de.rincewind.util.item.ItemLibary;

public class CraftElementList extends CraftElementSizeable implements ElementList {

	private Color color;
	
	private int selected;
	private int startIndex;
	
	private List<ItemStack> items;
	
	private Directionality type;
	
	private EventManager eventManager;
	
	private SelectModifyer modifyer;
	
	public CraftElementList(Modifyable handle) {
		super(handle);
		
		this.selected = -1;
		this.startIndex = 0;
		this.type = Directionality.VERTICAL;
		this.items = new ArrayList<>();
		this.modifyer = (item) -> { 
			return ItemLibary.refactor().enchantItem(item.clone(), Enchantment.ARROW_INFINITE, 1, true);
		};
		
		this.color = Color.TRANSLUCENT;
		this.eventManager = new EventManager();
	}
	
	@Override
	public void updateItemMap() {
		if (this.type == Directionality.HORIZONTAL) {
			for (int y = 0; y < this.getHeight(); y++) {
				int index = this.startIndex + y;
				ItemStack item = null;
				
				if (this.items.size() <= index) {
					item = this.getColor().asItem();
				} else {
					item = this.items.get(index);
				}
				
				for (int x = 0; x < this.getWidth(); x++) {
					Point point = new Point(x, y);
					
					if (this.selected == index) {
						this.setItemAt(point, this.modifyToSelect(item));
					} else {
						this.setItemAt(point, item.clone());
					}
				}
			}
		} else {
			for (int x = 0; x < this.getWidth(); x++) {
				int index = this.startIndex + x;
				ItemStack item = null;
				
				if (this.items.size() <= index) {
					item = this.getColor().asItem();
				} else {
					item = this.items.get(index);
				}
				
				for (int y = 0; y < this.getHeight(); y++) {
					Point point = new Point(x, y);
					
					if (this.selected == index) {
						this.setItemAt(point, this.modifyToSelect(item));
					} else {
						this.setItemAt(point, item.clone());
					}
				}
			}
		}
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}
	
	@Override
	public void setSelectModifyer(SelectModifyer modifyer) {
		this.modifyer = modifyer;
	}
	
	@Override
	public ItemStack modifyToSelect(ItemStack item) {
		return this.modifyer.modify(item);
	}

	@Override
	public Color getColor() {
		return this.color;
	}

	@Override
	public void addItem(ItemStack item) {
		Validate.notNull(item, "The item cannot be null!");
		
		this.items.add(item);
		this.getHandle().updateItemMap(this);
	}

	@Override
	public void removeItem(ItemStack item) {
		Validate.notNull(item, "The item cannot be null!");
		
		this.items.remove(item);
		this.getHandle().updateItemMap(this);
	}

	@Override
	public void setType(Directionality type) {
		this.type = type;
		this.getHandle().updateItemMap(this);
	}

	@Override
	public void setStartIndex(int index) {
		if (0 > index || index > this.items.size() - 1) {
			return;
		}
		
		this.startIndex = index;
		this.getHandle().updateItemMap(this);
	}

	@Override
	public int getStartIndex() {
		return this.startIndex;
	}

	@Override
	public int getSelected() {
		return this.selected;
	}

	@Override
	public void select(int index) {
		if (0 <= index && index < this.items.size()) {
			this.selected = index + this.startIndex;
			this.getHandle().updateItemMap(this);
			this.eventManager.callEvent(new ListSelectEvent(this));
		}
	}

	@Override
	public void unselect() {
		this.selected = -1;
		this.getHandle().updateItemMap(this);
		this.eventManager.callEvent(new ListUnselectEvent(this));
	}

	@Override
	public List<ItemStack> getItems() {
		return this.items;
	}
	
	@Override
	public EventManager getEventManager() {
		return this.eventManager;
	}
	
	@Override
	public Runnable getRunnable(InventoryClickEvent event) {
		return () -> {
			if (event.getCurrentItem().equals(getColor().asItem())) {
				return;
			}
			
			InventoryType invType = event.getInventory().getType();
			
			int slot = event.getRawSlot();
			int invWidth = invType == InventoryType.DISPENSER ? 3 : (invType == InventoryType.HOPPER ? 5 : 9);
			
			if (this.type == Directionality.VERTICAL) {
				select(slot - ((int) ((double) slot / (double) invWidth) * invWidth) - this.getPoint().getX());
			} else {
				select((int) ((double) slot / (double) invWidth) - this.getPoint().getY());
			}
			
			this.getHandle().updateItemMap(this);
		};
	}

	@Override
	public void onAdd() {
		
	}

}
