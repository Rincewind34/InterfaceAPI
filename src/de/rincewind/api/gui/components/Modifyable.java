package de.rincewind.api.gui.components;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.components.Locatable.Point;
import de.rincewind.api.gui.elements.abstracts.Element;
import de.rincewind.api.gui.elements.util.ElementCreator;
import de.rincewind.util.item.ItemLibary;

public abstract interface Modifyable {
	
	public static final ItemStack EMPTY_USED_SLOT = ItemLibary.refactor().renameItem(new ItemStack(Material.WOOL, 1, (byte) 15), "§0EMPTY_USED_ITEM");
	public static final ItemStack INVISIBLE_ELEMENT = ItemLibary.refactor().renameItem(new ItemStack(Material.WOOL, 1, (byte) 15), "§0INVISIBLE_ELEMENT");
	
	
	public abstract List<Element> getElements();
	
	public abstract Element getElementAt(Point point);
	
	public abstract ElementCreator elementCreator();
	
	public abstract void readItemsFromAll();
	
	public abstract void readItemsFrom(Element element);
	
	public abstract void clearItemsFrom(Element element);
	
	public abstract void addElement(Element element);
	
}
