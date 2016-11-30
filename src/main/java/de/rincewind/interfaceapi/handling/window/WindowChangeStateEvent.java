package de.rincewind.interfaceapi.handling.window;

import de.rincewind.interfaceapi.gui.windows.abstracts.Window;
import de.rincewind.interfaceapi.gui.windows.util.WindowState;

public class WindowChangeStateEvent extends WindowEvent<Window> {
	
	private WindowState state;
	
	public WindowChangeStateEvent(Window window, WindowState state) {
		super(window);
		
		this.state = state;
	}
	
	public WindowState getNewState() {
		return this.state;
	}
	
}
