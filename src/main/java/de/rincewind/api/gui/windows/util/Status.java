package de.rincewind.api.gui.windows.util;

import org.bukkit.entity.Player;

import de.rincewind.api.gui.windows.abstracts.Window;

/**
 * This enum is used by {@link Window} to save the current window-state
 * 
 * @author Rincewind34
 * @since 2.3.3
 * 
 * @see WindowManager
 */
public enum Status {
	
	/**
	 * If the window is in this state, to the owing player ({@link Window#getUser()}
	 * is the window-instance displayed.
	 */
	MAXIMIZED,
	
	/**
	 * @see WindowManager#minimize(Player)
	 */
	BACKGROUND,
	
	/**
	 * If the window is in this state, the window is only opened and
	 * you can close maximize it
	 * 
	 * @see WindowManager#close(Player, Window)
	 * @see WindowManager#maximize(Player, Window)
	 */
	MINIMIZED;
	
}
