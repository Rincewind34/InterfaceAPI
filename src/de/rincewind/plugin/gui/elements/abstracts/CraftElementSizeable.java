package de.rincewind.plugin.gui.elements.abstracts;

import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.abstracts.ElementSizeable;
import de.rincewind.defaults.exceptions.InvalidSizeException;

public abstract class CraftElementSizeable extends CraftElementDisplayable implements ElementSizeable {
	
	private int width;
	private int height;
	
	/**
	 * ElementSizable: An element, which size can be customized
	 * @param handle The window for this element
	 */
	public CraftElementSizeable(Modifyable handle) {
		super(handle);
		
		this.width = 1;
		this.height = 1;
	}
	
	@Override
	public void updateItemMap() {
		this.updateItemMap(true);
	}
	
	@Override
	public ItemStack[][] getNewArray() {
		return new ItemStack[this.width][this.height];
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
	public void setSize(int width, int height) {
		if (width <= 0 || height <= 0) {
			throw new InvalidSizeException(width, height, ElementSizeable.class);
		}
		
		this.width = width;
		this.height = height;
		this.createArray();
		this.getHandle().readItemsFrom(this);
	}
	
	@Override
	public boolean isInside(Point point) {
		if (point.getX() < this.width && point.getY() < this.height) {
			return true;
		} else {
			return false;
		}
	}
	
	public final void updateItemMap(boolean withIcon) {
		this.iterate((point) -> {
			this.updateItemMap(point, withIcon);
		});
	}
	
}
