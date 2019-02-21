package de.rincewind.interfaceapi.gui.components;

import org.bukkit.entity.Player;

public interface ActionItem {
	
	public abstract void performCustomAction(Player player, Runnable executed);
	
	public abstract String getCustomActionInstructions();
	
}
