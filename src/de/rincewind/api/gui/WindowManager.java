package de.rincewind.api.gui;

import java.util.List;

import org.bukkit.entity.Player;

import de.rincewind.api.gui.windows.Window;

public abstract interface WindowManager {
	
	/**
	 * Get all active windows from a player
	 * @param player The target
	 * @return All windows of the player; the status of the windows doesnt care
	 */
	public abstract List<Window> getOpenWindows(Player player);
	
	/**
	 * @param player The target
	 * @return The currently maximized window, which the player can see (Status.MAXIMIZED)
	 */
	public abstract Window getMaximizedWindow(Player player);
	
	/**
	 * @param player The target
	 * @param index The index in the active window list
	 * @return The window object with the specified index
	 */
	public abstract Window getWindow(Player player, int index);
	
	/**
	 * @param player The target
	 * @return If the player has an activated window, which is maximed (Status.MAXIMIZED)
	 */
	public abstract boolean hasMaximizedWindow(Player player);
	
	/**
	 * @param player The target
	 * @param window The target window
	 * @return If the window is currently activated (but not necessarily maximized)
	 */
	public abstract boolean hasOpenWindow(Player player, Window window);
	
	/**
	 * 
	 * Opens a window for a player
	 * 
	 * @param player The target player
	 * @param window The window to open
	 */
	public abstract void open(Player player, Window window);
	
	/**
	 * 
	 * Closes an active window of a player
	 * 
	 * @param player the target player
	 * @param window the window
	 */
	public abstract void close(Player player, Window window);
	
	/**
	 * 
	 * Closes a window with the specified index in the activewindowlist of a player
	 * 
	 * @param player the target player
	 * @param id the index of the window
	 */
	public abstract void close(Player player, int index);
	
	/**
	 * 
	 * Closes all the active windows of a player
	 * 
	 * @param player the target player
	 */
	public abstract void closeAll(Player player);
	
	/**
	 * 
	 * Maximizes a open window of a player
	 * 
	 * @param player the target palyer
	 * @param window the window
	 */
	public abstract void maximize(Player player, Window window);
	
	/**
	 * 
	 * Maximizes a window with the specified index in the activewindowlist of a player
	 * 
	 * @param player the player
	 * @param id the index of the window
	 */
	public abstract void maximize(Player player, int index);
	
	/**
	 * 
	 * Minimizes the currently maximized window
	 * 
	 * @param player the target player
	 */
	public abstract void minimize(Player player);
}
