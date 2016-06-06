package de.rincewind.api.gui.elements;

import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.elements.abstracts.ElementDisplayable;
import de.rincewind.api.gui.windows.abstracts.Window;
import de.rincewind.api.item.ItemSelector;

public interface ElementSelector extends ElementDisplayable {
	
	public abstract void startSelecting();
	
	public abstract void stopSelecting();
	
	public abstract void pullOnlyOne(boolean value);
	
	public abstract void canUnselect(boolean value);
	
	public abstract void setSelected(ItemStack item);
	
	public abstract void setSelected(ItemStack item, boolean fireEvent);
	
	public abstract void registerTarget(Window window);
	
	public abstract void setSelector(ItemSelector selector);
	
	public abstract boolean isPullingOnlyOne();
	
	public abstract boolean canUnselect();
	
	public abstract boolean isSelecting();
	
	public abstract ItemStack getSelected();
	
}
