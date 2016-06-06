package de.rincewind.api.handling.events;

import de.rincewind.api.gui.windows.abstracts.Window;

public class WindowMoveBackEvent extends WindowEvent<Window> {

	public WindowMoveBackEvent(Window window) {
		super(window);
	}

}
