package de.rincewind.api.gui.windows.util;

import java.util.List;
import java.util.function.Consumer;

import org.bukkit.plugin.Plugin;

import de.rincewind.api.gui.elements.util.Point;
import de.rincewind.api.gui.windows.WindowBrewing;
import de.rincewind.api.gui.windows.WindowEnchanter;
import de.rincewind.api.gui.windows.WindowFurnace;
import de.rincewind.api.gui.windows.WindowPointer;
import de.rincewind.api.gui.windows.WindowSizeable;
import de.rincewind.plugin.gui.windows.CraftWindowBrewing;
import de.rincewind.plugin.gui.windows.CraftWindowEnchanter;
import de.rincewind.plugin.gui.windows.CraftWindowFurnace;
import de.rincewind.plugin.gui.windows.CraftWindowPointer;
import de.rincewind.plugin.gui.windows.CraftWindowSizeable;

public class WindowCreator {
	
	public WindowSizeable newSizeableWindow() {
		return new CraftWindowSizeable();
	}
	
	public WindowBrewing newBrewer(Plugin plugin) {
		return new CraftWindowBrewing(plugin);
	}
	
	public WindowFurnace newFurnace(Plugin plugin) {
		return new CraftWindowFurnace(plugin);
	}
	
	public WindowEnchanter newEnchanter() {
		return new CraftWindowEnchanter();
	}
	
	public WindowPointer newPointer(Consumer<Point> action, int width, int height, String name, List<Point> blocked) {
		return new CraftWindowPointer(action, width, height, name, blocked);
	}
	
}

