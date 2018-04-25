package de.rincewind.interfaceplugin;

import org.bukkit.plugin.java.JavaPlugin;

import de.rincewind.interfaceapi.InterfaceAPI;
import de.rincewind.interfaceplugin.listener.InventoryClickListener;
import de.rincewind.interfaceplugin.listener.InventoryCloseListener;
import de.rincewind.interfaceplugin.listener.PlayerQuitListener;

public class InterfacePlugin extends JavaPlugin {
	
	public static InterfacePlugin instance;
	
	@Override
	public void onEnable() {
		InterfacePlugin.instance = this;
		
		InterfaceAPI.disable();
		
		this.getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
		this.getServer().getPluginManager().registerEvents(new InventoryCloseListener(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
	}
	
	@Override
	public void onDisable() {
		InterfaceAPI.disable();
	}
	
}