package de.rincewind.plugin.gui.components;

import de.rincewind.api.gui.components.Activatable;

public class CraftActivatalble implements Activatable {
	
	private boolean enable;
	
	public CraftActivatalble() {
		this(true);
	}
	
	public CraftActivatalble(boolean enable) {
		this.setEnabled(enable);
	}
	
	@Override
	public void enable() {
		this.setEnabled(true);
	}

	@Override
	public void disable() {
		this.setEnabled(false);
	}

	@Override
	public void setEnabled(boolean enable) {
		this.enable = enable;
	}

	@Override
	public boolean isEnabled() {
		return this.enable;
	}

}
