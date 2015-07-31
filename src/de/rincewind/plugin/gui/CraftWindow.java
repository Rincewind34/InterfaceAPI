package de.rincewind.plugin.gui;

import org.bukkit.entity.Player;

import de.rincewind.api.gui.components.Openable;
import de.rincewind.api.gui.windows.Window;
import de.rincewind.api.gui.windows.util.Status;
import de.rincewind.plugin.gui.components.CraftOpenable;

public class CraftWindow implements Window {
	
	private Openable openable;
	
	private Player player;

	public CraftWindow() {
		this.openable = new CraftOpenable();
	}
	
	@Override
	public Status getStatus() {
		return openable.getStatus();
	}

	@Override
	public Player getUser() {
		return this.player;
	}

	
	@Override
	public void open() {
		this.openable.open();
		onOpen();
	}

	@Override
	public void close() {
		this.openable.close();
		onClose();
	}

	@Override
	public void maximize() {
		this.openable.maximize();
		onMaximize();
	}

	@Override
	public void minimize() {
		this.openable.minimize();
		onMinimize();
	}

	@Override
	public void moveBack() {
		this.openable.moveBack();
		onMoveBack();
	}
	
	protected void setUser(Player player) {
		if(player == null) return;
		
		this.player = player;
	}
	
	protected void onOpen() {}
	
	protected void onClose() {}
	
	protected void onMaximize() {}
	
	protected void onMinimize() {}
	
	protected void onMoveBack() {}

}
