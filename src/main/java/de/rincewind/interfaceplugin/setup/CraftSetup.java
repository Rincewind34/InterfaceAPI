package de.rincewind.interfaceplugin.setup;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.rincewind.interfaceapi.InterfaceAPI;
import de.rincewind.interfaceapi.exceptions.SetupException;
import de.rincewind.interfaceapi.gui.windows.WindowAnvil;
import de.rincewind.interfaceapi.gui.windows.abstracts.Window;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowContainer;
import de.rincewind.interfaceapi.gui.windows.selectors.WindowSelector;
import de.rincewind.interfaceapi.gui.windows.util.WindowState;
import de.rincewind.interfaceapi.handling.window.WindowChangeStateEvent;
import de.rincewind.interfaceapi.handling.window.WindowOpenEvent;
import de.rincewind.interfaceapi.setup.FileBrowser;
import de.rincewind.interfaceapi.setup.Setup;
import de.rincewind.interfaceplugin.InterfacePlugin;
import de.rincewind.interfaceplugin.Validate;
import de.rincewind.interfaceplugin.listener.InventoryCloseListener;

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
			if (window.getState() == WindowState.MAXIMIZED) {
				return window;
			}
		}

		return null;
	}

	@Override
	public Window getWindow(int index) {
		return this.windows.get(index);
	}

	@Override
	public boolean hasMaximizedWindow() {
		return this.getMaximizedWindow() != null;
	}

	@Override
	public boolean hasOpenWindow(Window window) {
		Validate.notNull(window, "The window cannot be null!");

		return this.windows.contains(window);
	}

	@Override
	public void open(Window window) {
		Validate.notNull(window, "The window cannot be null!");

		if (window.getUser() != null) {
			throw new SetupException("The window is already opend!");
		}

		window.getEventManager().callEvent(WindowOpenEvent.class, new WindowOpenEvent(window, this.owner));
		this.windows.add(window);

		this.maximize(window);
	}

	@Override
	public void close(Window window) {
		Validate.notNull(window, "The window cannot be null!");

		if (!this.hasOpenWindow(window)) {
			throw new SetupException("The window is not opend by this player!");
		}

		if (window.getState() == WindowState.MAXIMIZED) {
			this.minimize();
		}

		window.getEventManager().callEvent(WindowChangeStateEvent.class, new WindowChangeStateEvent(window, WindowState.CLOSED));
		this.windows.remove(window);
	}

	@Override
	public void close(int index) {
		this.close(this.getWindow(index));
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
			throw new SetupException("The window is not opend by this player!");
		}

		if (this.hasMaximizedWindow()) {
			if (this.getMaximizedWindow() == window) {
				throw new SetupException("The window is already maximized!");
			}

			Window maximized = this.getMaximizedWindow();
			maximized.getEventManager().callEvent(WindowChangeStateEvent.class, new WindowChangeStateEvent(window, WindowState.BACKGROUND));

			if (maximized instanceof WindowContainer) {
				this.sendClosePacket();
			}
		}
		
		window.getEventManager().callEvent(WindowChangeStateEvent.class, new WindowChangeStateEvent(window, WindowState.MAXIMIZED));
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
		window.getEventManager().callEvent(WindowChangeStateEvent.class, new WindowChangeStateEvent(window, WindowState.MINIMIZED));

		if (window instanceof WindowContainer || window instanceof WindowAnvil) {
			this.sendClosePacket();
		}

		List<Window> reverse = new ArrayList<>(this.windows);
		Collections.reverse(reverse);

		for (Window background : reverse) {
			if (background.getState() == WindowState.BACKGROUND) {
				this.maximize(background);
				break;
			}
		}
	}

	public void sendClosePacket() {
		InventoryCloseListener.blocked.add(this.owner.getName());
		this.owner.closeInventory();
	}

	public InterfacePlugin getPlugin() {
		return this.plugin;
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
	
	@Override
	public <T> WindowSelector<T> openSelector(Class<T> typeClass, Plugin plugin, Iterable<T> elements, Consumer<T> action) {
		// Let InterfaceAPI#newSelector(...) validate the parameters
		WindowSelector<T> window = InterfaceAPI.newSelector(typeClass, plugin, elements, action);
		
		if (window == null) {
			return null;
		}
		
		this.open(window);
		return window;
	}

}
