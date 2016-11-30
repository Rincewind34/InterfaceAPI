package de.rincewind.api.gui.elements;

import java.util.function.Predicate;

import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.elements.abstracts.ElementDisplayable;
import de.rincewind.api.gui.windows.abstracts.Window;

public interface ElementSelector extends ElementDisplayable {
	
	public abstract void pullOnlyOne(boolean value);
	
	public abstract void canUnselect(boolean value);
	
	public abstract void setSelected(ItemStack item);
	
	public abstract void setSelected(ItemStack item, boolean fireEvent);
	
	public abstract void registerTarget(Window window);
	
	public abstract void setSelector(Predicate<ItemStack> selector);
	
	public abstract boolean isPullingOnlyOne();
	
	public abstract boolean canUnselect();
	
	public abstract ItemStack getSelected();
	
}
