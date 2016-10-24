package de.rincewind.api.handling.listener;

import de.rincewind.api.handling.events.OutputConsumeEvent;

@Deprecated
public abstract class OutputConsumeListener implements ElementListener<OutputConsumeEvent> {
	
	@Override
	public Class<OutputConsumeEvent> getEventClass() {
		return OutputConsumeEvent.class;
	}
	
}
