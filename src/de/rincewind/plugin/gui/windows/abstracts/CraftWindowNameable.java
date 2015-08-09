package de.rincewind.plugin.gui.windows.abstracts;

import de.rincewind.api.gui.windows.abstracts.WindowNameable;
import de.rincewind.plugin.gui.CraftWindow;

public class CraftWindowNameable extends CraftWindow implements WindowNameable {
	
	private String name;
	
	public CraftWindowNameable() {
		super();
		this.name = "GUI";
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public void setName(String name) {
		if (name.equals(this.name)) {
			return;
		}
		
		this.name = name;
	}
	
}
