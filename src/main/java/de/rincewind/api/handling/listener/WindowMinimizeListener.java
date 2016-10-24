package de.rincewind.api.handling.listener;

import de.rincewind.api.handling.events.WindowMinimizeEvent;

@Deprecated
public abstract class WindowMinimizeListener implements WindowListener<WindowMinimizeEvent> {

	@Override
	public final Class<WindowMinimizeEvent> getEventClass() {
		return WindowMinimizeEvent.class;
	}
	
}
