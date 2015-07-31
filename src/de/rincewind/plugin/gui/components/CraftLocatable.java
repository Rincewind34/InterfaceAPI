package de.rincewind.plugin.gui.components;

import de.rincewind.api.gui.components.Locatable;

public final class CraftLocatable implements Locatable {
	
	private int x;
	private int y;
	
	public CraftLocatable() {
		this.x = 0;
		this.y = 0;
	}
	
	@Override
	public int getX() {
		return this.x;
	}

	@Override
	public int getY() {
		return this.y;
	}

	@Override
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

}
