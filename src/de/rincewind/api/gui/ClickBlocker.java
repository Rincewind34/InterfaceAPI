package de.rincewind.api.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ClickBlocker {
	
	private List<ClickAction> blocked;
	
	private boolean lock;
	
	public ClickBlocker() {
		this.blocked = new ArrayList<ClickAction>();
	}
	
	public List<ClickAction> getBlocked() {
		if (this.lock) {
			return Collections.unmodifiableList(Arrays.asList(ClickAction.values()));
		} else {
			return Collections.unmodifiableList(this.blocked);
		}
	}
	
	public void addAction(ClickAction action) {
		this.blocked.add(action);
	}
	
	public void removeAction(ClickAction action) {
		this.blocked.add(action);
	}
	
	public void lock() {
		this.lock = true;
	}
	
	public void unlock() {
		this.lock = false;
	}
	
	public boolean isLocked() {
		return this.lock;
	}
	
}
