package de.rincewind.plugin.setup;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.rincewind.api.exceptions.APIException;
import de.rincewind.api.gui.windows.WindowAnvil;
import de.rincewind.api.gui.windows.abstracts.Window;
import de.rincewind.api.gui.windows.abstracts.WindowContainer;
import de.rincewind.api.gui.windows.util.Status;
import de.rincewind.api.handling.events.WindowCloseEvent;
import de.rincewind.api.handling.events.WindowMaximizeEvent;
import de.rincewind.api.handling.events.WindowMinimizeEvent;
import de.rincewind.api.handling.events.WindowMoveBackEvent;
import de.rincewind.api.handling.events.WindowOpenEvent;
import de.rincewind.api.setup.FileBrowser;
import de.rincewind.api.setup.Setup;
import de.rincewind.plugin.InterfacePlugin;
import de.rincewind.plugin.listener.InventoryCloseListener;
import lib.securebit.Validate;

@SuppressWarnings("deprecation")
public class CraftSetup implements Setup {

	private List<Window> windows;
	
	private Player owner;
	
	private InterfacePlugin plugin;
	
	private String clipBoard;
	
	private FileBrowser browser;
	
	public CraftSetup(Player player, InterfacePlugin plugin) {
		this.windows = new ArrayList<>();
		this.plugin = plugin;
		this.owner = player;
		this.browser = new CraftFileBrowser(this, new File("plugins"));
	}
	
	@Override
	public String getClipBoard() {
		return this.clipBoard;
	}

	@Override
	public void setClipBoard(String clipBoard) {
		this.clipBoard = clipBoard;
	}
	
	@Override
	public List<Window> getOpenWindows() {
		return Collections.unmodifiableList(this.windows);
	}

	@Override
	public Window getMaximizedWindow() {
		for (Window window : this.windows) {
			if(window.getStatus() == Status.MAXIMIZED) {
				return window;
			}
		}
		
		return null;
	}

	@Override
	public Window getWindow(int index) {
		if (index < 0 || index >= this.windows.size()) {
			throw new APIException("Invalid index!");
		}
		
		return this.windows.get(index);
	}

	@Override
	public boolean hasMaximizedWindow() {
		return this.getMaximizedWindow() != null;
	}
	
	@Override
	public boolean hasOpenWindow(Window window) {
		Validate.notNull(window, "The window cannot be null!");
		
		if (this.windows.contains(window)) {
			return true;
		}
		
		return false;
	}

	@Override
	public void open(Window window) {
		Validate.notNull(window, "The window cannot be null!");
		
		if (window.getUser() != null) {
			throw new APIException("The window is already opend!");
		}
		
		window.getEventManager().callEvent(WindowOpenEvent.class, new WindowOpenEvent(this.owner, window));
		this.windows.add(window);
		
		this.maximize(window);
	}
	
	@Override
	public void close(Window window) {
		Validate.notNull(window, "The window cannot be null!");
		
		if (!this.hasOpenWindow(window)) {
			throw new APIException("The window is not opend by this player!");
		}
		
		if (window.getStatus() == Status.MAXIMIZED) {
			this.minimize();
		}
		
		window.getEventManager().callEvent(WindowCloseEvent.class, new WindowCloseEvent(window));
		this.windows.remove(window);
	}

	@Override
	public void close(int index) {
		Window window = this.getWindow(index);
		
		if (window != null) {
			this.close(window);
		}
	}

	@Override
	public void closeAll() {
		while (this.windows.size() > 0) {
			this.close(this.windows.get(0));
		}
	}

	@Override
	public void maximize(Window window) {
		Validate.notNull(window, "The window cannot be null!");
		
		if (!this.hasOpenWindow(window)) {
			throw new APIException("The window is not opend by this player!");
		}
		
		if (this.hasMaximizedWindow()) {
			if (this.getMaximizedWindow().equals(window)) {
				throw new APIException("The window is already maximized!");
			}
			
			Window maximized = this.getMaximizedWindow();
			
			maximized.getEventManager().callEvent(WindowMoveBackEvent.class, new WindowMoveBackEvent(window));
			
			if (maximized instanceof WindowContainer) {
				this.sendClosePacket();
			}
		}
		
		window.getEventManager().callEvent(WindowMaximizeEvent.class, new WindowMaximizeEvent(window));
	}

	@Override
	public void maximize(int index) {
		Window window = this.getWindow(index);
		
		if (window != null) {
			this.maximize(window);
		}
	}

	@Override
	public void minimize() {
		if (!this.hasMaximizedWindow()) {
			return;
		}
		
		Window window = this.getMaximizedWindow();
		window.getEventManager().callEvent(WindowMinimizeEvent.class, new WindowMinimizeEvent(window));
		
		if (window instanceof WindowContainer || window instanceof WindowAnvil) {
			this.sendClosePacket();
		}
		
		List<Window> reverse = new ArrayList<>(this.windows);
		Collections.reverse(reverse);
		
		for (Window background : reverse) {
			if (background.getStatus() == Status.BACKGROUND) {
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, () -> {
					this.maximize(background);
				}, 1L);
				
				break;
			}
		}
	}
	
	public void sendClosePacket() {
		InventoryCloseListener.blocked.add(this.owner.getName());
		this.owner.closeInventory();
		InventoryCloseListener.blocked.remove(this.owner.getName());
	}

	@Override
	@Deprecated
	public FileBrowser getFileBrowser() {
		return this.browser;
	}

	@Override
	public Player getOwner() {
		return this.owner;
	}
	
}
