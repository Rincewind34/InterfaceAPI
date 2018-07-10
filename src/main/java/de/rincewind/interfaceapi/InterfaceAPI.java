package de.rincewind.interfaceapi;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import de.rincewind.interfaceapi.gui.windows.util.Toolbar;
import de.rincewind.interfaceapi.selectors.element.SelectorElementSetCreator;
import de.rincewind.interfaceapi.selectors.window.WindowSelectorCreator;
import de.rincewind.interfaceapi.setup.Setup;
import de.rincewind.interfaceplugin.InterfacePlugin;
import de.rincewind.interfaceplugin.Validate;
import de.rincewind.interfaceplugin.gui.util.CraftToolbar;
import de.rincewind.interfaceplugin.setup.CraftSetup;

public class InterfaceAPI {

	private static Map<Player, Setup> setups;
	private static Map<Class<?>, WindowSelectorCreator<?, ?>> windowSelectors;
	private static Map<Class<?>, SelectorElementSetCreator<?>> inlineSelectors;

	public static void enable() {
		InterfaceAPI.setups = new HashMap<>();
		InterfaceAPI.windowSelectors = new HashMap<>();
		InterfaceAPI.inlineSelectors = new HashMap<>();
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

	public static <T> void registerSelector(Class<T> typeClass, WindowSelectorCreator<T, ?> creator) {
		Validate.notNull(typeClass, "The type class cannot be null");
		Validate.notNull(creator, "The creator cannot be null");

		InterfaceAPI.windowSelectors.put(typeClass, creator);
	}

	public static <T> void registerSelector(Class<T> typeClass, SelectorElementSetCreator<T> creator) {
		Validate.notNull(typeClass, "The type class cannot be null");
		Validate.notNull(creator, "The creator cannot be null");

		InterfaceAPI.inlineSelectors.put(typeClass, creator);
	}

	public static boolean isWindowSelectorRegistered(Class<?> typeClass) {
		Validate.notNull(typeClass, "The type class cannot be null");

		return InterfaceAPI.windowSelectors.containsKey(typeClass);
	}

	public static boolean isInlineSelectorRegistered(Class<?> typeClass) {
		Validate.notNull(typeClass, "The type class cannot be null");

		return InterfaceAPI.inlineSelectors.containsKey(typeClass);
	}

	public static int getSetupAmount() {
		return InterfaceAPI.setups.size();
	}

	public static int getActiveWindowId(Player player) {
		return ((CraftPlayer) player).getHandle().activeContainer.windowId;
	}
	
	public static Toolbar createToolbar() {
		return new CraftToolbar();
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

	@SuppressWarnings("unchecked")
	public static <T> SelectorElementSetCreator<T> getElementCreator(Class<T> typeClass) {
		Validate.notNull(typeClass, "The type class cannot be null");

		return (SelectorElementSetCreator<T>) InterfaceAPI.inlineSelectors.get(typeClass);
	}

	@SuppressWarnings("unchecked")
	public static <T> WindowSelectorCreator<T, ?> getWindowCreator(Class<T> typeClass) {
		Validate.notNull(typeClass, "The type class cannot be null");

		return (WindowSelectorCreator<T, ?>) InterfaceAPI.windowSelectors.get(typeClass);
	}
	
}
