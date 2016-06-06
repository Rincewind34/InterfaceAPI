package de.rincewind.api.handling.listener;

import de.rincewind.api.handling.events.AnvilNameEvent;

public abstract class AnvilNameListener implements WindowListener<AnvilNameEvent> {

	@Override
	public Class<AnvilNameEvent> getEventClass() {
		return AnvilNameEvent.class;
	}

}
