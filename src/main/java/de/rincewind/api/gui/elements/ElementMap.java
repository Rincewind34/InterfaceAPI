package de.rincewind.api.gui.elements;

import java.util.Comparator;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.elements.abstracts.ElementSizeable;
import de.rincewind.api.gui.elements.util.Icon;
import de.rincewind.api.gui.elements.util.Point;
import de.rincewind.api.gui.util.Color;

public interface ElementMap<T> extends ElementSizeable {
	
	public abstract void setColor(Color color);
	
	public abstract void setPage(int page);
	
	public abstract void addItem(MapItem<T> item);
	
	public abstract void sortItems(Comparator<T> comperator);
	
	public abstract void reverse();
	
	public abstract void nextPage();
	
	public abstract void previousPage();
	
	public abstract boolean isFirstPage();
	
	public abstract boolean isLastPage();
	
	public abstract Color getColor();
	
	public abstract int getPage();
	
	public abstract int getCountPerPage();
	
	public abstract int getMaxPage();
	
	public abstract int getFirstIndex(int page);
	
	public abstract MapItem<T> getItem(Point point);
	
	public abstract MapItem<T> getItem(Point point, int page);
	
	public abstract MapItem<T> getItem(int index);
	
	public abstract MapItem<T> getItem(int index, int page);
	
	public abstract List<MapItem<T>> getItems();
	
	
	public static class MapItem<K> {
		
		private ItemStack item;
		private K save;
		
		public MapItem(Icon icon, K save) {
			this.item = icon.toItem();
			this.save = save;
		}
		
		public ItemStack getItem() {
			return this.item;
		}
		
		public K getSave() {
			return this.save;
		}

	}
	
}
