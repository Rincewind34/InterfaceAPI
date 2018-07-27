package de.rincewind.interfaceplugin.gui.windows;

import java.util.Collections;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import de.rincewind.interfaceapi.exceptions.InvalidBoundsException;
import de.rincewind.interfaceapi.gui.util.Bounds;
import de.rincewind.interfaceapi.gui.util.Point;
import de.rincewind.interfaceapi.gui.windows.WindowSizeable;
import de.rincewind.interfaceapi.gui.windows.util.WindowState;
import de.rincewind.interfaceplugin.Validate;
import de.rincewind.interfaceplugin.gui.windows.abstracts.CraftWindowColorable;

public class CraftWindowSizeable extends CraftWindowColorable implements WindowSizeable {

	private Bounds bounds;

	public CraftWindowSizeable(Plugin plugin) {
		super(plugin);

		this.bounds = Bounds.of(9, 3);
	}

	@Override
	public void setSize(Bounds bounds) {
		Validate.notNull(bounds, "The bounds cannot be null");

		if (this.bounds.equals(bounds)) {
			return;
		}

		if (!this.checkSize(bounds)) {
			throw new InvalidBoundsException(bounds, WindowSizeable.class);
		}

		this.bounds = bounds;

		if (this.isRenderClosed() || this.getState() == WindowState.MAXIMIZED) {
			this.reconfigure();
		}
	}

	@Override
	public boolean checkSize(Bounds bounds) {
		if (bounds.getWidth() == 3 && bounds.getHeight() == 3) {
			return true;
		} else if (bounds.getWidth() == 5 && bounds.getHeight() == 1) {
			return true;
		} else if (bounds.getWidth() == 9 && bounds.getWidth() * bounds.getHeight() <= 54) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int getSlot(Point point) {
		assert this.isInside(point) : "The point " + point + " is undefined for window sizeable";

		return point.getX() + (this.bounds.getWidth() * point.getY());
	}

	@Override
	public Bounds getBounds() {
		return this.bounds;
	}

	@Override
	public Point getPoint(int bukkitSlot) {
		assert this.getWidth() * this.getHeight() > bukkitSlot : "The bukkit slot " + bukkitSlot + " is undefined for window sizeable";

		int y = (int) ((double) bukkitSlot / (double) this.getWidth());
		int x = bukkitSlot - (y * this.getWidth());

		return Point.of(x, y);
	}

	@Override
	public Inventory newInventory() {
		if (this.bounds.getWidth() == 3) {
			return Bukkit.createInventory(null, InventoryType.DISPENSER, this.getName());
		} else if (this.bounds.getWidth() == 5) {
			return Bukkit.createInventory(null, InventoryType.HOPPER, this.getName());
		} else if (this.bounds.getWidth() == 9) {
			return Bukkit.createInventory(null, this.getHeight() * 9, this.getName());
		} else {
			assert false : "Tried to build invalid size w=" + this.bounds;
			return null;
		}
	}

	@Override
	public Set<Point> getPoints() {
		return Collections.unmodifiableSet(Point.NULL.square(this.bounds));
	}

}
