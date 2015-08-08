package de.rincewind.api.gui.windows;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.components.Sizeable;
import de.rincewind.api.gui.windows.abstracts.WindowColorable;
import de.rincewind.defaults.exceptions.InvalidSizeException;
import de.rincewind.defaults.exceptions.InvalidSlotException;
import de.rincewind.plugin.gui.components.CraftSizeable;

public class WindowSizeable extends WindowColorable implements Sizeable {
	
	private Sizeable sizeable = new CraftSizeable();
	
	/**
	 * WindowSizeable: You are able to resize this window
	 * Please note, that you can only use sizes from existing inventories (e.g. hopper or chest)
	 * Valid sizes are:
	 * <ul>
	 * 	<li>3x3</li>
	 * 	<li>5x1</li>
	 * 	<li>9x(i e {1,2,3,4,5,6})</li>
	 * </ul>
	 */
	public WindowSizeable() {
		super();
		this.createBukkitInventory();
		this.setSize(9, 3);
	}
	
	@Override
	public void updateBukkitInventory() {
		super.updateBukkitInventory();
		
		List<Integer> usedSlots = new ArrayList<Integer>();
		
		for (int i = 0; i < this.getWidth(); i++) {
			for (int j = 0; j < this.getHeight(); j++) {
				if (super.hasSpaceAt(i, j)) {
					continue;
				}
				
				ItemStack item = super.getItemAt(i, j);
				
				if (item == null) {
					continue;
				}
				
				
				int slot = this.getSlot(i, j);
				
				if (item.equals(Modifyable.EMPTY_USED_SLOT)) {
					usedSlots.add(slot);
				} else {
					usedSlots.add(slot);
					this.inv.setItem(slot, item);
				}
			}
		}
		
		super.createBackground(usedSlots);
		
		if (super.getUser() != null) {
			super.getUser().updateInventory();
		}
	}
	
	@Override
	public boolean isInside(int x, int y) {
		return sizeable.isInside(x, y);
	}
	
	@Override
	public int getWidth() {
		return sizeable.getWidth();
	}

	@Override
	public int getHeight() {
		return sizeable.getHeight();
	}
	
	@Override
	public int getPositionX(int slot) {
		if (super.inv.getSize() < slot) {
			throw new InvalidSlotException(slot, WindowSizeable.class);
		}
		
		return slot - (this.getPositionY(slot) * this.getWidth());
	}

	@Override
	public int getPositionY(int slot) {
		if (super.inv.getSize() < slot) {
			throw new InvalidSlotException(slot, WindowSizeable.class);
		}
		
		return (int) ((double) slot / (double) this.getWidth());
	}
	
	@Override
	public void setSize(int width, int height) {
		if (!this.checkSize(width, height)) {
			throw new InvalidSizeException(width, height, WindowSizeable.class);
		}
		
		this.sizeable.setSize(width, height);
		this.reconfigurate();
	}
	
	@Override
	protected void createBukkitInventory() {
		if (this.getWidth() == 3) {
			this.inv = Bukkit.createInventory(null, InventoryType.DISPENSER, this.getName());
		} else if (this.getWidth() == 5) {
			this.inv = Bukkit.createInventory(null, InventoryType.HOPPER, this.getName());
		} else if (this.getWidth() == 9) {
			this.inv = Bukkit.createInventory(null, this.getHeight() * 9, this.getName()); //ChestInv
		}
	}
	
	private boolean checkSize(int width, int height) {
		if (width <= 0 || height <= 0) {
			return false;
		} else if (width == 3 && height == 3) {
			return true; //Inventar eines Droppers oder Dispensers
		} else if (width == 5 && height == 1) {
			return true; //Inventar eines Hoppers
		} else if (width == 9 && width * height <= 54) {
			return true; //CustomInventar einer Kiste (1, 2, 3, 4, 5 oder 6 Reihen)
		}
		
		return false;
	}
	
	private int getSlot(int x, int y) {
		if (!this.isInside(x, y)) {
			return -1;
		}
		
		return x + (this.getWidth() * y);
	}
	
}
