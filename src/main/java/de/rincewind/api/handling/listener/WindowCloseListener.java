package de.rincewind.api.handling.listener;

import de.rincewind.api.handling.events.WindowCloseEvent;

@Deprecated
public abstract class WindowCloseListener implements WindowListener<WindowCloseEvent> {

	@Override
	public final Class<WindowCloseEvent> getEventClass() {
		return WindowCloseEvent.class;
	}

}