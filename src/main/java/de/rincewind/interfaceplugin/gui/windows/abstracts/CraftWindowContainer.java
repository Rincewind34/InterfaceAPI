package de.rincewind.interfaceplugin.gui.windows.abstracts;

import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.util.Point;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowContainer;
import de.rincewind.interfaceapi.gui.windows.util.WindowState;
import de.rincewind.interfaceapi.handling.window.WindowChangeStateEvent;

public abstract class CraftWindowContainer extends CraftWindowNameable implements WindowContainer {

	private boolean renderClosed;
	private boolean bypassRendering;

	private Inventory inventory;

	public CraftWindowContainer(Plugin plugin) {
		super(plugin);

		this.getEventManager().registerListener(WindowChangeStateEvent.class, (event) -> {
			if (event.getNewState() == WindowState.MAXIMIZED) {
				if (!this.renderClosed) {
					this.reconfigure();
				}

				// #reconfigure() does not open the inventory, for this event is called before the window state is set.
				this.getUser().openInventory(this.inventory);
			}
		}).addAfter();
	}

	public abstract int getSlot(Point point);

	public abstract Point getPoint(int bukkitSlot);

	public abstract Icon getIcon(Point point);

	public abstract Inventory newInventory();

	@Override
	public void setRenderClosed(boolean value) {
		if (this.renderClosed != value) {
			this.renderClosed = value;

			if (this.getState() != WindowState.MAXIMIZED && this.renderClosed) {
				this.reconfigure();
			}
		}
	}
	
	@Override
	public void setRenderBypass(boolean bypass) {
		this.bypassRendering = bypass;
	}
	
	@Override
	public boolean isRenderBypass() {
		return this.bypassRendering;
	}

	@Override
	public boolean isRenderClosed() {
		return this.renderClosed;
	}

	@Override
	public void setName(String name) {
		if (!this.getName().equals(name)) {
			super.setName(name);

			if (this.renderClosed || this.getState() == WindowState.MAXIMIZED) {
				this.reconfigure();
			}
		}
	}

	protected void renderFrame() {
		assert this.renderClosed || this.getState() == WindowState.MAXIMIZED : "Closed rendering is disabled";

		this.iterate((point) -> {
			this.renderPoint(point);
		});

		this.updateInventory();
	}

	protected void renderPoint(Point point) {
		assert this.renderClosed || this.getState() == WindowState.MAXIMIZED : "Closed rendering is disabled";

		if (!this.bypassRendering) {
			Icon icon = this.getIcon(point);
			this.inventory.setItem(this.getSlot(point), icon != null ? icon.toItem() : null);
		}
	}

	protected void renderPoints(Iterable<Point> points) {
		assert this.renderClosed || this.getState() == WindowState.MAXIMIZED : "Closed rendering is disabled";

		for (Point point : points) {
			this.renderPoint(point);
		}
	}

	protected void updateInventory() {
		if (this.getUser() != null) {
			this.getUser().updateInventory();
		}
	}

	protected void reconfigure() {
		assert this.renderClosed || this.getState() == WindowState.MAXIMIZED : "Closed rendering is disabled";

		this.inventory = this.newInventory();
		this.renderFrame();

		if (this.getState() != WindowState.MAXIMIZED) {
			this.getUser().openInventory(this.inventory);
		}
	}

}
