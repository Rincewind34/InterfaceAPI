package de.rincewind.interfaceplugin.gui.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Predicate;

import com.google.common.base.Predicates;

import de.rincewind.interfaceapi.exceptions.EventPipelineException;
import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.components.DisplayableDisabled;
import de.rincewind.interfaceapi.gui.elements.ElementMap;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.util.Color;
import de.rincewind.interfaceapi.gui.util.Point;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowEditor;
import de.rincewind.interfaceapi.handling.InterfaceListener;
import de.rincewind.interfaceapi.handling.element.ElementInteractEvent;
import de.rincewind.interfaceapi.handling.element.ElementValueChangeEvent;
import de.rincewind.interfaceapi.handling.element.MapChangeSelectEvent;
import de.rincewind.interfaceplugin.Validate;
import de.rincewind.interfaceplugin.gui.elements.abstracts.CraftElement;

public class CraftElementMap extends CraftElement implements ElementMap {

	private static final Predicate<Displayable> defaultFilter = Predicates.alwaysTrue();

	private Color color;

	private int page;
	private int selected;

	private Displayable disabledIcon;

	private Predicate<Displayable> filter;
	private List<Displayable> items;

	private Set<Element> nextFliper;
	private Set<Element> previousFliper;

	public CraftElementMap(WindowEditor handle) {
		super(handle);

		this.page = 1;
		this.selected = -1;
		this.color = Color.TRANSLUCENT;
		this.filter = CraftElementMap.defaultFilter;
		this.disabledIcon = DisplayableDisabled.default_icon;
		this.nextFliper = new HashSet<>();
		this.previousFliper = new HashSet<>();
		this.items = new ArrayList<>();

		this.getComponent(Element.ENABLED).setEnabled(true);
		this.getComponent(Element.WIDTH).setEnabled(true);
		this.getComponent(Element.HEIGHT).setEnabled(true);

		this.getEventManager().registerListener(ElementInteractEvent.class, (event) -> {
			if (event.isLeftClick() && !event.isShiftClick()) {
				int index = this.convertFiltered(event.getPoint());

				if (index != -1 && this.selected != index) {
					this.select(index);
				}
			}
		}).addAfter();
	}

	@Override
	public void setDisabledIcon(Displayable icon) {
		this.disabledIcon = Displayable.checkNull(icon);

		if (!this.isEnabled()) {
			this.update();
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
			throw new NoSuchElementException("The page " + page + " is invalid");
		}

		this.page = page;
		this.update();
		this.updateFliper();
	}

	@Override
	public void addItem(Displayable item) {
		Validate.notNull(item, "The item cannot be null");

		this.items.add(item);
		this.update();
		this.updateFliper();
	}

	@Override
	public void addItems(Iterable<? extends Displayable> items) {
		Validate.notNull(items, "The iterable cannot be null");

		for (Displayable item : items) {
			this.addItem(item);
		}
	}

	@Override
	public void removeItem(int index) {
		this.items.remove(index);
		this.validateCurrentPage();
	}

	@Override
	public void removeItem(Displayable item) {
		this.items.remove(item);
		this.validateCurrentPage();
	}

	@Override
	public void clear() {
		this.selected = -1;
		this.items.clear();
		this.update();
	}

	@Override
	public void sortItems() {
		this.items.sort((value1, value2) -> {
			Comparable<?> compare1 = Displayable.readPayload(value1);
			return compare1.compareTo(Displayable.readPayload(value2));
		});
		this.updateEnabled();
	}

	@Override
	public void sortItems(Comparator<Displayable> comperator) {
		this.items.sort(comperator);
		this.updateEnabled();
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
		if (this.selected == index) {
			throw new RuntimeException("This element is selected!");
		}

		if (index != -1 && (index < 0 || index >= this.items.size())) {
			throw new RuntimeException("The index is invalid");
		}

		this.selected = index;

		if (fireEvent) {
			this.getEventManager().callEvent(MapChangeSelectEvent.class, new MapChangeSelectEvent(this, index));
		}

		this.update();
	}

	@Override
	public void deselect() {
		this.deselect(true);
	}

	@Override
	public void deselect(boolean fireEvent) {
		this.select(-1, fireEvent);
	}

	@Override
	public void setFilter(Predicate<Displayable> filter) {
		this.filter = filter == null ? CraftElementMap.defaultFilter : filter;
		this.validateCurrentPage();
		this.updateFliper();
	}

	@Override
	public void registerNextPageFliper(Element element) {
		Validate.notNull(element, "Element cannot be null");

		if (!element.isElementComponentEnabled(Element.ENABLED)) {
			throw new IllegalArgumentException("The element cannot be disbaled");
		}

		if (this.nextFliper.contains(element)) {
			return;
		}

		this.nextFliper.add(element);
		element.setComponentValue(Element.ENABLED, this.isEnabled() && !this.isLastPage());
		element.getEventManager().registerListener(ElementInteractEvent.class, this.new FlipListener(1)).addAfter();
	}

