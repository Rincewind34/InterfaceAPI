package de.rincewind.plugin.gui.windows.abstracts;

import lib.securebit.Validate;
import de.rincewind.api.gui.windows.abstracts.WindowNameable;
import de.rincewind.api.gui.windows.util.WindowDefaults;

public class CraftWindowNameable extends CraftWindow implements WindowNameable {
	
	private String name;
	
	public CraftWindowNameable() {
		super();
		this.name = WindowDefaults.NAME;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public void setName(String name) {
		Validate.notNull(name, "The name cannot be null!");
		
		if (name.equals(this.name)) {
			return;
		}
		
		this.name = name;
	}

}
