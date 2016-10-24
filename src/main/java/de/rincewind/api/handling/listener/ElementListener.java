package de.rincewind.api.handling.listener;

import de.rincewind.api.handling.events.ElementEvent;

@Deprecated
public interface ElementListener<T extends ElementEvent<?>> extends Listener<T> {
	
}
