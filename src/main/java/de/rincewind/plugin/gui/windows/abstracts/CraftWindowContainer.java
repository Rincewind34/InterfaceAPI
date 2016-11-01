package de.rincewind.plugin.gui.windows.abstracts;

import org.bukkit.inventory.Inventory;

import de.rincewind.api.InterfaceAPI;
import de.rincewind.api.gui.elements.util.Icon;
import de.rincewind.api.gui.elements.util.Point;
import de.rincewind.api.gui.windows.abstracts.WindowContainer;
import de.rincewind.api.gui.windows.util.Status;
import de.rincewind.api.handling.events.WindowMaximizeEvent;
import de.rincewind.plugin.setup.CraftSetup;

public abstract class CraftWindowContainer extends CraftWindowNameable implements WindowContainer {
	
	private Inventory inventory;
	
	public CraftWindowContainer() {
		super();
		
		this.getEventManager().registerListener(WindowMaximizeEvent.class, (event) -> {
			this.reconfigurate();
		}).addAfter();
	}
	
	@Override
	public void setName(String name) {
		super.setName(name);
		this.reconfigurate();
	}
	
	protected abstract int getSlot(Point point);
	
	protected abstract Point getPoint(int bukkitSlot);
	
	protected abstract Icon getIcon(Point point);
	
	protected abstract Inventory newInventory();
	
	public void createBukkitInventory() {
		this.inventory = this.newInventory();
	}
	
	protected void update() {
		this.inventory.clear();
		
		this.iterate((point) -> {
			this.update(point);
		});
	}
	
	protected void update(Point point) {
		this.inventory.setItem(this.getSlot(point), this.getIcon(point).toItem());
	}
	
	protected void update(Iterable<Point> points) {
		for (Point point : points) {
			this.update(point);
		}
	}
	
	protected void reconfigurate() {
		this.createBukkitInventory();
		this.update();
		this.openBukkitInventory();
	}
	
	private void openBukkitInventory() {
		if (this.getStatus() == Status.CLOSED) {
			return;
		}
		
		((CraftSetup) InterfaceAPI.getSetup(this.getUser())).sendClosePacket();
		this.getUser().openInventory(this.inventory);
	}
	
}
