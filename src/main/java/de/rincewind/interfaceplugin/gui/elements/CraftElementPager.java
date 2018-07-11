package de.rincewind.interfaceplugin.gui.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.base.Objects;
import com.google.common.base.Predicates;

import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.components.DisplayableDisabled;
import de.rincewind.interfaceapi.gui.elements.ElementPager;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.ElementSet;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.util.Color;
import de.rincewind.interfaceapi.gui.util.Point;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowEditor;
import de.rincewind.interfaceapi.handling.InterfaceListener;
import de.rincewind.interfaceapi.handling.element.ElementInteractEvent;
import de.rincewind.interfaceplugin.Validate;
import de.rincewind.interfaceplugin.gui.elements.abstracts.CraftElement;

public class CraftElementPager extends CraftElement implements ElementPager {
	
	private int pageIndex;

	private Color color;
	private Displayable disabledIcon;

	private List<PageElement> elements;

	private final Set<Element> nextFliper;
	private final Set<Element> previousFliper;

	public CraftElementPager(WindowEditor handle) {
		super(handle);

		this.pageIndex = 0;

		this.color = Color.BLACK;
		this.disabledIcon = DisplayableDisabled.default_icon;

		this.elements = new ArrayList<>();
		this.nextFliper = new HashSet<>();
		this.previousFliper = new HashSet<>();

		this.elements.add(PageElement.NEW_PAGE);
	}

	@Override
	public void setDisabledIcon(Displayable icon) {
		this.disabledIcon = Displayable.checkNull(icon);
	}

	@Override
	public void addPageElement(ElementSet elements, int height) {
		Validate.notNull(elements, "The element set cannot be null");

		if (height <= 0) {
			throw new IllegalArgumentException("The hight cannot be smaller than 1");
		}

		PageElement element = new PageElement(height, elements);

		int usedHeight = 0;

		for (int i = this.elements.size() - 1; this.elements.get(i) != PageElement.NEW_PAGE; i--) {
			usedHeight = usedHeight + this.elements.get(i).height;
		}

		if (usedHeight + element.height > this.getHeight()) {
			usedHeight = 0;
			this.elements.add(PageElement.NEW_PAGE);
		}

		this.elements.add(element);

		element.setOffsetY(usedHeight);
		element.elements.setElementsVisible(this.isLastPage());

		if (this.isEnabled() && this.isLastPage()) {
			this.update();
		}
	}

	@Override
	public void removePageElement(ElementSet elements) {
		Validate.notNull(elements, "The element set cannot be null");

		int index = 1; /* Skip first page */

		for (; index < this.elements.size(); index++) {
			if (this.elements.get(index).elements == elements) {
				this.elements.remove(index).setOffsetY(0);
				this.recalculateList(index - 1);

				elements.setElementsVisible(true);

				this.updateEnabled();
				break;
			}
		}
	}

	@Override
	public void nextPage() {
		if (this.isLastPage()) {
			throw new NoSuchElementException("Already on last page");
		}

		boolean visible = false;

		for (int i = this.pageIndex + 1; i < this.elements.size(); i++) {
			PageElement element = this.elements.get(i);

			if (element == PageElement.NEW_PAGE) {
				if (!visible) {
					visible = true;
				} else {
					break;
				}
			} else {
				element.elements.setElementsVisible(visible);
			}
		}
		
		this.updateFliper();
	}

	@Override
	public void previousPage() {
		if (this.isFirstPage()) {
			throw new NoSuchElementException("Already on first page");
		}

		for (int i = this.pageIndex + 1; i < this.elements.size(); i++) {
			PageElement element = this.elements.get(i);

			if (element != PageElement.NEW_PAGE) {
				element.elements.setElementsVisible(false);
			} else {
				break;
			}
		}

		/*
		 * If this for loop causes an ArrayIndexOutOfBoundsException, the list
		 * is malformed containing no NEW_PAGE at index 0
		 */
		for (int i = this.pageIndex - 1;; i--) {
			PageElement element = this.elements.get(i);

			if (element != PageElement.NEW_PAGE) {
				element.elements.setElementsVisible(true);
			} else {
				break;
			}
		}
		
		this.updateFliper();
	}

	@Override
	public void setPage(int page) {
		int pageIndex = this.findPageIndex(page);
		
		if (this.pageIndex == pageIndex) {
			return;
		}
		
		for (ElementSet elements : this.getCurrentPageElements()) {
			elements.setElementsVisible(false);
		}
		
		this.pageIndex = pageIndex;

		for (ElementSet elements : this.getCurrentPageElements()) {
			elements.setElementsVisible(true);
		}
		
		this.updateFliper();
	}

