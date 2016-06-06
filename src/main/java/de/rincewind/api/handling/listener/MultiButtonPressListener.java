package de.rincewind.api.handling.listener;

import de.rincewind.api.handling.events.MultiButtonPressEvent;

public abstract class MultiButtonPressListener implements ElementListener<MultiButtonPressEvent> {

	@Override
	public Class<MultiButtonPressEvent> getEventClass() {
		return MultiButtonPressEvent.class;
	}

}
