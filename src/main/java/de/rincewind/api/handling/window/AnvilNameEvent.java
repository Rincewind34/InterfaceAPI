package de.rincewind.api.handling.window;

import de.rincewind.api.gui.windows.WindowAnvil;

public class AnvilNameEvent extends WindowEvent<WindowAnvil> {
	
	private String name;
	
	public AnvilNameEvent(WindowAnvil window, String name) {
		super(window);
		
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

}
