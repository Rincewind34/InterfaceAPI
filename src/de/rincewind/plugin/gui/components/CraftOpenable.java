package de.rincewind.plugin.gui.components;

import de.rincewind.api.gui.components.Openable;
import de.rincewind.api.gui.windows.util.Status;

public final class CraftOpenable implements Openable {
	
	private Status status;
	
	public CraftOpenable() {
		this.status = Status.MINIMIZED;
	}
	
	@Override
	public Status getStatus() {
		return status;
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
	
}
