package de.rincewind.api.setup;

import java.util.List;

import org.bukkit.entity.Player;

import de.rincewind.api.exceptions.APIException;
import de.rincewind.api.gui.util.EventManager;
import de.rincewind.api.gui.windows.abstracts.Window;
import de.rincewind.api.gui.windows.util.WindowState;
import de.rincewind.api.handling.window.WindowOpenEvent;

public interface Setup {
	
	public abstract String getClipBoard();
	
	public abstract void setClipBoard(String clipBoard);
	
	@Deprecated
	public abstract FileBrowser getFileBrowser();
	
	public abstract Player getOwner();
	
	/**
	 * Returns all open windows of a player. If the player has not any open
	 * windows this method will return an empty List.
	 * 
	 * @return all open windows of a player
	 * 
	 * @throws NullPointerException if the player is <b>null</b>
	 */
	public abstract List<Window> getOpenWindows();
	
	/**
	 * Returns the maximized window of a player. If the player does not 
	 * have a maximized window this method will return <b>null</b>.
	 * 
	 * @return the maximized window
	 * 
	 * @throws NullPointerException if the player is <b>null</b>
	 */
	public abstract Window getMaximizedWindow();
	
	/**
	 * Returns the window-instance stored with the specified index.
	 * 
	 * @param index to get the window
	 * 
	 * @return the window-instance stored with the specified index
	 * 
	 * @throws APIException if the index is smaller than 0 or greater than the
	 * 			count of the opened windows
	 * @throws NullPointerException if the player is <b>null</b>
	 */
	public abstract Window getWindow(int index);
	
	/**
	 * Returns <b>true</b> if the player has a maximized window and
	 * <b>false</b> if not.
	 * 
	 * @return <b>true</b> if the player has a maximized window and
	 * 			<b>false</b> is not
	 * 
	 * @throws NullPointerException if the player is <b>null</b>
	 */
	public abstract boolean hasMaximizedWindow();
	
	/**
	 * Returns <b>true</b> if the player has a opened a specified window-
	 * instance and <b>false</b> if not.
	 * 
	 * @param window The window instance to check
	 * 
	 * @return <b>true</b> if the player has a maximized window and
	 * 			<b>false</b> is not
	 * 
	 * @throws NullPointerException if the player or the window is <b>null</b>
	 */
	public abstract boolean hasOpenWindow(Window window);
	
	/**
	 * Opens a window to a player. This window will be automatically
	 * maximized and if the player already has a maximized window, the current
	 * window will be minimized. The current window will be maximized, if the player closes the new
	 * window. The window, witch will be minimized, will get the {@link WindowState#BACKGROUND}.
	 * If you do not want, that the current window will be maximized on 
	 * closing the new window, invoke {@link Setup#minimize()}
	 * before opening the new window.
	 * 
	 * The {@link WindowOpenEvent} will be called in the {@link EventManager}.
	 * 
	 * To maximize the new window, the method 
	 * {@link Setup#maximize(Window)} will be called.
	 * 
	 * @param window to open
	 * 
	 * @throws NullPointerException if the player or the window is <b>null</b>.
	 * @throws APIException if the window is already opened by a player.
	 */
	public abstract void open(Window window);
	
	/**
	 * Closes an open window to a player. The window to close does not has to be
	 * in the state {@link WindowState#MAXIMIZED}. All other states are possible too and 
	 * the window will be closed.
	 * If the closing window is the maximized window of the given player
	 * the method {@link Setup#minimize()} will be called.
	 * 
	 * The {@link WindowCloseEvent} will be called in the {@link EventManager}.
	 * 
	 * @param window to close
	 * 
	 * @throws NullPointerException if the player or the window is <b>null</b>
	 * @throws APIException if the given player does not own the given window
	 */
	public abstract void close(Window window);
	
	/**
	 * Closes the window stored with the given index, calling 
	 * {@link Setup#close(Window)}. To get the window to close 
	 * the method {@link Setup#getWindow(int)} will be used.
	 * 
	 * @param index to get the window to close.
	 * 
	 * @see Setup#getWindow(int)
	 * @see Setup#close(Window)
	 */
	public abstract void close(int index);
	
	/**
	 * Closes all open windows from a specified player, 
	 * calling {@link Setup#close(Window)}
	 * 
	 * @see Setup#close(Window)
	 */
	public abstract void closeAll();
	
	/**
	 * Maximizes a window to a player. If a window is currently maximized
	 * the current window will get the state {@link WindowState#BACKGROUND}. If the new 
	 * maximized window gets minimized the first window with the state 
	 * {@link WindowState#BACKGROUND} will be automatically maximized.
	 * 
	 * The {@link WindowMaximizeEvent} will be called in the {@link EventManager}.
	 * 
	 * @param window to maximize
	 * 
	 * @throws NullPointerException if the player or the window is <b>null</b>
	 * @throws APIException if the player does not own the window to maximize
	 * 			or the window is already maximized to the given player
	 */
	public abstract void maximize(Window window);
	
	/**
	 * Maximizes the window saved with a given index, calling 
	 * {@link Setup#maximize(Window)}. To get the window to maximize 
	 * the method {@link Setup#getWindow(int)} will be used.
	 * 
	 * @param index to get the window to maximize
	 * 
	 * @see Setup#getWindow(int)
	 * @see Setup#maximize(Window)
	 */
	public abstract void maximize(int index);
	
	/**
	 * Minimizes the currently maximized window. If the player does not have a
	 * maximized window this method will do nothing.
	 * 
	 * If the player has any other opened windows with the state {@link WindowState#BACKGROUND}
	 * the first window with this state will be maximized with a little delay of one tick.
	 * 
	 * The {@link WindowMinimizeEvent} will be called in the {@link EventManager}.
	 * 
	 * @throws NullPointerException if the player is <b>null</b>
	 */
	public abstract void minimize();
	
}
