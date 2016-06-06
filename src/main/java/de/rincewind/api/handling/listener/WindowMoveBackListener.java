package de.rincewind.api.handling.listener;

import de.rincewind.api.handling.events.WindowMoveBackEvent;

public abstract class WindowMoveBackListener implements WindowListener<WindowMoveBackEvent> {

	@Override
	public final Class<WindowMoveBackEvent> getEventClass() {
		return WindowMoveBackEvent.class;
	}

}

