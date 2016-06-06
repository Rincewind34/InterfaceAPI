package de.rincewind.api.gui.windows.util;

import java.util.List;
import java.util.function.Consumer;

import org.bukkit.plugin.Plugin;

import de.rincewind.api.gui.elements.util.Point;
import de.rincewind.plugin.gui.windows.CraftWindowBrewing;
import de.rincewind.plugin.gui.windows.CraftWindowEnchanter;
import de.rincewind.plugin.gui.windows.CraftWindowFurnace;
import de.rincewind.plugin.gui.windows.CraftWindowPointer;
import de.rincewind.plugin.gui.windows.CraftWindowSizeable;
import de.rincewind.plugin.gui.windows.abstracts.CraftWindow;
import de.rincewind.plugin.gui.windows.abstracts.CraftWindowActivatable;
import de.rincewind.plugin.gui.windows.abstracts.CraftWindowColorable;
import de.rincewind.plugin.gui.windows.abstracts.CraftWindowContainer;
import de.rincewind.plugin.gui.windows.abstracts.CraftWindowEditor;
import de.rincewind.plugin.gui.windows.abstracts.CraftWindowNameable;

public abstract class Windows {
	
	public static abstract class WindowExtendable extends CraftWindow {
		
	}
	
	public static abstract class WindowActivatableExtendable extends CraftWindowActivatable {

		public WindowActivatableExtendable(Plugin plugin) {
			super(plugin);
		}
		
	}

	public static abstract class WindowColorableExtendable extends CraftWindowColorable {
		
	}
	
	public static abstract class WindowContainerExtendable extends CraftWindowContainer {
		
	}
	
	public static abstract class WindowEditorExtendable extends CraftWindowEditor {
		
	}
	
	public static abstract class WindowNameableExtendable extends CraftWindowNameable {
		
	}
	
	public static abstract class WindowBrewingExtendable extends CraftWindowBrewing {

		public WindowBrewingExtendable(Plugin plugin) {
			super(plugin);
		}
		
	}
	
	public static abstract class WindowEnchatnerExtendable extends CraftWindowEnchanter {
		
	}
	
	public static abstract class WindowFurnaceExtendable extends CraftWindowFurnace {

		public WindowFurnaceExtendable(Plugin plugin) {
			super(plugin);
		}
		
	}
	
	public static abstract class WindowPointerExtendable extends CraftWindowPointer {

		public WindowPointerExtendable(Consumer<Point> action, int width, int height, String name, List<Point> blocked) {
			super(action, width, height, name, blocked);
		}
		
	}
	
	public static abstract class WindowSizeableExtendable extends CraftWindowSizeable {
		
	}
	
}
