package de.rincewind.interfaceplugin.setup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.rincewind.interfaceapi.InterfaceAPI;
import de.rincewind.interfaceapi.exceptions.SetupException;
import de.rincewind.interfaceapi.gui.windows.WindowAnvil;
import de.rincewind.interfaceapi.gui.windows.abstracts.Window;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowContainer;
import de.rincewind.interfaceapi.gui.windows.util.WindowState;
import de.rincewind.interfaceapi.handling.window.WindowChangeStateEvent;
import de.rincewind.interfaceapi.handling.window.WindowOpenEvent;
import de.rincewind.interfaceapi.selectors.window.WindowSelector;
import de.rincewind.interfaceapi.setup.Setup;
import de.rincewind.interfaceplugin.InterfacePlugin;
import de.rincewind.interfaceplugin.Validate;
import de.rincewind.interfaceplugin.listener.InventoryCloseListener;

public class CraftSetup implements Setup { // TODO cache maximized window

	private List<Window> windows;

	private Player owner;

	private InterfacePlugin plugin;

	private String clipBoard;

	public CraftSetup(Player player, InterfacePlugin plugin) {
		this.windows = new ArrayList<>();
		this.plugin = plugin;
		this.owner = player;
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

		// TODO maximize already opened window to avoid things like QuestSystem ChapterWindow

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

		if (!this.windows.contains(window)) {
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
	public void closeMaximized() {
		if (this.hasMaximizedWindow()) {
			this.close(this.getMaximizedWindow());
		} else {
			throw new SetupException("No window is maximized");
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

		if (!this.windows.contains(window)) {
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
	public void maximizeAll() {
		int start;
		
		if (!this.hasMaximizedWindow() && !this.windows.isEmpty()) {
			this.maximize(this.windows.size() - 1);
			start = this.windows.size() - 2;
		} else {
			start = this.windows.size() - 1;
		}
		
		for (int index = start; index >= 0; index--) {
			Window minimized = this.windows.get(index);

			if (minimized.getState() == WindowState.MINIMIZED) {
				minimized.getEventManager().callEvent(WindowChangeStateEvent.class,
						new WindowChangeStateEvent(minimized, WindowState.BACKGROUND));
			}
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

		for (int index = this.windows.size() - 1; index >= 0; index--) {
			Window background = this.windows.get(index);

			if (background.getState() == WindowState.BACKGROUND) {
				// Delay, because the interact listener doesn't detect clicks without
				Bukkit.getScheduler().runTask(InterfacePlugin.instance, () -> {
					this.maximize(background);
				});

				return;
			}
		}
	}

	@Override
	public void minimizeAll() {
		if (!this.hasMaximizedWindow()) {
			return;
		}

		Window window = this.getMaximizedWindow();
		window.getEventManager().callEvent(WindowChangeStateEvent.class, new WindowChangeStateEvent(window, WindowState.MINIMIZED));

		if (window instanceof WindowContainer || window instanceof WindowAnvil) {
			this.sendClosePacket();
		}

		for (int index = this.windows.size() - 1; index >= 0; index--) {
			Window background = this.windows.get(index);

			if (background.getState() == WindowState.BACKGROUND) {
				background.getEventManager().callEvent(WindowChangeStateEvent.class,
						new WindowChangeStateEvent(window, WindowState.MINIMIZED));
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
	public Player getOwner() {
		return this.owner;
	}

	@Override
	public <T> WindowSelector<T> openSelector(Class<T> typeClass, Plugin plugin, Consumer<? super T> action) {
		// Let WindowSelectorCreator#newWindow(...) validate the parameters
		WindowSelector<T> window = InterfaceAPI.getWindowCreator(typeClass).newWindow(plugin, action);

		if (window == null) {
			return null;
		}

		this.open(window);
		return window;
	}

	@Override
	public <T> WindowSelector<T> openSelector(Class<T> typeClass, Plugin plugin, Collection<? extends T> elements,
			Consumer<? super T> action) {
		// Let WindowSelectorCreator#newWindow(...) validate the parameters
		WindowSelector<T> window = InterfaceAPI.getWindowCreator(typeClass).newWindow(plugin, action, elements);

		if (window == null) {
			return null;
		}

		this.open(window);
		return window;
	}

	@Override
	public <T> WindowSelector<T> openSelector(Class<T> typeClass, Plugin plugin, T current, Consumer<? super T> action) {
		// Let WindowSelectorCreator#newWindow(...) validate the parameters
		WindowSelector<T> window = InterfaceAPI.getWindowCreator(typeClass).newWindow(plugin, action, current);

		if (window == null) {
			return null;
		}

		this.open(window);
		return window;
	}

	@Override
	public <T> WindowSelector<T> openSelector(Class<T> typeClass, Plugin plugin, T current, Collection<? extends T> elements,
			Consumer<? super T> action) {
		// Let WindowSelectorCreator#newWindow(...) validate the parameters
		WindowSelector<T> window = InterfaceAPI.getWindowCreator(typeClass).newWindow(plugin, action, elements, current);

		if (window == null) {
			return null;
		}

		this.open(window);
		return window;
	}

}
