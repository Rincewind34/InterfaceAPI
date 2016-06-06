package de.rincewind.api.handling.listener;

import de.rincewind.api.handling.events.SwitchEvent;

public abstract class SwitchListener implements ElementListener<SwitchEvent> {

	@Override
	public Class<SwitchEvent> getEventClass() {
		return SwitchEvent.class;
	}

}
