package de.rincewind.interfaceplugin.gui.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

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
import de.rincewind.interfaceapi.handling.element.MapChangeSelectEvent;
import de.rincewind.interfaceplugin.Validate;
import de.rincewind.interfaceplugin.gui.elements.abstracts.CraftElement;

public class CraftElementMap extends CraftElement implements ElementMap {

	private static final Predicate<Displayable> defaultFilter = (item) -> {
		return true;
	};

	private Color color;

	private int page;
	private int selected;

	private Displayable disabledIcon;

	private Predicate<Displayable> filter;
	private List<Displayable> items;

	private List<Element> nextFliper;
	private List<Element> previousFliper;

	public CraftElementMap(WindowEditor handle) {
		super(handle);

		this.page = 1;
		this.selected = -1;
		this.color = Color.TRANSLUCENT;
		this.filter = CraftElementMap.defaultFilter;
		this.disabledIcon = DisplayableDisabled.default_icon;
		this.nextFliper = new ArrayList<>();
		this.previousFliper = new ArrayList<>();
		this.items = new ArrayList<>();

		this.getComponent(Element.ENABLED).setEnabled(true);
		this.getComponent(Element.WIDTH).setEnabled(true);
		this.getComponent(Element.HEIGHT).setEnabled(true);

		this.getEventManager().registerListener(ElementInteractEvent.class, (event) -> {
			this.select(this.convert(event.getPoint()));
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
			throw new IllegalArgumentException("The page is invalid");
		}

		this.page = page;
		this.update();
		this.updateFliper();
	}

	@Override
	public void addItem(Displayable item) {
		this.items.add(item);
		this.update();
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
		element.setComponentValue(Element.ENABLED, !this.isLastPage());
		element.getEventManager().registerListener(ElementInteractEvent.class, this.new FlipListener(1));
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
		element.setComponentValue(Element.ENABLED, !this.isFirstPage());
		element.getEventManager().registerListener(ElementInteractEvent.class, this.new FlipListener(-1));
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
		return size / count + (size % count == 0 ? 0 : 1);
	}

	@Override
	public int getFirstIndex(int page) {
		return (page - 1) * this.getCountPerPage();
	}

	@Override
	public int size() {
		return this.items.size();
	}

	@Override
	public int filteredSize() {
		return this.items.stream().filter(this.filter).mapToInt(item -> 1).sum();
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
	public <T> T get(Point point) {
		return this.get(point, this.page);
	}

	@Override
	public <T> T get(Point point, int page) {
		return this.get(this.convert(point), this.page);
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
	@SuppressWarnings("unchecked")
	public <T extends Displayable> T getItem(Point point, int page) {
		return (T) this.items.get(this.convert(point));
	}

	@Override
	public List<Displayable> getItems() {
		return Collections.unmodifiableList(this.items);
	}

	@Override
	protected Icon getIcon0(Point point) {
		Stream<Displayable> stream = this.items.stream().filter(this.filter);

		if (this.isEnabled()) {
			Displayable item = stream.skip(this.convert(point)).findAny().orElse(null);

			if (item != null) {
				return item.getIcon();
			} else {
				return this.color.asIcon();
			}
		} else {
			return this.getDisabledIcon();
		}
	}

	private void updateFliper() {
		Iterator<Element> iterator = this.nextFliper.iterator();

		while (iterator.hasNext()) {
			iterator.next().setComponentValue(Element.ENABLED, !this.isLastPage());
		}

		iterator = this.previousFliper.iterator();

		while (iterator.hasNext()) {
			iterator.next().setComponentValue(Element.ENABLED, !this.isFirstPage());
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

	private int convert(Point point) {
		return this.getHeight() * point.getY() + point.getX();
	}

	private class FlipListener implements InterfaceListener<ElementInteractEvent> {

		private int pageOffset;

		public FlipListener(int pageOffset) {
			this.pageOffset = pageOffset;
		}

		@Override
		public void onAction(ElementInteractEvent event) throws EventPipelineException {
			CraftElementMap.this.setPage(CraftElementMap.this.getPage() + this.pageOffset);
		}

	}

}
