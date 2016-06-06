package de.rincewind.api.handling.listener;

import de.rincewind.api.handling.events.ItemSelectEvent;

public abstract class ItemSelectListener implements ElementListener<ItemSelectEvent> {

	@Override
	public Class<ItemSelectEvent> getEventClass() {
		return ItemSelectEvent.class;
	}

}
