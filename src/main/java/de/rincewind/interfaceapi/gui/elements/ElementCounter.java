package de.rincewind.interfaceapi.gui.elements;

import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.abstracts.ElementDisplayable;

/**
 * With this element you can count
 * 
 * @author torben
 *
 */
public abstract interface ElementCounter extends ElementDisplayable {
	
	public static final int MAXIMUM_COUNT = 64;
	
	public static final int MINIMUM_COUNT = 1;
	
	
	/**
	 * Returns the current count of this element.
	 * 
	 * @return the current count of this element
	 */
	public abstract int getCount();
	
	/**
	 * Returns the maximal countable number of this element.
	 * The default is set to 10 ({@link ElementDefaults#COUNTER_MAXIMUM}).
	 * 
	 * @return the maximal countable number of this element
	 */
	public abstract int getMaxCount();
	
	/**
	 * Returns the minimal countable number of this element.
	 * The default is set to 1 ({@link ElementDefaults#COUNTER_MINIMUM}).
	 * 
	 * @return the minimal countable number of this element
	 */
	public abstract int getMinCount();
	
	/**
	 * Sets the maximal countable number to this element. If the new count
	 * is greater than 64 the count will be set automaticly to 64.
	 * 
	 * @param maxCount change to
	 */
	public abstract void setMaxCount(int maxCount);
	
	/**
	 * Sets the minimal countable number to this element. If the new count
	 * is smaller than 1 the count will be set automaticly to 1.
	 * 
	 * @param minCount change to
	 */
	public abstract void setMinCount(int minCount);
	
	/**
	 * Sets the current displayed count. If the new count does not match the
	 * frame of minimal and maximal count, the new count will be adapted.
	 * 
	 * @param count change to
	 */
	public abstract void setCount(int count);
	
	/**
	 * Adds to the current count 1. To set the new count {@link ElementCounter#setCount(int)}
	 * will be used.
	 */
	public abstract void increment();
	
	/**
	 * Subtracts 1 of the current count. To set the new count {@link ElementCounter#setCount(int)}
	 * will be used.
	 */
	public abstract void decrement();
	
	/**
	 * Adds to an {@link ElementButton} a listener to increment this element. The value to
	 * add by clicking the button can be specified. If the user shiftclicks the button, the value
	 * to add will multiplied by 2.
	 * 
	 * You can also set the value to a number smaller than 0 to subtract counts from this element
	 * by clicking the button.
	 * 
	 * @param btn add the listener to
	 * @param value incremented by clicking
	 * 
	 * @throws NullPointerException if the button is <code>null</code>
	 */
	public abstract void addIncrementer(Element btn, int value);
	
}
