package de.rincewind.api.handling.events;

import de.rincewind.api.gui.windows.abstracts.Window;

public class WindowMaximizeEvent extends WindowEvent<Window> {

	public WindowMaximizeEvent(Window window) {
		super(window);
	}

}
