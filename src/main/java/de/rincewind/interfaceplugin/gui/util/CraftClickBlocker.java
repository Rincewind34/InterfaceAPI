package de.rincewind.interfaceplugin.gui.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;

import de.rincewind.interfaceapi.gui.elements.util.ClickAction;
import de.rincewind.interfaceapi.gui.elements.util.ClickBlocker;

public class CraftClickBlocker implements ClickBlocker {
	
	private boolean lock;
	
	private Set<ClickAction> blocked;
	
	public CraftClickBlocker() {
		this.blocked = new HashSet<>();
	}
	
	@Override
	public Set<ClickAction> getBlocked() {
		if (this.lock) {
			return Collections.unmodifiableSet(Sets.newHashSet(ClickAction.values()));
		} else {
			return Collections.unmodifiableSet(this.blocked);
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

	@Override
	public boolean allows(ClickAction action) {
		if (this.isLocked()) {
			return false;
		} else {
			return !this.blocked.contains(action);
		}
	}
	
}
