package de.rincewind.interfaceapi.handling.window;

import de.rincewind.interfaceapi.gui.windows.abstracts.Window;
import de.rincewind.interfaceapi.handling.Event;

public class WindowEvent<T extends Window> extends Event<T> {
	
	private T window;
	
	public WindowEvent(T window) {
		this.window = window;
	}
	
	public T getWindow() {
		return this.window;
	}
	
}
