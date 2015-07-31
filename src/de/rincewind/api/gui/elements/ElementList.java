package de.rincewind.api.gui.elements;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.Color;
import de.rincewind.api.gui.Directionality;
import de.rincewind.api.gui.components.Colorable;
import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.listener.ElementActionListener;
import de.rincewind.api.listener.ElementSelectListener;
import de.rincewind.api.listener.ElementUnselectListener;
import de.rincewind.plugin.gui.components.CraftColorable;
import de.rincewind.util.item.ItemLibary;

public class ElementList extends ElementButton implements Colorable {

	private CraftColorable colorable;
	
	private int selected;
	private int top;
	
	private List<ItemStack> items;
	
	private List<ElementSelectListener> selectListeners;
	private List<ElementUnselectListener> unselectListeners;
	
	private Directionality type;
	
	/**
	 * 
	 * @param handle The window for this element
	 */
	public ElementList(Modifyable handle) {
		super(handle);
		
		this.colorable = new CraftColorable();
		this.selected = -1;
		this.top = 0;
		this.type = Directionality.VERTICAL;
		this.items = new ArrayList<ItemStack>();
		this.selectListeners = new ArrayList<ElementSelectListener>();
		this.unselectListeners = new ArrayList<ElementUnselectListener>();
		
		this.setColor(Color.TRANSLUCENT);
		this.registerListener();
	}

	@Override
	public boolean setColor(Color color) {
		return this.colorable.setColor(color);
	}

	@Override
	public Color getColor() {
		return this.colorable.getColor();
	}
	
	/**
	 * Adds an item to the list
	 * 
	 * @param item The new item
	 */
	public void addItem(ItemStack item) {
		if (item == null) {
			throw new NullPointerException("The item cannot be null");
		} else {
			this.items.add(item);
		}
		
		this.configureItems();
	}
	
	/**
	 * Removes an item to the list
	 * 
	 * @param item The old item
	 */
	public void removeItem(ItemStack item) {
		if (item == null) {
			throw new NullPointerException("The item cannot be null");
		} else {
			this.items.remove(item);
		}
		
		this.configureItems();
	}
	
	/**
	 * Updates the list to the window
	 */
	public void configureItems() {
		if (this.type == Directionality.HORIZONTAL) {
			for (int y = 0; y < this.getHeigth(); y++) {
				int index = this.top + y;
				ItemStack item = null;
				
				if (this.items.size() <= index) {
					item = this.getColor().asItem();
				} else {
					item = this.items.get(index);
				}
				
				for (int x = 0; x < this.getWidth(); x++) {
					if (this.selected == index) {
						this.setItemAt(x, y, ItemLibary.refactor().enchantItem(item.clone(), Enchantment.ARROW_INFINITE, 1, true));
					} else {
						this.setItemAt(x, y, item.clone());
					}
				}
			}
		} else {
			for (int x = 0; x < this.getWidth(); x++) {
				int index = this.top + x;
				ItemStack item = null;
				
				if (this.items.size() <= index) {
					item = this.getColor().asItem();
				} else {
					item = this.items.get(index);
				}
				
				for (int y = 0; y < this.getHeigth(); y++) {
					if (this.selected == index) {
						this.setItemAt(x, y, ItemLibary.refactor().enchantItem(item.clone(), Enchantment.ARROW_INFINITE, 1, true));
					} else {
						this.setItemAt(x, y, item.clone());
					}
				}
			}
		}
		
		this.getHandle().updateItemMap(this);
	}
	
	/**
	 * Sets the listtype of this element
	 * 
	 * @param type The new type
	 */
	public void setType(Directionality type) {
		this.type = type;
		this.configureItems();
	}
	
	/**
	 * Sets the index of the list-item at the top (the last list-item, which will be shown, is the start index + height or width)
	 * 
	 * @param index 
	 */
	public void setStartIndex(int index) {
		if (0 > index || index > this.items.size() - 1) {
			return;
		}
		
		if (this.top == index) {
			return;
		}
		
		this.top = index;
		this.configureItems();
	}
	
	/**
	 * 
	 * @return The index of the list-item at the top, which will be shown
	 */
	public int getStartIndex() {
		return this.top;
	}
	
	/**
	 * 
	 * @return The selected listelement
	 */
	public int getSelected() {
		return this.selected;
	}
	
	/**
	 * Sets the selection of this element on a specified index
	 * 
	 * @param index the index
	 */
	public void select(int index) {
		if (0 <= index && index < this.items.size()) {
			this.selected = index + this.top;
			this.configureItems();
			
			for (ElementSelectListener listener : selectListeners) {
				listener.onSelect(this);
			}
		}
	}
	
	/**
	 * Removes the selection of this element
	 */
	public void unselect() {
		this.selected = -1;
		this.configureItems();
		
		for(ElementUnselectListener listener : this.unselectListeners) listener.onUnselect(this);
	}
	
	/**
	 * Adds a listener, witch gets activated, when item gets selceted.
	 * 
	 * @param listener The listener
	 */
	public void addSelectListener(ElementSelectListener listener) {
		if (listener == null) {
			throw new NullPointerException("The listener cannot be null!");
		} else {
			this.selectListeners.add(listener);
		}
	}
	
	/**
	 * Adds a listener, witch gets activated, when the selected item gets unselected
	 * 
	 * @param listener The listener
	 */
	public void addUnselectListener(ElementUnselectListener listener) {
		if (listener == null) {
			throw new NullPointerException("The listener cannot be null!");
		} else {
			this.unselectListeners.add(listener);
		}
	}
	
	/**
	 * 
	 * @return The added items
	 */
	public List<ItemStack> getItems() {
		return this.items;
	}
	
	/**
	 * Registers the listeners
	 */
	protected void registerListener() {
		this.addActionListener(new ElementActionListener() {
			
			@Override
			public void onRightClick(InventoryClickEvent event, Element e) {
				this.onClick(event, e);
			}
			
			@Override
			public void onLeftClick(InventoryClickEvent event, Element e) {
				this.onClick(event, e);
			}
			
			private void onClick(InventoryClickEvent event, Element e) {
				if (event.getCurrentItem().equals(getColor().asItem())) {
					return;
				}
				
				InventoryType invType = event.getInventory().getType();
				
				int slot = event.getRawSlot();
				int invWidth = invType == InventoryType.DISPENSER ? 3 : (invType == InventoryType.HOPPER ? 5 : 9);
				
				if (ElementList.this.type == Directionality.VERTICAL) {
					select(slot - ((int) ((double) slot / (double) invWidth) * invWidth) - getX());
				} else {
					select((int) ((double) slot / (double) invWidth) - getY());
				}
				configureItems();
			}
			
		});
	}
	
}
