package de.rincewind.api.handling.listener;

import de.rincewind.api.handling.events.ListUnselectEvent;

public abstract class ListUnselectListener<T> implements ElementListener<ListUnselectEvent<T>> {

	@Override
	@SuppressWarnings("unchecked")
	public final Class<ListUnselectEvent<T>> getEventClass() {
		return (Class<ListUnselectEvent<T>>) new ListUnselectEvent<T>(null).getClass();
	}

}
