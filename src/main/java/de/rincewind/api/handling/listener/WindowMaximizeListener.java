package de.rincewind.api.handling.listener;

import de.rincewind.api.handling.events.WindowMaximizeEvent;

@Deprecated
public abstract class WindowMaximizeListener implements WindowListener<WindowMaximizeEvent> {

	@Override
	public final Class<WindowMaximizeEvent> getEventClass() {
		return WindowMaximizeEvent.class;
	}

}
