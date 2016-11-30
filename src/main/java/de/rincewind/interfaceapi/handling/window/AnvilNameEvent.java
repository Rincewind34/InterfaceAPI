package de.rincewind.interfaceapi.handling.window;

import de.rincewind.interfaceapi.gui.windows.WindowAnvil;

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
