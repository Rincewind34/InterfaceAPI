package de.rincewind.api.listener;

import de.rincewind.api.events.ButtonPressEvent;

public abstract class ButtonPressListener implements ElementListener<ButtonPressEvent> {
	
	@Override
	public Class<ButtonPressEvent> getEventClass() {
		return ButtonPressEvent.class;
	}

}