	@Override
	public void setColor(Color color) {
		Validate.notNull(color, "The color cannot be null");

		if (!Objects.equal(this.color, color)) {
			this.color = color;
			this.updateEnabled();
		}
	}

	@Override
	public void clear() {
		for (PageElement element : this.elements) {
			element.elements.setElementsVisible(false);
		}

		this.elements.clear();
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
		return this.pageIndex == 0;
	}

	@Override
	public boolean isLastPage() {
		return this.pageIndex == this.elements.lastIndexOf(PageElement.NEW_PAGE);
	}

	@Override
	public Color getColor() {
		return this.color;
	}

	@Override
	public int getPage() {
		int page = 0;
		
		for (int i = 0; i < this.pageIndex; i++) {
			if (this.elements.get(i) == PageElement.NEW_PAGE) {
				page = page + 1;
			}
		}
		
		return page;
	}

	@Override
	public int getMaxPage() {
		return (int) this.elements.stream().filter(Predicates.equalTo(PageElement.NEW_PAGE)).count();
	}

	@Override
	public int size() {
		return this.elements.size();
	}

	@Override
	public Icon getDisabledIcon() {
		return this.disabledIcon.getIcon();
	}

	@Override
	public InterfaceListener<ElementInteractEvent> newFlipListener(int offset) {
		return this.new FlipListener(offset);
	}

	@Override
	public List<ElementSet> getCurrentPageElements() {
		return Collections.unmodifiableList(this.calculatePageElements(this.pageIndex));
	}

	@Override
	public List<ElementSet> getPageElements() {
		return Collections.unmodifiableList(this.elements.stream().map(PageElement::set).collect(Collectors.toList()));
	}

	@Override
	public List<ElementSet> getPageElements(int page) {
		return Collections.unmodifiableList(this.calculatePageElements(this.findPageIndex(page)));
	}

	@Override
	protected Icon getIcon0(Point point) {
		if (this.isEnabled()) {
			return this.color.asIcon();
		} else {
			return this.disabledIcon.getIcon();
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

	private void recalculateList(int startIndex) {
		List<PageElement> newList = new ArrayList<>();

		if (startIndex > 0) {
			newList.addAll(this.elements.subList(0, startIndex));
		} else {
			newList.add(PageElement.NEW_PAGE);
		}

		int currentHeight = 0;

		for (int index = newList.lastIndexOf(PageElement.NEW_PAGE) + 1; index < newList.size(); index++) {
			currentHeight = currentHeight + newList.get(index).height;
		}

		for (int i = startIndex; i < this.elements.size(); i++) {
			PageElement element = this.elements.get(i);

			if (element != PageElement.NEW_PAGE) {
				element.elements.setElementsVisible(false);

				if (element.height + currentHeight > this.getHeight()) {
					currentHeight = 0;
					newList.add(PageElement.NEW_PAGE);
				}

				currentHeight = currentHeight + element.height;
				newList.add(element);
			}
		}

		this.elements = newList;
	}

	private int findPageIndex(int page) {
		if (page <= 0) {
			throw new NoSuchElementException("The page " + page + " is invalid");
		}

		int pageIndex = 0;

		for (; pageIndex < this.elements.size(); pageIndex++) {
			if (this.elements.get(pageIndex) == PageElement.NEW_PAGE) {
				page = page - 1;

				if (page == 0) {
					break;
				}
			}
		}

		if (page != 0) {
			throw new NoSuchElementException("The page " + page + " is invalid");
		}

		return pageIndex;
	}

	private List<ElementSet> calculatePageElements(int from) {
		List<ElementSet> result = new ArrayList<>();

		for (int i = from + 1; i < this.elements.size() && this.elements.get(i) != PageElement.NEW_PAGE; i++) {
			result.add(this.elements.get(i).elements);
		}

		return Collections.unmodifiableList(result);
	}

	private static class PageElement {

		public static final PageElement NEW_PAGE = new PageElement();

		private int height;
		private int offsetY;

		private ElementSet elements;

		private PageElement() {

		}

		public PageElement(int height, ElementSet elements) {
			assert height > 0 : "The height is smaller than 1";
			assert elements != null : "The element set is null";

			this.height = height;
			this.elements = elements;
		}

		public void setOffsetY(int offsetY) {
			this.elements.moveElements(0, offsetY - this.offsetY);
			this.offsetY = offsetY;
		}

		public ElementSet set() {
			return this.elements;
		}

	}

	private class FlipListener implements InterfaceListener<ElementInteractEvent> {

		private int value;

		private FlipListener(int value) {
			this.value = value;
		}

		@Override
		public void onAction(ElementInteractEvent event) {
			CraftElementPager.this.setPage(CraftElementPager.this.getPage() + (this.value * (event.isShiftClick() ? 2 : 1)));
		}

	}

}
