package de.rincewind.api.gui.windows.abstracts;

import org.bukkit.entity.Player;

import de.rincewind.api.gui.components.EventBased;
import de.rincewind.api.gui.windows.util.Status;
import de.rincewind.api.setup.Setup;

/**
 * This is the main window-class. All other windows extend from this class.
 * With this window, you are only able to open this window to a player.
 * When you create a new window, the user is not set before opening the window
 * in the {@link Setup}.
 * 
 * @author Rincewind34
 * @since 2.3.3
 * 
 * @see WindowNameable
 */
public abstract interface Window extends EventBased {
	
	/**
	 * Returns the current state of this window.
	 * The default state is <code>WindowDefaults#STATE</code>.
	 * 
	 * @return the current state of this window.
	 * 
	 * @see Setup
	 */
	public abstract Status getStatus();
	
	/**
	 * Returns the user of this window. If the
	 * window does not have a user yet, this method will
	 * return null.
	 * 
	 * @return the user of this window.
	 */
	public abstract Player getUser();
	
	/**
	 * Returns if the window already has a user.
	 * 
	 * @return if the window already has a user.
	 */
	public abstract boolean isOpened();
	
}
