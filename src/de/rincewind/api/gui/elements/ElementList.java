package de.rincewind.api.gui.elements;

import java.util.List;

import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.Directionality;
import de.rincewind.api.gui.components.Colorable;
import de.rincewind.api.gui.components.EventBased;
import de.rincewind.api.gui.elements.abstracts.ElementSizeable;

public abstract interface ElementList extends ElementSizeable, Colorable, EventBased {

	public abstract void addItem(ItemStack item);
	
	public abstract void removeItem(ItemStack item);
	
	public abstract void setType(Directionality type);
	
	public abstract void setStartIndex(int index);
	
	public abstract void select(int index);
	
	public abstract void unselect();
	
	public abstract void setSelectModifyer(SelectModifyer modifyer);
	
	public abstract void addScroler(ElementButton btn, int value);
	
	public abstract int getStartIndex();
	
	public abstract int getSelected();
	
	public abstract ItemStack modifyToSelect(ItemStack item);
	
	public abstract List<ItemStack> getItems();
	
	
	public static interface SelectModifyer {
		
		public abstract ItemStack modify(ItemStack item);
		
	}
	
}
