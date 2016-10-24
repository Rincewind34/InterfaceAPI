package de.rincewind.api.handling.listener;

import de.rincewind.api.handling.events.MapClickEvent;

@Deprecated
public abstract class MapClickListener implements ElementListener<MapClickEvent> {

	@Override
	public Class<MapClickEvent> getEventClass() {
		return MapClickEvent.class;
	}

}