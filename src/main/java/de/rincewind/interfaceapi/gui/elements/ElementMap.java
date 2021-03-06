package de.rincewind.interfaceapi.gui.elements;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.components.DisplayableDisabled;
import de.rincewind.interfaceapi.gui.components.Selectable;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.util.Color;
import de.rincewind.interfaceapi.gui.util.Point;
import de.rincewind.interfaceapi.gui.util.creators.ElementCreator;
import de.rincewind.interfaceapi.handling.InterfaceListener;
import de.rincewind.interfaceapi.handling.element.ElementInteractEvent;
import de.rincewind.interfaceapi.util.HeadsDatabase;

public interface ElementMap extends Element, DisplayableDisabled, Selectable, Iterable<Displayable> {

	public abstract void setColor(Color color);
	
	public abstract void setPage(int page);
	
	public abstract void addItem(Displayable item);
	
	public abstract void addItems(Iterable<? extends Displayable> items);
	
	public abstract void removeItem(Displayable item);
	
	public abstract void removeItem(int index);
	
	public abstract void clear();

	public abstract void sortItems();
	
	public abstract void sortItems(Comparator<Displayable> comperator);
	
	public abstract void reverse();
	
	public abstract void shuffle();
	
	public abstract void nextPage();
	
	public abstract void previousPage();
	
	public abstract void select(int index);
	
	public abstract void select(int index, boolean fireEvent);
	
	public abstract void deselect(boolean fireEvent);
	
	public abstract void setFilter(Predicate<Displayable> filter);
	
	public abstract void registerNextPageFliper(Element element);
	
	public abstract void registerPreviousPageFliper(Element element);
	
	public abstract void unregisterFliper(Element element);
	
	public abstract boolean isFirstPage();
	
	public abstract boolean isLastPage();
	
	public abstract boolean isFiltered();
	
	public abstract Color getColor();
	
	public abstract int getSelectedIndex();
	
	public abstract int getPage();
	
	public abstract int getCountPerPage();
	
	public abstract int getMaxPage();
	
	public abstract int getFirstIndex(int page);
	
	public abstract int size();
	
	public abstract int filteredSize();
	
	public abstract Point getPoint(int index);
	
	public abstract InterfaceListener<ElementInteractEvent> newFlipListener(int offset);
	
	public abstract Predicate<Displayable> getFilter();
	
	public abstract <T> T getSelected();
	
	public abstract <T> T get(Point point);
	
	public abstract <T> T get(int index);
	
	public abstract <T> T get(int index, int page);
	
	public abstract List<Displayable> getItems();
	
	@Override
	public default Iterator<Displayable> iterator() {
		return this.getItems().iterator();
	}
	
	public default ElementItem newNextFliper(ElementCreator creator, Point point) {
		ElementItem item = creator.newItem();
		item.setPoint(point);
		item.setIcon(HeadsDatabase.arrowWoodRight());
		item.setDisabledIcon(HeadsDatabase.arrowStoneRight());
		
		this.registerNextPageFliper(item);
		return item;
	}
	
	public default ElementItem newPreviousFliper(ElementCreator creator, Point point) {
		ElementItem item = creator.newItem();
		item.setPoint(point);
		item.setIcon(HeadsDatabase.arrowWoodLeft());
		item.setDisabledIcon(HeadsDatabase.arrowStoneLeft());
		
		this.registerPreviousPageFliper(item);
		return item;
	}
	
	public default <T> T getSelected(Class<T> cls) {
		return this.getSelected();
	}
	
	public default <T> T get(Class<T> cls, Point point) {
		return this.get(point);
	}
	
	public default <T> T get(Class<T> cls, int index) {
		return this.get(index);
	}
	
	public default <T> T get(Class<T> cls, int index, int page) {
		return this.get(index, page);
	}
	
	public default <T extends Displayable> List<T> getItems(Class<T> cls) {
		return this.getItems().stream().map(cls::cast).collect(Collectors.toList());
	}
	
}
