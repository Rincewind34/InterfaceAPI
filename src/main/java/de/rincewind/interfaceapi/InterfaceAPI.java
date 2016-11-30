package de.rincewind.interfaceapi;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.rincewind.interfaceapi.setup.Setup;
import de.rincewind.interfaceapi.util.recipes.RecipeManager;
import de.rincewind.interfaceapi.util.recipes.RecipePacket;
import de.rincewind.interfaceplugin.APIReflection;
import de.rincewind.interfaceplugin.InterfacePlugin;
import de.rincewind.interfaceplugin.ReflectionUtil;
import de.rincewind.interfaceplugin.recipes.CraftRecipeManager;
import de.rincewind.interfaceplugin.recipes.CraftRecipePacket;
import de.rincewind.interfaceplugin.setup.CraftSetup;

public class InterfaceAPI {
	
	public static Setup getSetup(Player player) {
		if (!InterfacePlugin.setups.containsKey(player)) {
			Setup setup = new CraftSetup(player, InterfacePlugin.instance);
			Bukkit.getPluginManager().callEvent(new SetupCreateEvent(setup, player));
			InterfacePlugin.setups.put(player, setup);
		}
		
		return InterfacePlugin.setups.get(player);
	}
	
	public static RecipeManager createRecipeManager() {
		return new CraftRecipeManager();
	}
	
	public static int getActiveWindowId(Player player) {
		Object nmsPlayer = ReflectionUtil.createObject(APIReflection.METHOD_GETHANDLE, player, ReflectionUtil.emtyObjectArray());
		Object container = ReflectionUtil.createObject(APIReflection.FIELD_ACTIVEWINDOW, nmsPlayer);
		
		return (int) ReflectionUtil.createObject(APIReflection.FIELD_WINDOWID, container);
	}
	
	public static RecipePacket createRecipePacket() {
		return new CraftRecipePacket();
	}
	
}
