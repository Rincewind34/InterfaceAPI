package de.rincewind.interfaceapi.gui.elements;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.components.DisplayableDisabled;
import de.rincewind.interfaceapi.gui.components.Selectable;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceapi.gui.util.Color;

public interface ElementMap extends Element, DisplayableDisabled, Selectable, Iterable<Displayable> {
	
	public abstract void setColor(Color color);
	
	public abstract void setPage(int page);
	
	public abstract void addItem(Displayable item);
	
	public abstract void removeItem(Displayable item);
	
	public abstract void removeItem(int index);
	
	public abstract void clear();
	
	public abstract void sortItems(Comparator<Displayable> comperator);
	
	public abstract void reverse();
	
	public abstract void shuffle();
	
	public abstract void nextPage();
	
	public abstract void previousPage();
	
	public abstract void select(int index);
	
	public abstract void select(int index, boolean fireEvent);
	
	public abstract void unselect(boolean fireEvent);
	
	public abstract boolean isFirstPage();
	
	public abstract boolean isLastPage();
	
	public abstract Color getColor();
	
	public abstract int getSelectedIndex();
	
	public abstract int getPage();
	
	public abstract int getCountPerPage();
	
	public abstract int getMaxPage();
	
	public abstract int getFirstIndex(int page);
	
	public abstract Point getPoint(int index);
	
	public abstract List<Displayable> getItems();
	
	public abstract <T> T get(Point point);
	
	public abstract <T> T get(Point point, int page);
	
	public abstract <T> T get(int index);
	
	public abstract <T> T get(int index, int page);
	
	public abstract <T extends Displayable> T getItem(Point point, int page);
	
	@Override
	public default Iterator<Displayable> iterator() {
		return this.getItems().iterator();
	}
	
	public default <T> T get(Class<T> cls, Point point) {
		return this.get(point);
	}
	
	public default <T> T get(Class<T> cls, Point point, int page) {
		return this.get(point, page);
	}
	
	public default <T> T get(Class<T> cls, int index) {
		return this.get(index);
	}
	
	public default <T> T get(Class<T> cls, int index, int page) {
		return this.get(index, page);
	}
	
	public default <T extends Displayable> T getItem(Class<T> cls, Point point, int page) {
		return this.getItem(point, page);
	}
	
	public default <T> List<T> getItem(Class<T> cls) {
		return this.getItems().stream().map((entry) -> {
			return cls.cast(entry);
		}).collect(Collectors.toList());
	}
	
}