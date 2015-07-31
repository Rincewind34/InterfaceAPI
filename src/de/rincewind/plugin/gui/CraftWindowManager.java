package de.rincewind.plugin.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.rincewind.api.gui.WindowManager;
import de.rincewind.api.gui.windows.Window;
import de.rincewind.api.gui.windows.util.Status;
import de.rincewind.defaults.listener.InventoryCloseListener;
import de.rincewind.plugin.InterfacePlugin;

public class CraftWindowManager implements WindowManager, Listener {
	
	private Map<Player, List<Window>> windows;
	
	private InterfacePlugin plugin;
	
	public CraftWindowManager(InterfacePlugin plugin) {
		this.windows = new HashMap<Player, List<Window>>();
		this.plugin = plugin;
		this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@Override
	public List<Window> getOpenWindows(Player player) {
		if (player == null) {
			return null;
		}
		
		if (!this.windows.containsKey(player)) {
			this.windows.put(player, new ArrayList<Window>());
		}
		
		return this.windows.get(player);
	}

	@Override
	public Window getMaximizedWindow(Player player) {
		if (player == null) {
			return null;
		}
		
		if (!this.windows.containsKey(player)) {
			this.windows.put(player, new ArrayList<Window>());
		}
		
		for (Window window : this.windows.get(player)) {
			if(window.getStatus() == Status.MAXIMIZED) return window;
		}
		
		return null;
	}

	@Override
	public Window getWindow(Player player, int index) {
		if (player == null) {
			return null;
		}
		
		if (!this.windows.containsKey(player)) {
			this.windows.put(player, new ArrayList<Window>());
		}
		
		if (index < 0 || index >= this.windows.get(player).size()) {
			return null;
		}
		
		return this.windows.get(player).get(index);
	}

	@Override
	public boolean hasMaximizedWindow(Player player) {
		return this.getMaximizedWindow(player) != null;
	}
	
	@Override
	public boolean hasOpenWindow(Player player, Window window) {
		if (player == null) {
			return false;
		}
		
		if (window == null) {
			return false;
		}
		
		if (!this.windows.containsKey(player)) {
			this.windows.put(player, new ArrayList<Window>());
		}
		
		if (this.windows.get(player).contains(window)) {
			return true;
		}
		
		return false;
	}

	@Override
	public void open(Player player, Window window) {
		if (player == null) {
			return;
		}
		
		if (window == null) {
			return;
		}
		
		if (!this.windows.containsKey(player)) {
			this.windows.put(player, new ArrayList<Window>());
		}
		
		if (this.hasOpenWindow(player, window)) {
			return;
		}
		
		((CraftWindow) window).setUser(player);
		window.open();
		this.windows.get(player).add(window);
		
		this.maximize(player, window);
	}
	
	@Override
	public void close(Player player, Window window) {
		if (player == null) {
			return;
		}
		
		if (window == null) {
			return;
		}
		
		if (!this.windows.containsKey(player)) {
			this.windows.put(player, new ArrayList<Window>());
		}
		
		if (!this.hasOpenWindow(player, window)) {
			return;
		}
		
		if (window.getStatus() == Status.MAXIMIZED) {
			this.minimize(player);
		}
		
		window.close();
		this.windows.get(player).remove(window);
	}

	@Override
	public void close(Player player, int index) {
		Window window = this.getWindow(player, index);
		
		if (window != null) {
			this.close(player, window);
		}
	}

	@Override
	public void closeAll(Player player) {
		if (player == null) {
			return;
		}
		
		if (!this.windows.containsKey(player)) {
			this.windows.put(player, new ArrayList<Window>());
		}
		
		while (this.windows.get(player).size() > 0) {
			this.close(player, this.windows.get(player).get(0));
		}
	}

	@Override
	public void maximize(Player player, Window window) {
		if (player == null) {
			return;
		}
		
		if (window == null) {
			return;
		}
		
		if (!this.windows.containsKey(player)) {
			this.windows.put(player, new ArrayList<Window>());
		}
		
		if (!this.hasOpenWindow(player, window)) {
			return;
		}
		
		if (this.hasMaximizedWindow(player)) {
			Window maximized = this.getMaximizedWindow(player);
			
			maximized.moveBack();
			CraftWindowManager.sendClosePacket(player);
		}
		
		window.maximize();
	}

	@Override
	public void maximize(Player player, int index) {
		Window window = this.getWindow(player, index);
		
		if (window != null) {
			this.maximize(player, window);
		}
	}

	@Override
	public void minimize(final Player player) {
		if (player == null) {
			return;
		}
		
		if (!this.windows.containsKey(player)) {
			this.windows.put(player, new ArrayList<Window>());
		}
		
		if (!this.hasMaximizedWindow(player)) {
			return;
		}
		
		Window window = this.getMaximizedWindow(player);
		window.minimize();
		
		CraftWindowManager.sendClosePacket(player);
		
		for (final Window background : this.windows.get(player)) {
			if (background.getStatus() == Status.BACKGROUND) {
				
				//TODO
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
					
					@Override
					public void run() {
						CraftWindowManager.this.maximize(player, background);
					}
				}, 1L);
				
				break;
			}
		}
	}
	
	public static void sendClosePacket(Player player) {
		if (player == null) {
			return;
		}
		
		InventoryCloseListener.blocked.add(player.getName());
		player.closeInventory();
		InventoryCloseListener.blocked.remove(player.getName());
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		
		if (this.windows.containsKey(player)) {
			closeAll(player);
			this.windows.remove(player);
		}
	}

}
