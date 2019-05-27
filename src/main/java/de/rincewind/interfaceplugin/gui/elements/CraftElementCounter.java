package de.rincewind.interfaceplugin.gui.elements;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import de.rincewind.interfaceapi.gui.elements.ElementCounter;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.util.Point;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowEditor;
import de.rincewind.interfaceapi.handling.InterfaceListener;
import de.rincewind.interfaceapi.handling.element.CountChangeEvent;
import de.rincewind.interfaceapi.handling.element.ElementInteractEvent;
import de.rincewind.interfaceplugin.Validate;
import de.rincewind.interfaceplugin.gui.elements.abstracts.CraftElementDisplayable;

public class CraftElementCounter extends CraftElementDisplayable implements ElementCounter {

	private int count;

	private int minCount;
	private int maxCount;

	private Integer fallback;

	private final Set<Element> incrementingElements;
	private final Set<Element> decrementingElements;

	public CraftElementCounter(WindowEditor handle) {
		super(handle);

		this.getComponent(Element.INSTRUCTIONS).setEnabled(true);

		this.minCount = 0;
		this.maxCount = Integer.MAX_VALUE;

		this.incrementingElements = new HashSet<>();
		this.decrementingElements = new HashSet<>();

		this.getComponent(Element.WIDTH).setEnabled(true);
		this.getComponent(Element.HEIGHT).setEnabled(true);

		this.getEventManager().registerListener(ElementInteractEvent.class, (event) -> {
			this.setCount(this.getFallbackCalculated());
		}).addAfter();
	}

	@Override
	public void setFallback(Integer value) {
		this.fallback = value;
	}

	@Override
	public Integer getFallback() {
		return this.fallback;
	}

	@Override
	public int getCount() {
		return this.count;
	}

	@Override
	public int getMaxCount() {
		return this.maxCount;
	}

	@Override
	public int getMinCount() {
		return this.minCount;
	}

	@Override
	public void setMaxCount(int maxCount) {
		if (maxCount < this.minCount) {
			throw new RuntimeException("The maximal count is smaller than the current minimal count!");
		}

		this.maxCount = maxCount;
		this.setCount(this.count);
	}

	@Override
	public void setMinCount(int minCount) {
		if (minCount > this.maxCount) {
			throw new IllegalArgumentException("The minimal count is bigger than the current maximal count");
		}

		this.minCount = minCount;
		this.setCount(this.count);
	}

	@Override
	public void setCount(int count, boolean fireEvent) {
		if (count > this.maxCount) {
			count = this.maxCount;
		}

		if (count < this.minCount) {
			count = this.minCount;
		}

		if (count != this.count) {
			int previous = this.count;
			this.count = count;

			if (fireEvent) {
				this.getEventManager().callEvent(CountChangeEvent.class, new CountChangeEvent(this, previous));
			}

			this.update();
			this.updateIncrementors();
		}
	}

	@Override
	public void increment() {
		this.setCount(this.count + 1);
	}

	@Override
	public void decrement() {
		this.setCount(this.count - 1);
	}

	@Override
	public void registerIncrementer(Element element, int value, int shiftedValue) {
		Validate.notNull(element, "The element cannot be null");

		if (value <= 0) {
			throw new IllegalArgumentException("The value cannot be smaller than 1");
		}

		if (!element.isElementComponentEnabled(Element.ENABLED)) {
			throw new IllegalArgumentException("The element cannot be disbaled");
		}

		if (this.incrementingElements.contains(element)) {
			return;
		}

		this.incrementingElements.add(element);

		element.setComponentValue(Element.ENABLED, this.isEnabled() && this.count < this.maxCount);
		element.getEventManager().registerListener(ElementInteractEvent.class, this.new ActionHandler(value, shiftedValue)).addAfter();
	}

	@Override
	public void registerDecrementer(Element element, int value, int shiftedValue) {
		Validate.notNull(element, "The element cannot be null");

		if (value <= 0) {
			throw new IllegalArgumentException("The value cannot be smaller than 1");
		}

		if (!element.isElementComponentEnabled(Element.ENABLED)) {
			throw new IllegalArgumentException("The element cannot be disbaled");
		}

		if (this.decrementingElements.contains(element)) {
			return;
		}

		this.decrementingElements.add(element);

		element.setComponentValue(Element.ENABLED, this.isEnabled() && this.count > this.minCount);
		element.getEventManager().registerListener(ElementInteractEvent.class, this.new ActionHandler(-value, -shiftedValue)).addAfter();
	}

	@Override
	public void unregisterFliper(Element element) {
		Validate.notNull(element, "Element cannot be null");

		this.incrementingElements.remove(element);
		this.decrementingElements.remove(element);
	}

	@Override
	public InterfaceListener<ElementInteractEvent> newIncrementListener(int value, int shiftedValue) {
		return this.new ActionHandler(value, shiftedValue);
	}

	@Override
	public void onElementAdded() {
		this.setComponentValue(Element.INSTRUCTIONS, true);
		this.setWorkInstructions(false);
	}
	
	@Override
	protected void onEnabledChange() {
		this.updateIncrementors();
	}

	@Override
	protected Icon getIcon0(Point point) {
		Icon icon = super.getIcon0(point);

		if (this.isEnabled()) {
			icon.getLore().setEnd(this.buildLoreEnd());
		}

		return icon;
	}

	private int getFallbackCalculated() {
		return this.fallback != null ? this.fallback.intValue() : (this.minCount + (this.maxCount - this.minCount) / 2);
	}

	private String buildLoreEnd() {
		return "§7Aktueller Wert: §6" + this.count
				+ (this.showInstructions() ? "\\n" + String.format(ElementCounter.INSTRUCTIONS_FALLBACK, this.getFallbackCalculated())
						: "");
	}

	private void updateIncrementors() {
		Iterator<Element> iterator = this.incrementingElements.iterator();

		while (iterator.hasNext()) {
			iterator.next().setComponentValue(Element.ENABLED, this.isEnabled() && this.count < this.maxCount);
		}

		iterator = this.decrementingElements.iterator();

		while (iterator.hasNext()) {
			iterator.next().setComponentValue(Element.ENABLED, this.isEnabled() && this.count > this.minCount);
		}
	}

	private class ActionHandler implements InterfaceListener<ElementInteractEvent> {

		private int value;
		private int shiftedValue;

		private ActionHandler(int value, int shiftedValue) {
			this.value = value;
			this.shiftedValue = shiftedValue;
		}

		@Override
		public void onAction(ElementInteractEvent event) {
			CraftElementCounter.this
					.setCount(CraftElementCounter.this.getCount() + (event.isShiftClick() ? this.shiftedValue : this.value));
		}

	}

}
