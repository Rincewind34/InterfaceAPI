package de.rincewind.plugin.gui.components;

import de.rincewind.api.gui.components.Nameable;

public final class CraftNameable implements Nameable{

	private String name;
	
	public CraftNameable(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean setName(String name) {
		if (name == null) {
			return false;
		} else if (name.isEmpty()) {
			return false;
		} else if (name.trim().isEmpty()) {
			return false;
		} else if (name.equals(this.name)) {
			return false;
		}
		
		this.name = name;
		return true;
	}

}
