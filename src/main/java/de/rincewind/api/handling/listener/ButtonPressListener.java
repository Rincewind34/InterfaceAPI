package de.rincewind.api.handling.listener;

import de.rincewind.api.handling.events.ButtonPressEvent;

@Deprecated
public abstract class ButtonPressListener implements ElementListener<ButtonPressEvent> {
	
	@Override
	public final Class<ButtonPressEvent> getEventClass() {
		return ButtonPressEvent.class;
	}

}
