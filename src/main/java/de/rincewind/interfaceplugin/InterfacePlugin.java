package de.rincewind.interfaceplugin;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.rincewind.interfaceapi.item.categorys.Categorys;
import de.rincewind.interfaceapi.setup.Setup;
import de.rincewind.interfaceplugin.listener.InventoryClickListener;
import de.rincewind.interfaceplugin.listener.InventoryCloseListener;
import de.rincewind.interfaceplugin.listener.PlayerQuitListener;

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
	
}