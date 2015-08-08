package de.rincewind.api.gui.elements;

import org.bukkit.inventory.ItemStack;

public interface ElementSwitcher extends ElementButton {
	
	public void next();
	
	public void back();
	
	public void setSwitchId(int id);
	
	public void addSwitch(ItemStack item);
	
	public void removeSwitch(ItemStack item);
	
	public void clear();
	
	public int size();
	
	public int getSwitchid();
	
}
