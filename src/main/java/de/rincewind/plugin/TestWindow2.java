package de.rincewind.plugin;

import org.bukkit.plugin.Plugin;

import de.rincewind.api.gui.windows.util.Windows.WindowBrewingExtendable;

public class TestWindow2 extends WindowBrewingExtendable {

	public TestWindow2(Plugin plugin) {
		super(plugin);
		
		this.start(10L);
	}

}
