package de.rincewind.plugin.gui.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import de.rincewind.api.exceptions.APIException;
import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.ElementMap;
import de.rincewind.api.gui.elements.util.Point;
import de.rincewind.api.gui.util.Color;
import de.rincewind.api.handling.events.MapClickEvent;
import de.rincewind.plugin.gui.elements.abstracts.CraftElementSizeable;

public class CraftElementMap<T> extends CraftElementSizeable implements ElementMap<T> {
	
	private Color color;
	
	private int page;
	
	private List<MapItem<T>> items;
	
	public CraftElementMap(Modifyable handle) {
		super(handle);
		
		this.items = new ArrayList<>();
		this.color = Color.TRANSLUCENT;
		this.page = 1;
	}
	
	@Override
	public void handleClick(InventoryClickEvent event) {
		if (event.getCurrentItem().equals(this.color.asItem())) {
			return;
		}
		
		InventoryType invType = event.getInventory().getType();
		
		int slot = event.getRawSlot();
		int invWidth = invType == InventoryType.DISPENSER ? 3 : (invType == InventoryType.HOPPER ? 5 : 9);
		
		int y = (int) ((double) slot / (double) invWidth);
		int x = slot - (y * invWidth);
		
		Point point = new Point(x - this.getPoint().getX(), y - this.getPoint().getY());
		MapItem<?> item = this.getItem(point);
		
		if (item != null) {
			this.getEventManager().callEvent(new MapClickEvent(this, point, this.getItem(point)));
		}
	}
	
	@Override
	public void setColor(Color color) {
		this.color = color;
		this.getHandle().readItemsFrom(this);
	}

	@Override
	public void setPage(int page) {
		if (this.getMaxPage() < page || page <= 0) {
			throw new APIException("The page is invalid");
		}
		
		this.page = page;
		this.getHandle().readItemsFrom(this);
	}

	@Override
	public void addItem(MapItem<T> item) {
		this.items.add(item);
		this.getHandle().readItemsFrom(this);
	}
	
	@Override
	public void removeItem(int index) {
		this.items.remove(index);
		this.getHandle().readItemsFrom(this);
	}
	
	@Override
	public void removeItem(MapItem<T> item) {
		this.items.remove(item);
		this.getHandle().readItemsFrom(this);
	}
	
	@Override
	public void sortItems(Comparator<T> comperator) {
		this.items.sort((value1, value2) -> {
			return comperator.compare(value1.getSave(), value2.getSave());
		});
		
		this.getHandle().readItemsFrom(this);
	}
	
	@Override
	public void reverse() {
		Collections.reverse(this.items);
		
		this.getHandle().readItemsFrom(this);
	}
	
	@Override
	public void nextPage() {
		this.setPage(this.page + 1);
	}
	
	@Override
	public void previousPage() {
		this.setPage(this.page - 1);
	}
	
	@Override
	public boolean isFirstPage() {
		return this.page == 1;
	}
	
	@Override
	public boolean isLastPage() {
		return this.page == this.getMaxPage();
	}
	
	@Override
	public Color getColor() {
		return this.color;
	}
	
	@Override
	public int getPage() {
		return this.page;
	}
	
	@Override
	public int getCountPerPage() {
		return this.getWidth() * this.getHeight();
	}
	
	@Override
	public int getMaxPage() {
		int page = 1;
		
		while (page * this.getCountPerPage() < this.items.size()) {
			page = page + 1;
		}
		
		return page;
	}
	
	@Override
	public int getFirstIndex(int page) {
		return (this.page - 1) * this.getCountPerPage();
	}
	
	@Override
	public MapItem<T> getItem(Point point) {
		return this.getItem(point, this.page);
	}
	
	@Override
	public MapItem<T> getItem(Point point, int page) {
		int index = point.getY() * this.getWidth() + point.getX();
		return this.getItem(index, this.page);
	}
	
	@Override
	public MapItem<T> getItem(int index) {
		if (index >= this.items.size()) {
			return null;
		}
		
		return this.items.get(index);
	}
	
	@Override
	public MapItem<T> getItem(int index, int page) {
		return this.getItem(index + this.getFirstIndex(page));
	}
	
	@Override
	public List<MapItem<T>> getItems() {
		return Collections.unmodifiableList(this.items);
	}
	
	@Override
	public void updateItemMap() {
		int index = this.getFirstIndex(this.page);
		
		int x = 0;
		int y = 0;
		
		while (index < this.items.size()) {
			this.setItemAt(new Point(x, y), this.items.get(index).getItem());
			
			index = index + 1;
			x = x + 1;
			
			if (x == this.getWidth()) {
				x = 0;
				y = y + 1;
			}
			
			if (y == this.getHeight()) {
				break;
			}
		}
		
		for (; y < this.getHeight(); y++) {
			for (; x < this.getWidth(); x++) {
				this.setItemAt(new Point(x, y), this.color.asItem());
			}
			
			x = 0;
		}
	}
	
}
