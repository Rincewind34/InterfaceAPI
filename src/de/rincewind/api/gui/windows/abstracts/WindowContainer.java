package de.rincewind.api.gui.windows.abstracts;

import org.bukkit.inventory.Inventory;

import de.rincewind.plugin.gui.CraftWindowManager;

public abstract class WindowContainer extends WindowNameable {
	
	protected Inventory inv;
	
	/**
	 * WindowContainer: This window is an BukkitInventory like a furnace or a chest
	 */
	public WindowContainer() {
		
	}
	
	@Override
	public void maximize() {
		super.maximize();
		this.reconfigurate();
	}
	
	@Override
	public boolean setName(String name) {
		if (!super.setName(name)) {
			return false;
		} else {
			this.reconfigurate();
			return true;
		}
	}
	
	public void updateBukkitInventory() {
		this.inv.clear();
	}
	
	/**
	 * Opens the BukkitInventory
	 */
	public void openBukkitInventory() {
		if (getUser() == null) {
			return;
		}
		
		CraftWindowManager.sendClosePacket(getUser());
		this.getUser().openInventory(this.inv);
	}
	
	/**
	 * 
	 * @return The BukkitInventory
	 */
	public Inventory getInventory() {
		return this.inv;
	}
	
	protected abstract void createBukkitInventory();
	
	protected void reconfigurate() {
		this.createBukkitInventory();
		this.updateBukkitInventory();
		this.openBukkitInventory();
	}
	
}
