package de.rincewind.plugin.gui;

import org.bukkit.entity.Player;

import de.rincewind.api.gui.windows.Window;
import de.rincewind.api.gui.windows.util.Status;

public abstract class CraftWindow implements Window {
	
	private Status status;
	
	private Player player;

	public CraftWindow() {
		this.status = Status.MINIMIZED;
	}
	
	@Override
	public Status getStatus() {
		return this.status;
	}
	
	@Override
	public void open() {
		
	}

	@Override
	public void close() {
		
	}

	@Override
	public void maximize() {
		this.status = Status.MAXIMIZED;
	}

	@Override
	public void minimize() {
		this.status = Status.MINIMIZED;
	}

	@Override
	public void moveBack() {
		this.status = Status.BACKGROUND;
	}
	
	@Override
	public Player getUser() {
		return this.player;
	}
	
	protected void setUser(Player player) {
		if (player == null) {
			return;
		}
		
		this.player = player;
	}
	
}
