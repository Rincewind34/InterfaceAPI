package de.rincewind.api.handling.window;

import de.rincewind.api.gui.windows.abstracts.Window;
import de.rincewind.api.gui.windows.util.WindowState;

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
