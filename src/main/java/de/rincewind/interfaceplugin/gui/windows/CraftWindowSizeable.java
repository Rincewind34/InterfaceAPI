package de.rincewind.interfaceplugin.gui.windows;

import java.util.Collections;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import de.rincewind.interfaceapi.exceptions.InvalidSizeException;
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
		if (this.width == width && this.height == height) {
			return;
		}

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
	public Set<Point> getPoints() {
		return Collections.unmodifiableSet(Point.NULL.square(this.width, this.height));
	}

	@Override
	public int getSlot(Point point) {
		assert this.isInside(point) : "The point " + point + " is undefined for window sizeable";

		return point.getX() + (this.width * point.getY());
	}

	@Override
	public Point getPoint(int bukkitSlot) {
		assert this.getWidth() * this.getHeight() > bukkitSlot : "The bukkit slot " + bukkitSlot + " is undefined for window sizeable";

		int y = (int) ((double) bukkitSlot / (double) this.getWidth());
		int x = bukkitSlot - (y * this.getWidth());

		return new Point(x, y);
	}

	@Override
	public Inventory newInventory() {
		if (this.width == 3) {
			return Bukkit.createInventory(null, InventoryType.DISPENSER, this.getName());
		} else if (this.width == 5) {
			return Bukkit.createInventory(null, InventoryType.HOPPER, this.getName());
		} else if (this.width == 9) {
			return Bukkit.createInventory(null, this.getHeight() * 9, this.getName());
		} else {
			assert false : "Tried to build invalid size w=" + this.width + ";h=" + this.height;
			return null;
		}
	}

}
