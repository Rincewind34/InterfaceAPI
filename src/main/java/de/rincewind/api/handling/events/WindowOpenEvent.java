package de.rincewind.api.handling.events;

import de.rincewind.api.gui.windows.abstracts.Window;

public class WindowOpenEvent extends WindowEvent<Window> {

	public WindowOpenEvent(Window window) {
		super(window);
	}

}
