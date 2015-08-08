package de.rincewind.plugin.gui.components;

import de.rincewind.api.gui.components.Sizeable;
import de.rincewind.defaults.exceptions.InvalidSizeException;

public final class CraftSizeable implements Sizeable {

	private int width;
	private int height;
	
	public CraftSizeable() {
		this.width = 1;
		this.height = 1;
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
	public boolean isInside(int x, int y) {
		if (x < this.width && y < this.height) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void setSize(int width, int height) {
		if (width <= 0 || height <= 0) {
			throw new InvalidSizeException(width, height, Sizeable.class);
		}
		
		this.width = width;
		this.height = height;
	}
	
}
