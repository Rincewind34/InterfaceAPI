package de.rincewind.interfaceapi.gui.windows.abstracts;

import java.util.List;
import java.util.Set;

import de.rincewind.interfaceapi.exceptions.APIException;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.ElementCreator;
import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceapi.gui.windows.WindowEnchanter;

/**
 * Into this window you can add elements.
 * 
 * @author Rincewind34
 * @since 2.3.3
 * 
 * @see WindowColorable
 * @see WindowEnchanter
 */
public abstract interface WindowEditor extends WindowContainer {
	
	public abstract void renderAll();
	
	public abstract void renderFrame();
	
	public abstract void renderElement(Element element);
	
	public abstract void renderPoint(Point point);
	
	public abstract void renderPoints(Iterable<Point> points);
	
	/**
	 * Removes an element from this object.
	 * 
	 * @param element to remove
	 * 
	 * @throws NullPointerException if the element is <code>null</code>
	 * @throws APIException if the element is not added in this object.
	 */
	public abstract void removeElement(Element element);
	
	/**
	 * Priorizes an element in this object. After invoking this method,
	 * this object handles the element as an element added before all other
	 * elements.
	 * 
	 * To reconfigure the items in the cache this method calls {@link #renderElement(Element)}
	 * and {@link #renderAll()}.
	 * 
	 * @param element to priorize
	 * 
	 * @throws NullPointerException if the element is <code>null</code>
	 * @throws APIException if the element is not added in this object.
	 */
	public abstract void priorize(Element element);
	
	/**
	 * Removes all elements from this object using {@link #removeElement(Element)}
	 * for all added elements.
	 */
	public abstract void clearElements();
	
	/**
	 * Returns <code>true</code> if this object has a visible element at a
	 * specified point and <code>false</code> if not.
	 * 
	 * @param point to check
	 * 
	 * @return <code>true</code> if this object has a visible element at a
	 * 			specified point and <code>false</code> if not
	 * 
	 * @throws NullPointerException if the point is <code>null</code>
	 */
	public abstract boolean hasVisibleElementAt(Point point);
	
	/**
	 * Returns the first added visible element laying on a point.
	 * If there are no element laying on this point, this method
	 * will return <code>null</code>.
	 * 
	 * @param point to get an element.
	 * 
	 * @return the first added visible element laying on a point
	 * 
	 * @throws NullPointerException if the point is <code>null</code>
	 */
	public abstract Element getVisibleElementAt(Point point);
	
	/**
	 * Returns the {@link ElementCreator} of this object.
	 * 
	 * @return the {@link ElementCreator} of this object
	 */
	public abstract ElementCreator elementCreator();
	
	/**
	 * Returns all added elements. If there is no element
	 * present, this method will return an empty list.
	 * 
	 * @return all added elements.
	 */
	public abstract List<Element> getElements();
	
	/**
	 * Returns all elements laying on a specified point. Never mind, if
	 * the elements set position is equals to the specified point or if
	 * the element lays on this slot because of its size.
	 * If there is no element on this point, this method will return an
	 * empty list.
	 * 
	 * @param point specified
	 * 
	 * @return all elements laying on a specified point.
	 * 
	 * @throws NullPointerException if the point is <code>null</code>.
	 */
	public abstract List<Element> getElementsAt(Point point);
	
	public abstract Set<Point> getOccupiedPoints(Element element);
	
}