	@Override
	public void registerPreviousPageFliper(Element element) {
		Validate.notNull(element, "Element cannot be null");

		if (!element.isElementComponentEnabled(Element.ENABLED)) {
			throw new IllegalArgumentException("The element cannot be disbaled");
		}

		if (this.previousFliper.contains(element)) {
			return;
		}

		this.previousFliper.add(element);
		element.setComponentValue(Element.ENABLED, this.isEnabled() && !this.isFirstPage());
		element.getEventManager().registerListener(ElementInteractEvent.class, this.new FlipListener(-1)).addAfter();
	}

	@Override
	public void unregisterFliper(Element element) {
		Validate.notNull(element, "Element cannot be null");

		this.nextFliper.remove(element);
		this.previousFliper.remove(element);
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
	public boolean isFiltered() {
		return this.filter != CraftElementMap.defaultFilter;
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
	public int getSelectedIndex() {
		return this.selected;
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
		int count = this.getCountPerPage();
		int size = this.filteredSize();

		if (size != 0) {
			return size / count + (size % count == 0 ? 0 : 1);
		} else {
			return 1;
		}
	}

	@Override
	public int getFirstIndex(int page) {
		if (this.getMaxPage() < page || page <= 0) {
			throw new NoSuchElementException("The page " + page + " is invalid");
		}

		return (page - 1) * this.getCountPerPage();
	}

	@Override
	public int size() {
		return this.items.size();
	}

	@Override
	public int filteredSize() {
		return (int) this.items.stream().filter(this.filter).count();
	}

	@Override
	public Point getPoint(int index) {
		return Point.calculate(index, this.getWidth(), this.getHeight());
	}

	@Override
	public Icon getDisabledIcon() {
		return this.disabledIcon.getIcon();
	}

	@Override
	public Predicate<Displayable> getFilter() {
		return this.filter;
	}

	@Override
	public InterfaceListener<ElementInteractEvent> newFlipListener(int offset) {
		return this.new FlipListener(offset);
	}

	@Override
	public <T> T getSelected() {
		if (this.selected == -1) {
			return null;
		} else {
			return Displayable.readPayload(this.items.get(this.selected));
		}
	}

	@Override
	public <T> T get(Point point) {
		return this.get(this.convertFiltered(point));
	}

	@Override
	public <T> T get(int index) {
		return Displayable.readPayload(this.items.get(index));
	}

	@Override
	public <T> T get(int index, int page) {
		return this.get(index + this.getFirstIndex(page));
	}

	@Override
	public List<Displayable> getItems() {
		return Collections.unmodifiableList(this.items);
	}

	@Override
	protected Icon getIcon0(Point point) {
		if (this.isEnabled()) {
			int index = this.convertFiltered(point);

			if (index != -1) {
				return this.items.get(index).getIcon();
			} else {
				return this.color.asIcon();
			}
		} else {
			return this.getDisabledIcon();
		}
	}

	@Override
	public void dependsOn(Element element) {
		super.dependsOn(element);

		element.getEventManager().registerListener(ElementValueChangeEvent.class, (event) -> {
			this.updateFliper();
		}).monitor();
	}

	@Override
	protected void onEnabledChange() {
		this.updateFliper();
	}

	private void updateFliper() {
		Iterator<Element> iterator = this.nextFliper.iterator();

		while (iterator.hasNext()) {
			iterator.next().setComponentValue(Element.ENABLED, this.isEnabled() && !this.isLastPage());
		}

		iterator = this.previousFliper.iterator();

		while (iterator.hasNext()) {
			iterator.next().setComponentValue(Element.ENABLED, this.isEnabled() && !this.isFirstPage());
		}
	}

	private void validateCurrentPage() {
		int maxPage = this.getMaxPage();

		if (maxPage < this.page) {
			this.setPage(maxPage);
		} else {
			this.update();
		}
	}

	private int convertFiltered(Point point) {
		int index = this.getBounds().indexOf(point) + this.getCountPerPage() * (this.page - 1);
		int resultIndex = 0;
		
		while (resultIndex != this.size()) {
			if (this.filter.test(this.items.get(resultIndex))) {
				index = index - 1;
				
				if (index == -1) {
					break;
				}
			}

			resultIndex = resultIndex + 1;
		}

		if (index != -1 || resultIndex == this.size()) {
			return -1;
		}

		return resultIndex;
	}

	private class FlipListener implements InterfaceListener<ElementInteractEvent> {

		private int pageOffset;

		public FlipListener(int pageOffset) {
			this.pageOffset = pageOffset;
		}

		@Override
		public void onAction(ElementInteractEvent event) throws EventPipelineException {
			int newPage = CraftElementMap.this.getPage() + (this.pageOffset * (event.isShiftClick() ? 2 : 1));

			if (newPage < 0) {
				newPage = 0;
			} else if (newPage > CraftElementMap.this.getMaxPage()) {
				newPage = CraftElementMap.this.getMaxPage();
			}

			CraftElementMap.this.setPage(newPage);
		}

	}

}
