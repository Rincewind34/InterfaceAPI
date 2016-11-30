package de.rincewind.interfaceplugin.gui.windows;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import de.rincewind.interfaceapi.exceptions.InvalidSizeException;
import de.rincewind.interfaceapi.exceptions.InvalidSlotException;
import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceapi.gui.windows.WindowSizeable;
import de.rincewind.interfaceplugin.gui.windows.abstracts.CraftWindowColorable;

public class CraftWindowSizeable extends CraftWindowColorable implements WindowSizeable {
	
	private int width;
	private int height;
	
	public CraftWindowSizeable() {
		super();
		
		this.width = 9;
		this.height = 3;
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
	public int getWidth() {
		return this.width;
	}

	@Override
	public int getHeight() {
		return this.height;
	}
	
	@Override
	public boolean checkSize(int width, int height) {
		if (width <= 0 || height <= 0) {
			return false;
		} else if (width == 3 && height == 3) {
			return true;
		} else if (width == 5 && height == 1) {
			return true;
		} else if (width == 9 && width * height <= 54) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Point> getPoints() {
		List<Point> result = new ArrayList<>();
		
		for (int x = 0; x < this.width; x++) {
			for (int y = 0; y < this.height; y++) {
				result.add(new Point(x, y));
			}
		}
		
		return result;
	}

	@Override
	public int getSlot(Point point) {
		if (!this.isInside(point)) {
			return -1;
		}
		
		return point.getX() + (this.width * point.getY());
	}
	
	@Override
	public Point getPoint(int bukkitSlot) {
		if (this.getWidth() * this.getHeight() <= bukkitSlot) {
			throw new InvalidSlotException(bukkitSlot, WindowSizeable.class);
		}
		
		int y = (int) ((double) bukkitSlot / (double) this.getWidth());
		int x = bukkitSlot - (y * this.getWidth());
		
		return new Point(x, y);
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
	
}
