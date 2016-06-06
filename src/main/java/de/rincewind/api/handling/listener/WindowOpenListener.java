package de.rincewind.api.handling.listener;

import de.rincewind.api.handling.events.WindowOpenEvent;

public abstract class WindowOpenListener implements WindowListener<WindowOpenEvent> {

	@Override
	public final Class<WindowOpenEvent> getEventClass() {
		return WindowOpenEvent.class;
	}

}
