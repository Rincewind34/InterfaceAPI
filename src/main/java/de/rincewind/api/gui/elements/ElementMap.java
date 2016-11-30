package de.rincewind.api.gui.elements;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import de.rincewind.api.gui.components.Displayable;
import de.rincewind.api.gui.components.DisplayableDisabled;
import de.rincewind.api.gui.components.Selectable;
import de.rincewind.api.gui.elements.abstracts.Element;
import de.rincewind.api.gui.elements.util.Point;
import de.rincewind.api.gui.util.Color;

public interface ElementMap extends Element, DisplayableDisabled, Selectable, Iterable<Displayable> {
	
	public abstract void setColor(Color color);
	
	public abstract void setPage(int page);
	
	public abstract void addItem(Displayable item);
	
	public abstract void removeItem(Displayable item);
	
	public abstract void removeItem(int index);
	
	public abstract void sortItems(Comparator<Displayable> comperator);
	
	public abstract void reverse();
	
	public abstract void nextPage();
	
	public abstract void previousPage();
	
	public abstract void select(int index);
	
	public abstract void select(int index, boolean fireEvent);
	
	public abstract void unselect(boolean fireEvent);
	
	public abstract boolean isFirstPage();
	
	public abstract boolean isLastPage();
	
	public abstract Color getColor();
	
	public abstract int getPage();
	
	public abstract int getCountPerPage();
	
	public abstract int getMaxPage();
	
	public abstract int getFirstIndex(int page);
	
	public abstract Point getPoint(int index);
	
	public abstract List<Displayable> getItems();
	
	public abstract <T extends Displayable> T getItem(Point point);
	
	public abstract <T extends Displayable> T getItem(Point point, int page);
	
	public abstract <T extends Displayable> T getItem(int index);
	
	public abstract <T extends Displayable> T getItem(int index, int page);
	
	@Override
	public default Iterator<Displayable> iterator() {
		return this.getItems().iterator();
	}
	
	public default <T extends Displayable> T getItem(Class<T> cls, Point point) {
		return cls.cast(this.getItem(point));
	}
	
	public default <T extends Displayable> T getItem(Class<T> cls, Point point, int page) {
		return cls.cast(this.getItem(point, page));
	}
	
	public default <T extends Displayable> T getItem(Class<T> cls, int index) {
		return cls.cast(this.getItem(index));
	}
	
	public default <T extends Displayable> T getItem(Class<T> cls, int index, int page) {
		return cls.cast(this.getItem(index, page));
	}
	
	public default <T extends Displayable> List<T> getItem(Class<T> cls) {
		return this.getItems().stream().map((entry) -> {
			return cls.cast(entry);
		}).collect(Collectors.toList());
	}
	
}
