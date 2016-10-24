package de.rincewind.api.handling.listener;

import de.rincewind.api.handling.events.WindowEvent;

@Deprecated
public interface WindowListener<T extends WindowEvent<?>> extends Listener<T> {

}
