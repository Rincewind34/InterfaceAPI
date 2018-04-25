package de.rincewind.interfaceapi;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import de.rincewind.interfaceapi.item.categorys.Categorys;
import de.rincewind.interfaceapi.setup.Setup;
import de.rincewind.interfaceapi.util.recipes.RecipeManager;
import de.rincewind.interfaceapi.util.recipes.RecipePacket;
import de.rincewind.interfaceplugin.InterfacePlugin;
import de.rincewind.interfaceplugin.recipes.CraftRecipeManager;
import de.rincewind.interfaceplugin.recipes.CraftRecipePacket;
import de.rincewind.interfaceplugin.setup.CraftSetup;

public class InterfaceAPI {

	private static Map<Player, Setup> setups;

	public static void enable() {
		InterfaceAPI.setups = new HashMap<>();

		new Categorys();
	}
	
	public static void cleanUpSetup(Player owner) {
		InterfaceAPI.getSetup(owner).closeAll();
		InterfaceAPI.setups.remove(owner);
	}

	public static void resetSetups() {
		for (Setup setup : InterfaceAPI.setups.values()) {
			setup.closeAll();
		}

		InterfaceAPI.setups.clear();
	}

	public static void disable() {
		for (Setup setup : InterfaceAPI.setups.values()) {
			setup.closeAll();
		}

		InterfaceAPI.setups = null;
	}
	
	public static int getSetupAmount() {
		return InterfaceAPI.setups.size();
	}

	public static Setup getSetup(Player player) {
		if (player == null) {
			throw new IllegalArgumentException("Player cannot be null");
		}

		if (!InterfaceAPI.setups.containsKey(player)) {
			Setup setup = new CraftSetup(player, InterfacePlugin.instance);

			if (InterfacePlugin.instance != null) {
				InterfacePlugin.instance.getServer().getPluginManager().callEvent(new SetupCreateEvent(setup, player));
			}

			InterfaceAPI.setups.put(player, setup);
		}

		return InterfaceAPI.setups.get(player);
	}

	public static RecipeManager createRecipeManager() {
		return new CraftRecipeManager();
	}

	public static int getActiveWindowId(Player player) {
		return ((CraftPlayer) player).getHandle().activeContainer.windowId;
	}

	public static RecipePacket createRecipePacket() {
		return new CraftRecipePacket();
	}

}
