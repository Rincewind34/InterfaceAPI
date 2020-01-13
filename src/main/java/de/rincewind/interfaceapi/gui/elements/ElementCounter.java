package de.rincewind.interfaceapi.gui.elements;

import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.abstracts.ElementDisplayable;
import de.rincewind.interfaceapi.gui.util.Point;
import de.rincewind.interfaceapi.gui.util.creators.ElementCreator;
import de.rincewind.interfaceapi.handling.InterfaceListener;
import de.rincewind.interfaceapi.handling.element.ElementInteractEvent;
import de.rincewind.interfaceapi.handling.element.ElementValueChangeEvent;
import de.rincewind.interfaceapi.util.HeadsDatabase;

/**
 * With this element you can count
 * 
 * @author torben
 *
 */
public abstract interface ElementCounter extends ElementDisplayable {

	public static String INSTRUCTIONS_FALLBACK = "§7§lLK: §7§oWert auf §7%d§7§o setzen";

	public abstract void setFallback(Integer value);

	public abstract Integer getFallback();

	/**
	 * Returns the current count of this element.
	 * 
	 * @return the current count of this element
	 */
	public abstract int getCount();

	/**
	 * Returns the maximal countable number of this element.
	 * 
	 * @return the maximal countable number of this element
	 */
	public abstract int getMaxCount();

	/**
	 * Returns the minimal countable number of this element.
	 * 
	 * @return the minimal countable number of this element
	 */
	public abstract int getMinCount();

	/**
	 * Sets the maximal countable number to this element. If the new count is
	 * greater than 64 the count will be set automatically to 64.
	 * 
	 * @param maxCount
	 *            change to
	 */
	public abstract void setMaxCount(int maxCount);

	/**
	 * Sets the minimal countable number to this element. If the new count is
	 * smaller than 1 the count will be set automatically to 1.
	 * 
	 * @param minCount
	 *            change to
	 */
	public abstract void setMinCount(int minCount);

	/**
	 * Sets the current displayed count. If the new count does not match the
	 * frame of minimal and maximal count, the new count will be adapted.
	 * 
	 * @param count
	 *            change to
	 * @param fireEvent
	 *            whether {@link ElementValueChangeEvent} should be fired
	 */
	public abstract void setCount(int count, boolean fireEvent);

	/**
	 * Adds to the current count 1. To set the new count
	 * {@link ElementCounter#setCount(int)} will be used.
	 */
	public abstract void increment();

	/**
	 * Subtracts 1 of the current count. To set the new count
	 * {@link ElementCounter#setCount(int)} will be used.
	 */
	public abstract void decrement();

	/**
	 * Adds to an {@link Element} a listener to increment this element. The
	 * value to add by clicking the button can be specified. If the user
	 * shift-clicks the button, the value to add will multiplied by 2.
	 * 
	 * You can also set the value to a number smaller than 0 to subtract counts
	 * from this element by clicking the button.
	 * 
	 * @param element
	 *            add the listener to
	 * @param value
	 *            incremented by clicking
	 * @param shiftedValue
	 *            incremented by shift clicking
	 * 
	 * @throws NullPointerException
	 *             if the button is <code>null</code>
	 */
	public abstract void registerIncrementer(Element element, int value, int shiftedValue);

	public abstract void registerDecrementer(Element element, int value, int shiftedValue);

	public abstract void unregisterFliper(Element element);

	public abstract InterfaceListener<ElementInteractEvent> newIncrementListener(int value, int shiftedValue);

	public default void setCount(int count) {
		this.setCount(count, true);
	}

	public default ElementItem newIncrementor(ElementCreator creator, Point point, int value, int shiftedValue) {
		ElementItem item = creator.newItem();
		item.setPoint(point);
		item.setIcon(HeadsDatabase.arrowWoodUp().rename("§7§o+" + value + " / +" + shiftedValue));
		item.setDisabledIcon(HeadsDatabase.arrowStoneUp());

		this.registerIncrementer(item, value, shiftedValue);
		return item;
	}

	public default ElementItem newDecrementor(ElementCreator creator, Point point, int value, int shiftedValue) {
		ElementItem item = creator.newItem();
		item.setPoint(point);
		item.setIcon(HeadsDatabase.arrowWoodDown().rename("§7§o-" + value + " / -" + shiftedValue));
		item.setDisabledIcon(HeadsDatabase.arrowStoneDown());

		this.registerDecrementer(item, value, shiftedValue);
		return item;
	}

}
