package de.rincewind.plugin.gui.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.rincewind.api.gui.elements.util.ClickAction;
import de.rincewind.api.gui.elements.util.ClickBlocker;

public class CraftClickBlocker implements ClickBlocker {
	
	private List<ClickAction> blocked;
	
	private boolean lock;
	
	public CraftClickBlocker() {
		this.blocked = new ArrayList<ClickAction>();
	}
	
	@Override
	public List<ClickAction> getBlocked() {
		if (this.lock) {
			return Collections.unmodifiableList(Arrays.asList(ClickAction.values()));
		} else {
			return Collections.unmodifiableList(this.blocked);
		}
	}
	
	@Override
	public void addAction(ClickAction action) {
		this.blocked.add(action);
	}
	
	@Override
	public void removeAction(ClickAction action) {
		this.blocked.add(action);
	}
	
	@Override
	public void lock() {
		this.lock = true;
	}
	
	@Override
	public void unlock() {
		this.lock = false;
	}
	
	@Override
	public boolean isLocked() {
		return this.lock;
	}
	
}
