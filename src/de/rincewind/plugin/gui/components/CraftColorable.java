package de.rincewind.plugin.gui.components;

import de.rincewind.api.gui.Color;
import de.rincewind.api.gui.components.Colorable;

public final class CraftColorable implements Colorable {
	
	private Color color;
	
	public CraftColorable() {
		this.color = Color.TRANSLUCENT;
	}
	
	@Override
	public boolean setColor(Color color) {
		if (color == null) {
			return false;
		}
		
		if (this.color == color) {
			return false;
		}
		
		this.color = color;
		
		return true;
	}

	@Override
	public Color getColor() {
		return color;
	}

}
