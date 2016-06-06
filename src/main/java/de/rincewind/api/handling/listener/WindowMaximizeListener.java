package de.rincewind.api.handling.listener;

import de.rincewind.api.handling.events.WindowMaximizeEvent;

public abstract class WindowMaximizeListener implements WindowListener<WindowMaximizeEvent> {

	@Override
	public final Class<WindowMaximizeEvent> getEventClass() {
		return WindowMaximizeEvent.class;
	}

}
