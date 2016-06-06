package de.rincewind.api.handling.events;

import de.rincewind.api.gui.windows.abstracts.Window;

public class WindowCloseEvent extends WindowEvent<Window> {

	public WindowCloseEvent(Window window) {
		super(window);
	}

}
