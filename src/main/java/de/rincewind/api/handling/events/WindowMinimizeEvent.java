package de.rincewind.api.handling.events;

import de.rincewind.api.gui.windows.abstracts.Window;

public class WindowMinimizeEvent extends WindowEvent<Window> {

	public WindowMinimizeEvent(Window window) {
		super(window);
	}

}
