package de.rincewind.plugin.gui.windows.abstracts;

import java.util.function.Consumer;

import org.bukkit.inventory.Inventory;

import de.rincewind.api.gui.components.Locatable.Point;
import de.rincewind.api.gui.windows.abstracts.WindowContainer;
import de.rincewind.plugin.gui.CraftWindowManager;

public abstract class CraftWindowContainer extends CraftWindowNameable implements WindowContainer {
	
	private Inventory inv;
	
	public CraftWindowContainer() {
		super();
	}
	
	@Override
	public void maximize() {
		super.maximize();
		this.reconfigurate();
	}
	
	@Override
	public void setName(String name) {
		super.setName(name);
		this.reconfigurate();
	}
	
	@Override
	public void updateBukkitInventory() {
		this.inv.clear();
	}
	
	@Override
	public int getBukkitSize() {
		return this.inv.getSize();
	}
	
	public void openBukkitInventory() {
		if (getUser() == null) {
			return;
		}
		
		CraftWindowManager.sendClosePacket(getUser());
		this.getUser().openInventory(this.inv);
	}
	
	public Inventory getBukkitInventory() {
		return this.inv;
	}
	
	public void createBukkitInventory() {
		this.inv = this.newInventory();
	}
	
	public abstract void iterate(Consumer<Point> action);
	
	public abstract int getSlot(Point point);
	
	public abstract Point getPoint(int bukkitSlot);
	
	public abstract Inventory newInventory();
	
	public void reconfigurate() {
		this.createBukkitInventory();
		this.updateBukkitInventory();
		this.openBukkitInventory();
	}

	
}
