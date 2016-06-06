package de.rincewind.api.handling.events;

import de.rincewind.api.gui.windows.abstracts.Window;

public class WindowEvent<T extends Window> extends Event<T> {
	
	private T window;
	
	public WindowEvent(T window) {
		this.window = window;
	}
	
	public T getWindow() {
		return this.window;
	}
	
}
