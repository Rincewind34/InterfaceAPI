package de.rincewind.api.handling.window;

import de.rincewind.api.gui.windows.abstracts.Window;
import de.rincewind.api.handling.Event;

public class WindowEvent<T extends Window> extends Event<T> {
	
	private T window;
	
	public WindowEvent(T window) {
		this.window = window;
	}
	
	public T getWindow() {
		return this.window;
	}
	
}
