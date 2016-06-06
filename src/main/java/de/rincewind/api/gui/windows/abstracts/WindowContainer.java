package de.rincewind.api.gui.windows.abstracts;

/**
 * This window is InventoryGUI. If you change the name or 
 * something like this, the inventory will be automatically
 * recreated and if this window already has a user, the inventory
 * will be opened completely new.
 * 
 * @author Rincewind34
 * @since 2.3.3
 * 
 * @see WindowEditor
 */
public abstract interface WindowContainer extends WindowNameable {
	
	/**
	 * This method updates the bukkit-inventory with all inserted
	 * items.
	 */
	public abstract void updateBukkitInventory();
	
	/**
	 * Returns the bukkit-size of this window.
	 * 
	 * @return the bukkit-size of this window.
	 */
	public abstract int getBukkitSize();
	
}
