package de.rincewind.plugin.gui.windows.abstracts;

import org.bukkit.entity.Player;

import de.rincewind.api.gui.util.EventManager;
import de.rincewind.api.gui.windows.abstracts.Window;
import de.rincewind.api.gui.windows.util.Status;
import de.rincewind.api.gui.windows.util.WindowDefaults;
import de.rincewind.api.handling.events.WindowMaximizeEvent;
import de.rincewind.api.handling.events.WindowMinimizeEvent;
import de.rincewind.api.handling.events.WindowMoveBackEvent;
import de.rincewind.api.handling.listener.WindowMaximizeListener;
import de.rincewind.api.handling.listener.WindowMinimizeListener;
import de.rincewind.api.handling.listener.WindowMoveBackListener;
import de.rincewind.plugin.gui.util.CraftEventManager;

public abstract class CraftWindow implements Window {
	
	private Status status;
	
	private Player player;
	
	private EventManager eventManager;

	public CraftWindow() {
		this.status = WindowDefaults.STATE;
		
		this.eventManager = new CraftEventManager();
		
		this.eventManager.registerListener(new WindowMinimizeListener() {
			
			@Override
			public void onFire(WindowMinimizeEvent event) {
				CraftWindow.this.status = Status.MINIMIZED;
			}
		}).addAfter();
		
		this.eventManager.registerListener(new WindowMaximizeListener() {
			
			@Override
			public void onFire(WindowMaximizeEvent event) {
				CraftWindow.this.status = Status.MAXIMIZED;
			}
			
		}).addAfter();
		
		this.eventManager.registerListener(new WindowMoveBackListener() {
			
			@Override
			public void onFire(WindowMoveBackEvent event) {
				CraftWindow.this.status = Status.BACKGROUND;
			}
		}).addAfter();
	}
	
	@Override
	public Status getStatus() {
		return this.status;
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
		return this.player != null;
	}
	
	public void setUser(Player player) {
		this.player = player;
	}
	
}
