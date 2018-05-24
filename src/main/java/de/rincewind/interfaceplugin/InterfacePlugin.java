package de.rincewind.interfaceplugin;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import de.rincewind.interfaceapi.InterfaceAPI;
import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.util.InterfaceUtils;
import de.rincewind.interfaceplugin.listener.InventoryClickListener;
import de.rincewind.interfaceplugin.listener.InventoryCloseListener;
import de.rincewind.interfaceplugin.listener.PlayerQuitListener;

public class InterfacePlugin extends JavaPlugin {
	
	public static InterfacePlugin instance;
	
	static {
		Displayable.put(GameMode.class, InterfaceUtils::convertGameMode);
		Displayable.put(Environment.class, InterfaceUtils::convertEnvironment);
		Displayable.put(World.class, InterfaceUtils::convertWorld);
		Displayable.put(EntityType.class, InterfaceUtils::convertEntityType);
		Displayable.put(Boolean.class, (input) -> {
			return input ? new Icon(Material.CONCRETE, 13, "§aTrue") : new Icon(Material.CONCRETE, 14, "§cFalse");
		});
		
		Displayable.copy(Boolean.class, boolean.class);
	}
	
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