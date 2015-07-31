package de.rincewind.plugin.gui.components;

import de.rincewind.api.gui.components.Sizeable;

public final class CraftSizeable implements Sizeable {

	private int width;
	private int heigth;
	
	public CraftSizeable() {
		this.width = 1;
		this.heigth = 1;
	}
	
	@Override
	public int getWidth() {
		return this.width;
	}

	@Override
	public int getHeigth() {
		return this.heigth;
	}

	@Override
	public boolean isInside(int x, int y) {
		if (x < this.width && y < this.heigth) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean setSize(int width, int heigth) {
		if (this.width == width && this.heigth == heigth) {
			return false; 
		} else if (width <= 0 || heigth <= 0) {
			return false;
		}
		
		this.width = width;
		this.heigth = heigth;
		
		return true;
	}
	
}
