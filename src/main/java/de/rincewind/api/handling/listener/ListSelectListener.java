package de.rincewind.api.handling.listener;

import de.rincewind.api.handling.events.ListSelectEvent;

@Deprecated
public abstract class ListSelectListener<T> implements ElementListener<ListSelectEvent<T>> {

	@Override
	@SuppressWarnings("unchecked")
	public final Class<ListSelectEvent<T>> getEventClass() {
		return (Class<ListSelectEvent<T>>) new ListSelectEvent<T>(null).getClass();
	}

}
