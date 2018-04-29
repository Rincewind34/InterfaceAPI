package de.rincewind.interfaceplugin.gui.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.rincewind.interfaceapi.exceptions.APIException;
import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.components.Modifyable;
import de.rincewind.interfaceapi.gui.elements.ElementMap;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceapi.gui.util.Color;
import de.rincewind.interfaceapi.handling.element.ElementInteractEvent;
import de.rincewind.interfaceapi.handling.element.MapChangeSelectEvent;
import de.rincewind.interfaceplugin.gui.elements.abstracts.CraftElement;

public class CraftElementMap extends CraftElement implements ElementMap {
	
	private Color color;
	
	private int page;
	private int selected;
	
	private Icon disabledIcon;
	
	private List<Displayable> items;
	
	public CraftElementMap(Modifyable handle) {
		super(handle);
		
		this.page = 1;
		this.selected = -1;
		this.color = Color.TRANSLUCENT;
		this.disabledIcon = Icon.AIR;
		this.items = new ArrayList<>();
		
		this.getComponent(Element.ENABLED).setEnabled(true);
		this.getComponent(Element.WIDTH).setEnabled(true);
		this.getComponent(Element.HEIGHT).setEnabled(true);
		
		this.getEventManager().registerListener(ElementInteractEvent.class, (event) -> {
			this.select(this.convert(event.getPoint()));
		}).addAfter();
	}
	
	@Override
	public void setDisabledIcon(Icon icon) {
		if (icon != null) {
			this.disabledIcon = icon;
		} else {
			this.disabledIcon = Icon.AIR;
		}
	}
	
	@Override
	public void setColor(Color color) {
		this.color = color;
		this.update();
	}

	@Override
	public void setPage(int page) {
		if (this.getMaxPage() < page || page <= 0) {
			throw new APIException("The page is invalid");
		}
		
		this.page = page;
		this.update();
	}

	@Override
	public void addItem(Displayable item) {
		this.items.add(item);
		this.update();
	}
	
	@Override
	public void removeItem(int index) {
		this.items.remove(index);
		this.update();
	}
	
	@Override
	public void removeItem(Displayable item) {
		this.items.remove(item);
		this.update();
	}
	
	@Override
	public void clear() {
		this.items.clear();
		this.update();
	}
	
	@Override
	public void sortItems(Comparator<Displayable> comperator) {
		this.items.sort((value1, value2) -> {
			return comperator.compare(value1, value2);
		});
		
		this.update();
	}
	
	@Override
	public void reverse() {
		Collections.reverse(this.items);
		this.update();
	}
	
	@Override
	public void shuffle() {
		Collections.shuffle(this.items);
		this.update();
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
	public void select() {
		this.select(0);
	}
	
	@Override
	public void select(int index) {
		this.select(index, true);
	}
	
	@Override
	public void select(int index, boolean fireEvent) {
		if (this.isSelected()) {
			throw new RuntimeException("This element is selected!");
		}
		
		if (this.items.isEmpty()) {
			throw new RuntimeException("The items are empty!");
		}
		
		if (fireEvent) {
			this.getEventManager().callEvent(MapChangeSelectEvent.class, new MapChangeSelectEvent(this, index));
		}
		
		this.selected = index;
		this.update();
	}
	
	@Override
	public void unselect() {
		this.unselect(true);
	}
	
	@Override
	public void unselect(boolean fireEvent) {
		this.select(-1, fireEvent);
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
	public boolean isSelected() {
		return this.selected != -1;
	}
	
	@Override
	public boolean canSelect() {
		return this.items.size() > 0;
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
		return (page - 1) * this.getCountPerPage();
	}
	
	@Override
	public Point getPoint(int index) {
		int solved = index;
		
		while (solved % this.getWidth() != 0) {
			solved = solved - 1;
		}
		
		return new Point(index - solved, solved / this.getWidth());
	}
	
	@Override
	public Icon getDisabledIcon() {
		return this.disabledIcon;
	}
	
	@Override
	public <T extends Displayable> T getItem(Point point) {
		return this.getItem(point, this.page);
	}
	
	@Override
	public <T extends Displayable> T getItem(Point point, int page) {
		int index = point.getY() * this.getWidth() + point.getX();
		return this.getItem(index, this.page);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends Displayable> T getItem(int index) {
		return (T) this.items.get(index);
	}
	
	@Override
	public <T extends Displayable> T getItem(int index, int page) {
		return this.getItem(index + this.getFirstIndex(page));
	}
	
	@Override
	public List<Displayable> getItems() {
		return Collections.unmodifiableList(this.items);
	}
	
	@Override
	public Icon getIcon0(Point point) {
		if (this.isEnabled()) {
			int index = this.convert(point);
			
			if (index < this.items.size()) {
				return this.items.get(index).getIcon();
			} else {
				return this.color.asIcon();
			}
		} else {
			return this.disabledIcon;
		}
	}
	
	private int convert(Point point) {
		return this.getHeight() * point.getY() + point.getX();
	}
	
}
