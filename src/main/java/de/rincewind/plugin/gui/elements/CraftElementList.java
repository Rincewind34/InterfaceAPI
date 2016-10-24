package de.rincewind.plugin.gui.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import de.rincewind.api.exceptions.APIException;
import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.ElementButton;
import de.rincewind.api.gui.elements.ElementList;
import de.rincewind.api.gui.elements.util.Point;
import de.rincewind.api.gui.util.Color;
import de.rincewind.api.gui.util.Directionality;
import de.rincewind.api.handling.InterfaceListener;
import de.rincewind.api.handling.events.ButtonPressEvent;
import de.rincewind.api.handling.events.ListSelectEvent;
import de.rincewind.api.handling.events.ListUnselectEvent;
import de.rincewind.api.item.ItemLibary;
import de.rincewind.api.item.ItemModifier;
import de.rincewind.plugin.gui.elements.abstracts.CraftElementSizeable;
import lib.securebit.Validate;

public class CraftElementList<T> extends CraftElementSizeable implements ElementList<T> {

	private Color color;
	
	private int selected;
	private int startIndex;
	
	private List<ListItem<T>> items;
	
	private Directionality type;
	
	private ItemModifier modifier;
	
	public CraftElementList(Modifyable handle) {
		super(handle);
		
		this.selected = -1;
		this.startIndex = 0;
		this.type = Directionality.VERTICAL;
		this.items = new ArrayList<>();
		this.modifier = (item) -> { 
			ItemStack modified = ItemLibary.refactor().enchantItem(item.clone(), Enchantment.ARROW_INFINITE, 1, true);
			modified = ItemLibary.refactor().addAllFlags(modified);
			
			return modified;
		};
		
		this.color = Color.TRANSLUCENT;
	}
	
	@Override
	public void updateItemMap() {
		if (this.type == Directionality.HORIZONTAL) {
			for (int y = 0; y < this.getHeight(); y++) {
				int index = this.startIndex + y;
				ItemStack item = null;
				
				if (this.getSize() <= index) {
					item = this.getColor().asItem();
				} else {
					item = this.items.get(index).getItem();
				}
				
				for (int x = 0; x < this.getWidth(); x++) {
					Point point = new Point(x, y);
					
					if (this.selected == index) {
						this.setItemAt(point, this.modifyToSelect(item.clone()));
					} else {
						this.setItemAt(point, item.clone());
					}
				}
			}
		} else {
			for (int x = 0; x < this.getWidth(); x++) {
				int index = this.startIndex + x;
				ItemStack item = null;
				
				if (this.getSize() <= index) {
					item = this.getColor().asItem();
				} else {
					item = this.items.get(index).getItem();
				}
				
				for (int y = 0; y < this.getHeight(); y++) {
					Point point = new Point(x, y);
					
					if (this.selected == index) {
						this.setItemAt(point, this.modifyToSelect(item.clone()));
					} else {
						this.setItemAt(point, item.clone());
					}
				}
			}
		}
		
		this.updateItemMap(false);
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}
	
	@Override
	public void setSelectModifyer(ItemModifier modifier) {
		Validate.notNull(modifier, "The mnodifier cannot be null!");
		
		this.modifier = modifier;
	}
	
	@Override
	public ItemStack modifyToSelect(ItemStack item) {
		Validate.notNull(item, "The item cannot be null!");
		
		return this.modifier.modifyItem(item);
	}

	@Override
	public Color getColor() {
		return this.color;
	}

	@Override
	public void addItem(ListItem<T> item) {
		Validate.notNull(item, "The item cannot be null!");
		
		this.items.add(item);
		this.getHandle().readItemsFrom(this);
	}

	@Override
	public void removeItem(ListItem<T> item) {
		Validate.notNull(item, "The item cannot be null!");
		
		this.items.remove(item);
		this.getHandle().readItemsFrom(this);
	}

	@Override
	public void setType(Directionality type) {
		Validate.notNull(type, "The type cannot be null!");
		
		this.type = type;
		this.getHandle().readItemsFrom(this);
	}

	@Override
	public void setStartIndex(int index) {
		if (0 > index || index > this.getSize() - 1) {
			return;
		}
		
		this.startIndex = index;
		this.getHandle().readItemsFrom(this);
	}

	@Override
	public int getStartIndex() {
		return this.startIndex;
	}

	@Override
	public int getSelectedIndex() {
		return this.selected;
	}
	
	@Override
	public ListItem<T> getSelected() {
		if (!this.isSelected()) {
			return null;
		} else {
			return this.items.get(this.selected);
		}
	}

	@Override
	public void select(int index) {
		if (0 <= index && index < this.getSize()) {
			this.selected = index + this.startIndex;
			this.getHandle().readItemsFrom(this);
			this.getEventManager().callEvent(ListSelectEvent.class, new ListSelectEvent<T>(this));
		}
	}

	@Override
	public void unselect() {
		this.selected = -1;
		this.getHandle().readItemsFrom(this);
		this.getEventManager().callEvent(ListUnselectEvent.class, new ListUnselectEvent<T>(this));
	}

	@Override
	public List<ListItem<T>> getItems() {
		return Collections.unmodifiableList(this.items);
	}
	
	@Override
	public void handleClick(InventoryClickEvent event) {
		if (event.getCurrentItem().equals(this.getColor().asItem())) {
			return;
		}
		
		InventoryType invType = event.getInventory().getType();
		
		int slot = event.getRawSlot();
		int invWidth = invType == InventoryType.DISPENSER ? 3 : (invType == InventoryType.HOPPER ? 5 : 9);
		int index;
		
		if (this.type == Directionality.VERTICAL) {
			index = slot - ((int) ((double) slot / (double) invWidth) * invWidth) - this.getPoint().getX();
		} else {
			index = (int) ((double) slot / (double) invWidth) - this.getPoint().getY();
		}
		
		if (index + this.startIndex == this.getSelectedIndex()) {
			this.unselect();
		} else {
			this.select(index);
		}
		
		this.getHandle().readItemsFrom(this);
	}

	@Override
	public void addScroler(ElementButton btn, int value) {
		//TODO exception if the value is 0
		Validate.notNull(btn, "The button cannot be null!");
		
		btn.getEventManager().registerListener(ButtonPressEvent.class, new ActionHandler(value)).addAfter();
	}
	
	@Override
	public boolean isSelected() {
		return this.selected >= 0;
	}
	
	@Override
	public Iterator<ListItem<T>> iterator() {
		return this.items.iterator();
	}

	@Override
	public void select() {
		if (this.getSize() != 0) {
			this.select(0);
		} else {
			throw new APIException("Cannot select an item in this list!");
		}
	}

	@Override
	public int getSize() {
		return this.items.size();
	}
	
	private class ActionHandler implements InterfaceListener<ButtonPressEvent> {

		private int value;
		
		private ActionHandler(int value) {
			this.value = value;
		}
		
		@Override
		public void onAction(ButtonPressEvent event) {
			CraftElementList.this.setStartIndex(CraftElementList.this.getStartIndex() + (this.value * (event.isShiftClick() ? 2 : 1)));
		}
		
	}

}
