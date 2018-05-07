package de.rincewind.interfaceplugin.gui.windows.abstracts;

import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowContainer;
import de.rincewind.interfaceapi.gui.windows.util.WindowState;
import de.rincewind.interfaceapi.handling.window.WindowChangeStateEvent;

public abstract class CraftWindowContainer extends CraftWindowNameable implements WindowContainer {

	private Inventory inventory;

	public CraftWindowContainer(Plugin plugin) {
		super(plugin);
		
		this.getEventManager().registerListener(WindowChangeStateEvent.class, (event) -> {
			if (event.getNewState() == WindowState.MAXIMIZED) {
				this.reconfigurate();
			}
		}).addAfter();
	}

	public abstract int getSlot(Point point);

	public abstract Point getPoint(int bukkitSlot);

	public abstract Icon getIcon(Point point);

	public abstract Inventory newInventory();

	@Override
	public void setName(String name) {
		if (!this.getName().equals(name)) {
			super.setName(name);

			this.reconfigurate();
		}
	}

	public final void createBukkitInventory() {
		this.inventory = this.newInventory();
	}

	protected void renderFrame() {
		this.iterate((point) -> {
			this.renderPoint(point);
		});

		this.updateInventory();
	}

	protected void renderPoint(Point point) {
		this.inventory.setItem(this.getSlot(point), this.getIcon(point).toItem());
	}

	protected void renderPoints(Iterable<Point> points) {
		for (Point point : points) {
			this.renderPoint(point);
		}
	}

	protected void updateInventory() {
		if (this.getUser() != null) {
			this.getUser().updateInventory();
		}
	}

	protected void reconfigurate() {
		this.createBukkitInventory();
		this.renderFrame();
		this.openBukkitInventory();
	}

	private void openBukkitInventory() {
		if (this.getState() != WindowState.MAXIMIZED) {
			return;
		}

		this.getUser().openInventory(this.inventory);
	}

}
