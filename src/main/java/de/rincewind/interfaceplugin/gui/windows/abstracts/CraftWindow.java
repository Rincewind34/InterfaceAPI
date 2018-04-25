package de.rincewind.interfaceplugin.gui.windows.abstracts;

import org.bukkit.entity.Player;

import de.rincewind.interfaceapi.gui.windows.abstracts.Window;
import de.rincewind.interfaceapi.gui.windows.util.WindowState;
import de.rincewind.interfaceapi.handling.EventManager;
import de.rincewind.interfaceapi.handling.window.WindowChangeStateEvent;
import de.rincewind.interfaceapi.handling.window.WindowOpenEvent;
import de.rincewind.interfaceplugin.gui.util.CraftEventManager;

public abstract class CraftWindow implements Window {

	private WindowState state;

	private Player player;

	private EventManager eventManager;

	public CraftWindow() {
		this.state = WindowState.CLOSED;
		this.eventManager = new CraftEventManager();
		
		this.eventManager.registerListener(WindowOpenEvent.class, (event) -> {
			this.state = WindowState.MINIMIZED;
			this.player = event.getOwner();
		}).addAfter();
		
		this.eventManager.registerListener(WindowChangeStateEvent.class, (event) -> {
			this.state = event.getNewState();
			
			if (this.state == WindowState.CLOSED) {
				this.player = null;
			}
		}).addAfter();
	}

	@Override
	public WindowState getState() {
		return this.state;
	}

	@Override
	public Player getUser() {
		return this.player;
	}

	@Override
	public EventManager getEventManager() {
		return this.eventManager;
	}

	@Override
	public boolean isOpened() {
		return this.state != WindowState.CLOSED;
	}

}
