package de.rincewind.api.gui.elements;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.listener.ElementActionListener;

public class ElementSwitcher extends ElementButton {
	
	private List<ItemStack> items;
	private int switchid;
	
	/**
	 * @param handle The window for this element
	 */
	public ElementSwitcher(Modifyable handle) {
		super(handle);
		this.items = new ArrayList<ItemStack>();
		this.registerListener();
	}
	
	@Override
	public void enable() {
		super.enable();
		this.updateItemArray();
	}
	
	@Override
	public void disable() {
		super.disable();
		for(int x = 0; x < super.getWidth(); x++) {
			for(int y = 0; y < super.getHeigth(); y++) {
				if(super.getDisabledIcon() == null) super.setItemAt(x, y, super.getIcon());
				else super.setItemAt(x, y, super.getDisabledIcon());
			}
		}
	}
	
	@Override
	public void setIcon(ItemStack icon) {
		super.setIcon(icon);
		this.items.add(icon);
		this.updateItemArray();
	}
	
	/**
	 * Switches to the next switch-item
	 */
	public void next() {
		this.setSwitchId(this.getSwitchid() + 1);
	}
	
	/**
	 * Switches to the previous switch-item
	 */
	public void back() {
		this.setSwitchId(this.getSwitchid() - 1);
	}
	
	/**
	 * Update
	 */
	public void updateItemArray() {
		for (int x = 0; x < super.getWidth(); x++) {
			for (int y = 0; y < super.getHeigth(); y++) {
				super.setItemAt(x, y, this.items.get(this.switchid));
			}
		}
		
		super.getHandle().updateItemMap(this);
	}
	
	/**
	 * Moves the switcher to the switch-item by a given id
	 * @param id The switch-elements id
	 */
	public void setSwitchId(int id) {
		this.switchid = id;
		
		if (this.switchid < 0) {
			this.switchid = this.items.size() - 1;
		}
		
		if (this.items.size() == this.switchid) {
			this.switchid = 0;
		}
		
		this.updateItemArray();
	}
	
	/**
	 * @return The id of the currently active switch-item
	 */
	public int getSwitchid() {
		return this.switchid;
	}
	
	/**
	 * Adds a switch-item to the switcher
	 * @param item The icon of the switch-item
	 */
	public void addSwitch(ItemStack item) {
		this.items.add(item);
		this.updateItemArray();
	}
	
	/**
	 * Removes an switch-item from the switcher
	 * @param item The switch-items icon to remove
	 */
	public void removeSwitch(ItemStack item) {
		this.items.remove(item);
		this.setSwitchId(this.getSwitchid());
	}
	
	/**
	 * Removes all switch-items from the switcher
	 */
	public void clear() {
		this.items.clear();
	}
	
	/**
	 * @return The count the all switch-items
	 */
	public int size() {
		return this.items.size();
	}
	
	/**
	 * Register the listener to activate the switcher
	 */
	protected void registerListener() {
		this.addActionListener(new ElementActionListener() {
			
			@Override
			public void onRightClick(InventoryClickEvent event, Element e) {
				back();
				
				if (event.isShiftClick()) {
					back();
				}
			}
			
			@Override
			public void onLeftClick(InventoryClickEvent event, Element e) {
				next();
				
				if (event.isShiftClick()) {
					next(); 
				}
			}
		});
	}

}
