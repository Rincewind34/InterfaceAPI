package de.rincewind.interfaceapi.gui.components;

import java.util.List;
import java.util.Set;

import de.rincewind.interfaceapi.exceptions.APIException;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.ElementCreator;
import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowEditor;

/**
 * If a class implements this methods, you are able to add elements
 * into that object. Each element-instance can only be added once.
 * The size and/or the position are set in the element-instance.
 * It is possible, that two element are on the same slot. The element,
 * witch is added earlier is that one, that you can see.
 * The methods take no care about, if the elements added to this object
 * can be rendered or if the added elements are out of the shown area.
 * 
 * The elements can decide, what item lays in there area. The area of an
 * element is defined as rectangle limited with the elements size.
 * 
 * There are tow special items:
 * {@link Modifyable#EMPTY_USED_SLOT}
 * {@link Modifyable#INVISIBLE_ELEMENT}
 * 
 * @author Rincewind34
 * @since 2.3.3
 * 
 * @see WindowEditor
 */
public abstract interface Modifyable {
	
	public abstract void renderAll();
	
	public abstract void renderFrame();
	
	public abstract void renderElement(Element element);
	
	public abstract void renderPoint(Point point);
	
	public abstract void renderPoints(Iterable<Point> points);
	
	/**
	 * Adds an element to this object. To create a new element use
	 * the {@link ElementCreator} (Modifyable{@link #elementCreator()}).
	 * 
	 * This method calls {@link Modifyable#readItemsFrom(Element)} to
	 * get the items to set from the new element. 
	 * 
	 * @param element to add
	 * 
	 * @throws NullPointerException if the element is <code>null</code>
	 * @throws APIException if the element is already added to a object
	 */
	public abstract void addElement(Element element);
	
	/**
	 * Removes an element from this object.
	 * 
	 * This method calls {@link Modifyable#clearItemsFrom(Element)} to
	 * remove the items set from the old element.
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
	 * To reconfigure the items in the cache this method calls {@link Modifyable#clearItemsFrom(Element)}
	 * and {@link Modifyable#readItemsFromAll()}.
	 * 
	 * @param element to priorize
	 * 
	 * @throws NullPointerException if the element is <code>null</code>
	 * @throws APIException if the element is not added in this object.
	 */
	public abstract void priorize(Element element);
	
	/**
	 * Removes all elements from this object using {@link Modifyable#removeElement(Element)}
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