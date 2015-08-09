package de.rincewind.plugin.gui.windows;

import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import de.rincewind.api.gui.components.Locatable.Point;
import de.rincewind.api.gui.windows.WindowSizeable;
import de.rincewind.defaults.exceptions.InvalidSizeException;
import de.rincewind.defaults.exceptions.InvalidSlotException;
import de.rincewind.plugin.gui.windows.abstracts.CraftWindowColorable;

public class CraftWindowSizeable extends CraftWindowColorable implements WindowSizeable {
	
	private int width;
	private int height;
	
	public CraftWindowSizeable() {
		super();
		
		this.createBukkitInventory();
		this.setSize(9, 3);
	}
	
	@Override
	public void updateBukkitInventory() {
		super.updateBukkitInventory();
		
		if (super.getUser() != null) {
			super.getUser().updateInventory();
		}
	}
	
	@Override
	public boolean isInside(Point point) {
		if (point.getX() < this.width && point.getY() < this.height) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int getWidth() {
		return this.width;
	}

	@Override
	public int getHeight() {
		return this.height;
	}
	
	@Override
	public Point getPoint(int bukkitSlot) {
		if (super.getBukkitSize() < bukkitSlot) {
			throw new InvalidSlotException(bukkitSlot, WindowSizeable.class);
		}
		
		int y = (int) ((double) bukkitSlot / (double) this.getWidth());
		int x = bukkitSlot - (y * this.getWidth());
		
		return new Point(x, y);
	}
	
	@Override
	public void open() {
		super.open();
	}
	
	@Override
	public void setSize(int width, int height) {
		if (!this.checkSize(width, height)) {
			throw new InvalidSizeException(width, height, WindowSizeable.class);
		}
		
		this.width = width;
		this.height = height;
		this.reconfigurate();
	}
	
	@Override
	public Inventory newInventory() {
		if (this.getWidth() == 3) {
			return Bukkit.createInventory(null, InventoryType.DISPENSER, this.getName());
		} else if (this.getWidth() == 5) {
			return Bukkit.createInventory(null, InventoryType.HOPPER, this.getName());
		} else if (this.getWidth() == 9) {
			return Bukkit.createInventory(null, this.getHeight() * 9, this.getName()); //ChestInv
		} else {
			return null;
		}
	}
	
	public boolean checkSize(int width, int height) {
		if (width <= 0 || height <= 0) {
			return false;
		} else if (width == 3 && height == 3) {
			return true; //Inventar eines Droppers oder Dispensers
		} else if (width == 5 && height == 1) {
			return true; //Inventar eines Hoppers
		} else if (width == 9 && width * height <= 54) {
			return true; //CustomInventar einer Kiste (1, 2, 3, 4, 5 oder 6 Reihen)
		} else {
			return false;
		}
	}

	@Override
	public void iterate(Consumer<Point> action) {
		for (int x = 0; x < this.getWidth(); x++) {
			for (int y = 0; y < this.getHeight(); y++) {
				action.accept(new Point(x, y));
			}
		}
	}

	@Override
	public int getSlot(Point point) {
		if (!this.isInside(point)) {
			return -1;
		}
		
		return point.getX() + (this.getWidth() * point.getY());
	}
	
}
