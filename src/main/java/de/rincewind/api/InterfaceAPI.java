package de.rincewind.api;

import lib.securebit.ReflectionUtil;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.rincewind.api.setup.Setup;
import de.rincewind.api.util.recipes.RecipeManager;
import de.rincewind.api.util.recipes.RecipePacket;
import de.rincewind.plugin.APIReflection;
import de.rincewind.plugin.InterfacePlugin;
import de.rincewind.plugin.recipes.CraftRecipeManager;
import de.rincewind.plugin.recipes.CraftRecipePacket;
import de.rincewind.plugin.setup.CraftSetup;

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
