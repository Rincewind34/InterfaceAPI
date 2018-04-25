package de.rincewind.interfaceplugin.gui.windows.abstracts;

import de.rincewind.interfaceapi.gui.windows.abstracts.WindowNameable;
import de.rincewind.interfaceplugin.Validate;

public class CraftWindowNameable extends CraftWindow implements WindowNameable {
	
	private String name;
	
	public CraftWindowNameable() {
		this.name = "GUI";
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public void setName(String name) {
		Validate.notNull(name, "The name cannot be null!");
		
		this.name = name;
	}

}
