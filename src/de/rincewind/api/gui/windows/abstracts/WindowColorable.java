package de.rincewind.api.gui.windows.abstracts;

import java.util.List;

import de.rincewind.api.gui.Color;
import de.rincewind.api.gui.components.Colorable;
import de.rincewind.plugin.gui.components.CraftColorable;

public abstract class WindowColorable extends WindowEditor implements Colorable {
	
	private Colorable colorable;
	
	/**
	 * WindowColorable: The User is able to set a backgroundcolor for this window
	 */
	public WindowColorable() {
		super();
		this.colorable = new CraftColorable();
	}
	
	@Override
	public Color getColor() {
		return colorable.getColor();
	}
	
	@Override
	public boolean setColor(Color color) {
		if (!colorable.setColor(color)) {
			return false;
		}
		
		this.updateBukkitInventory();
		return true;
	}
	
	/**
	 * Creates a background with the setted color
	 * 
	 * @param usedSlots The slots, witch are not get the color
	 */
	protected void createBackground(List<Integer> usedSlots) {
		for (int i = 0; i < this.inv.getSize(); i++) {
			if (usedSlots.contains(i)) {
				continue;
			} else {
				this.inv.setItem(i, colorable.getColor().asItem());
			}
		}
	}
	
}
