package de.rincewind.interfaceapi;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.rincewind.interfaceapi.exceptions.SelectorInterfaceException;
import de.rincewind.interfaceapi.gui.elements.sets.ElementSelectorSetCreator;
import de.rincewind.interfaceapi.gui.windows.selectors.WindowSelector;
import de.rincewind.interfaceapi.setup.Setup;
import de.rincewind.interfaceplugin.InterfacePlugin;
import de.rincewind.interfaceplugin.Validate;
import de.rincewind.interfaceplugin.setup.CraftSetup;

public class InterfaceAPI {

	private static Map<Player, Setup> setups;
	private static Map<Class<?>, Constructor<? extends WindowSelector<?>>> windowSelectors;
	private static Map<Class<?>, ElementSelectorSetCreator<?>> inlineSelectors;

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

	public static <T> void registerSelector(Class<T> typeClass, Class<? extends WindowSelector<T>> selectorClass) {
		Validate.notNull(typeClass, "The type class cannot be null");
		Validate.notNull(selectorClass, "The window class cannot be null");

		if (selectorClass.isInterface() || Modifier.isAbstract(selectorClass.getModifiers())) {
			throw new IllegalArgumentException("The selector class " + selectorClass + " is abstract");
		}

		for (Constructor<?> constructor : selectorClass.getDeclaredConstructors()) {
			if (constructor.getParameterTypes().length == 3 && constructor.getParameterTypes()[0] == Plugin.class
					&& constructor.getParameterTypes()[1] == Consumer.class && constructor.getParameterTypes()[2] == Iterable.class) {

				try {
					Constructor<? extends WindowSelector<T>> target = selectorClass.getConstructor(constructor.getParameterTypes());
					target.setAccessible(true);

					InterfaceAPI.windowSelectors.put(typeClass, target);
					return;
				} catch (NoSuchMethodException | SecurityException | IllegalArgumentException exception) {
					assert false : "Should be unreachable: " + exception.getMessage();
					return;
				}
			}
		}

		throw new IllegalArgumentException("The selector class " + selectorClass + " does not provide a valid constructor");
	}
	
	public static <T> void registerSelector(Class<T> typeClass, ElementSelectorSetCreator<T> creator) {
		Validate.notNull(typeClass, "The type class cannot be null");
		Validate.notNull(creator, "The creator cannot be null");
		
		InterfaceAPI.inlineSelectors.put(typeClass, creator);
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

	public static int getActiveWindowId(Player player) {
		return ((CraftPlayer) player).getHandle().activeContainer.windowId;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> ElementSelectorSetCreator<T> getCreator(Class<T> typeClass) {
		Validate.notNull(typeClass, "The type class cannot be null");
		
		return (ElementSelectorSetCreator<T>) InterfaceAPI.inlineSelectors.get(typeClass);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> WindowSelector<T> newSelector(Class<T> typeClass, Plugin plugin, Iterable<T> elements, Consumer<T> action) {
		Validate.notNull(typeClass, "The type class cannot be null");
		Validate.notNull(plugin, "The plugin cannot be null");
		Validate.notNull(elements, "The element interable cannot be null");
		Validate.notNull(action, "The action cannot be null");

		try {
			Constructor<? extends WindowSelector<T>> constructor = (Constructor<? extends WindowSelector<T>>) InterfaceAPI.windowSelectors.get(typeClass);

			if (constructor == null) {
				return null;
			}

			return constructor.newInstance(plugin, elements, action);
		} catch (ClassCastException exception) {
			assert false : "Failed with ClassCastException that should have been caught in register method";
			throw exception;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException exception) {
			assert false : "Failed with reflective exception that should have been caught in register method";
			throw new SelectorInterfaceException(exception);
		} catch (InvocationTargetException exception) {
			throw new SelectorInterfaceException(exception);
		}
	}

}
