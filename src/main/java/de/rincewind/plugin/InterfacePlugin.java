package de.rincewind.plugin;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.rincewind.api.InterfaceAPI;
import de.rincewind.api.item.categorys.Categorys;
import de.rincewind.api.setup.Setup;
import de.rincewind.plugin.listener.InventoryClickListener;
import de.rincewind.plugin.listener.InventoryCloseListener;
import de.rincewind.plugin.listener.PlayerQuitListener;

public class InterfacePlugin extends JavaPlugin {
	
	public static Map<Player, Setup> setups;
	
	public static InterfacePlugin instance;
	
	@Override
	public void onEnable() {
		InterfacePlugin.instance = this;
		InterfacePlugin.setups = new HashMap<>();
		
		new Categorys();
		
		this.getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
		this.getServer().getPluginManager().registerEvents(new InventoryCloseListener(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
	}
	
	@Override
	public void onDisable() {
		for (Setup setup : InterfacePlugin.setups.values()) {
			setup.closeAll();
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equals("test")) {
			InterfaceAPI.getSetup((Player) sender).getFileBrowser().openWindow();
		}
		
		return true;
	}
	
}