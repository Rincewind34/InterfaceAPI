package de.rincewind.plugin.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import com.google.common.annotations.Beta;

import de.rincewind.api.gui.components.Activatable;
import de.rincewind.api.gui.components.Locatable;
import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.Element;
import de.rincewind.plugin.gui.components.CraftActivatalble;
import de.rincewind.plugin.gui.components.CraftLocatable;

public abstract class CraftElement implements Element {
	
	private Locatable locatable;
	private Activatable activatable;
	
	private int id;
	private Modifyable handle;
	
	private boolean visible;
	
	private ItemStack icon;
	private ItemStack disabledIcon;
	
	protected ItemStack[][] items;
	
	public HashMap<Integer, Map<Integer, List<InventoryAction>>> blocked;
	
	public CraftElement(Modifyable handle) {
		this.handle = handle;
		this.id = -1;
		this.visible = true;
		this.icon = new ItemStack(Material.STONE);
		this.disabledIcon = this.getIcon();
		this.items = new ItemStack[][] {{null}};
		this.blocked = new HashMap<Integer, Map<Integer, List<InventoryAction>>>();
		this.blocked.put(0, new HashMap<Integer, List<InventoryAction>>());
		this.blocked.get(0).put(0, new ArrayList<InventoryAction>());
		this.blocked.get(0).get(0).add(InventoryAction.UNKNOWN);
		
		this.locatable = new CraftLocatable();
		this.activatable = new CraftActivatalble();
	}
	
	@Override
	public int getX() {
		return locatable.getX();
	}

	@Override
	public int getY() {
		return locatable.getY();
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public boolean isEnabled() {
		return this.activatable.isEnabled();
	}
	
	@Override
	public boolean hasItemAt(int x, int y) {
		if(0 <= x && x < items.length) 
			if(0 <= y && y < items[x].length) 
				return true;
		return false;
	}
	
	@Override
	public boolean isSimilar(Element element) {
		return this.id == ((CraftElement) element).getId();
	}
	
	@Override
	public boolean isPointBlocked(int x, int y, InventoryAction action) {
		if(!this.hasItemAt(x, y)) return false;
		if(this.isPointBlocked(x, y)) return true;
		else return this.blocked.get(x).get(y).contains(action);
	}
	
	@Override
	public ItemStack getIcon() {
		return icon;
	}

	@Override
	public ItemStack getDisabledIcon() {
		return disabledIcon;
	}

	@Override
	public ItemStack getItemAt(int x, int y) {
		if(this.hasItemAt(x, y)) return items[x][y];
		else return null;
	}

	@Override
	public void setDisabledIcon(ItemStack icon) {
		this.disabledIcon = icon;
	}

	@Override
	public void enable() {
		this.activatable.enable();
	}

	@Override
	public void disable() {
		this.activatable.disable();
	}

	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	@Override
	public void setPosition(int x, int y) {
		locatable.setPosition(x, y);
	}

	@Override
	public void setIcon(ItemStack icon) {
		if(icon != null) {
			this.icon = icon;
			this.handle.updateItemMap(this);
		}
	}

	@Override
	public void setBlocked(int x, int y, boolean toBlock) {
		if(!this.hasItemAt(x, y)) return;
		else {
			this.blocked.get(x).get(y).clear();
			if(toBlock) this.blocked.get(x).get(y).add(InventoryAction.UNKNOWN);
		}
	}

	@Override
	public void setBlocked(int x, int y, InventoryAction action) {
		if(!this.hasItemAt(x, y)) return;
		if(this.isPointBlocked(x, y, action)) return;
		if(action == InventoryAction.UNKNOWN) return;
		else this.blocked.get(x).get(y).add(action);
	}
	
	@Override
	public void setEnabled(boolean enable) {
		this.activatable.setEnabled(enable);
	}
	
	@Beta
	@Override
	public boolean isLocationBlocked(int x, int y) {
		return false;
	}
	
	/**
	 * 
	 * Sets an itemstack to the Point P(x|y)
	 * 
	 * @param x The x-coordinate
	 * @param y The y-coordinate
	 * @param item The itemstack to set
	 */
	protected void setItemAt(int x, int y, ItemStack item) {
		if(this.hasItemAt(x, y)) {
			items[x][y] = item;
		}
 	}
	
	/**
	 * 
	 * @return The ID of this element
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * 
	 * @return The handle of this element
	 */
	public Modifyable getHandle() {
		return handle;
	}
	
	/**
	 * Sets the ID of this element
	 * 
	 * @param id The new ID
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * 
	 * @param x The x-coordinate
	 * @param y The y-coordinate
	 * @return If the point P(x;y) is completely blocked
	 */
	public boolean isPointBlocked(int x, int y) {
		if(!this.hasItemAt(x, y)) return false;
		else return this.blocked.get(x).get(y).contains(InventoryAction.UNKNOWN);
	}

}
