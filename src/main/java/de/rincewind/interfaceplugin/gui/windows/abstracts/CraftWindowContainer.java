package de.rincewind.interfaceplugin.gui.windows.abstracts;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import de.rincewind.interfaceapi.InterfaceAPI;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowContainer;
import de.rincewind.interfaceapi.gui.windows.util.WindowState;
import de.rincewind.interfaceapi.handling.window.WindowChangeStateEvent;
import de.rincewind.interfaceplugin.setup.CraftSetup;

public abstract class CraftWindowContainer extends CraftWindowNameable implements WindowContainer {
	
	private Inventory inventory;
	
	public CraftWindowContainer() {
		super();
		
		this.getEventManager().registerListener(WindowChangeStateEvent.class, (event) -> {
			if (event.getNewState() == WindowState.MAXIMIZED) {
				this.reconfigurate();
			}
		}).addBefore();
	}
	
	@Override
	public void setName(String name) {
		super.setName(name);
		this.reconfigurate();
	}
	
	public abstract int getSlot(Point point);
	
	public abstract Point getPoint(int bukkitSlot);
	
	public abstract Icon getIcon(Point point);
	
	public abstract Inventory newInventory();
	
	public void createBukkitInventory() {
		this.inventory = this.newInventory();
	}
	
	protected void update() {
		this.inventory.clear();
		
		this.iterate((point) -> {
			this.update(point);
		});
		
		this.updateInventory();
	}
	
	protected void update(Point point) {
		Icon icon = this.getIcon(point);
		
		if (icon == null) {
			icon = new Icon(Material.AIR);
		}
		
		this.inventory.setItem(this.getSlot(point), icon.toItem());
	}
	
	protected void update(Iterable<Point> points) {
		for (Point point : points) {
			this.update(point);
		}
	}
	
	protected void updateInventory() {
		if (this.getUser() != null) {
			this.getUser().updateInventory();
		}
	}
	
	protected void reconfigurate() {
		this.createBukkitInventory();
		this.update();
		this.openBukkitInventory();
	}
	
	private void openBukkitInventory() {
		if (this.getState() != WindowState.MAXIMIZED) {
			return;
		}
		
		((CraftSetup) InterfaceAPI.getSetup(this.getUser())).sendClosePacket();
		this.getUser().openInventory(this.inventory);
	}
	
}
