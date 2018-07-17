package de.rincewind.interfaceapi.gui.elements;

import java.util.Iterator;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import de.rincewind.interfaceapi.exceptions.APIException;
import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.components.DisplayableDisabled;
import de.rincewind.interfaceapi.gui.components.Selectable;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.util.Color;
import de.rincewind.interfaceapi.gui.util.Direction;
import de.rincewind.interfaceapi.gui.util.Point;
import de.rincewind.interfaceapi.gui.util.creators.ElementCreator;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowEditor;
import de.rincewind.interfaceapi.handling.element.ListChangeSelectEvent;
import de.rincewind.interfaceapi.util.HeadsDatabase;

/**
 * With this element you can create a list of entries and you are able to scroll
 * through the entries.
 * 
 * The list can be resized, and perhaps the entries will be resized too. You can
 * only scroll in one direction through the list. This depends on the type of
 * this list. If the type is {@link Direction#HORIZONTAL} the entries will
 * be rendered laying from the right to the left (Horizontal), so they will be
 * added to the list from the top to the bottom. With
 * {@link Direction#VERTICAL} the same but you scroll the right to the
 * left.
 * 
 * This element contains its own color. This color will be displayed, when there
 * are not enough entries to fill the area of the list, or when you scroll to
 * the end of the list.
 * 
 * If the user clicks on an entry, this entry will be selected (by the default,
 * it will be enchanted). By an second click on the selected entry, the entry
 * will be unselected.
 * 
 * For a better handling with this element, you can 'save' an object from an
 * specified type in each list-entry. You can get this object from each entry.
 * For example you can save a {@link String} in an entry an your able to
 * identify the entries by that string. Therefore, you do not have to compare
 * the icons or the entries its self.
 * 
 * This element implements {@link Iterable} to iterate throw all added entries.
 * 
 * This element implements {@link Selectable} and defines the method
 * {@link Selectable#select()} as <code>this.select(0)</code> with a little
 * check if the size is not 0. If it is 0 the {@link APIException} will be
 * thrown.
 * 
 * The {@link ListChangeSelectEvent} will be called, when the user selects an
 * entry or you calls the method {@link ElementList#select(int)}.
 * 
 * @author Rincewind34
 * @since 2.3.3
 */
public abstract interface ElementList extends Element, Selectable, DisplayableDisabled, Iterable<Displayable> {

	/**
	 * Sets the color to a specified value. This method does not update the
	 * color by its self to the handler ({@link WindowEditor}).
	 * 
	 * @param color
	 *            The color to set
	 */
	public abstract void setColor(Color color);

	/**
	 * Adds an entry to this list. The list will be automatically updated in the
	 * {@link WindowEditor}.
	 * 
	 * @param item
	 *            to add
	 * 
	 * @throws NullPointerException
	 *             if the item is <code>null</code>
	 */
	public abstract void addItem(Displayable item);

	public abstract void addItem(int index, Displayable item);

	public abstract <T extends Enum<?>> void addItems(Class<T> cls);

	/**
	 * Removes an entry from this list. The list will be automatically updated
	 * in the {@link WindowEditor}.
	 * 
	 * @param item
	 *            to remove
	 * 
	 * @throws NullPointerException
	 *             if the item is <code>null</code>
	 */
	public abstract void removeItem(Displayable item);

	public abstract void clear();

	/**
	 * Changes the type of this list. By the default it is
	 * {@link Direction#VERTICAL}.
	 * 
	 * @param type
	 *            to set
	 * 
	 * @throws NullPointerException
	 *             if the type is <code>null</code>
	 */
	public abstract void setType(Direction type);

	/**
	 * Sets the index of the first displayed entry. If the index does not match
	 * the frame of 0 and the size of the entrylist subtracting 1, this method
	 * will do nothing.
	 * 
	 * @param index
	 *            to set
	 */
	public abstract void setStartIndex(int index);

	/**
	 * Selects a specified index. If the index is not selectable, this method
	 * will do nothing.
	 * 
	 * @param index
	 *            to select
	 */
	public abstract void select(int index);

	public abstract void select(int index, boolean fireEvent);

	public abstract void deselect(boolean fireEvent);

	/**
	 * Sets the item-modifier used to modify the entry, witch is selected.
	 * 
	 * @param modifier
	 *            The modifier to set
	 * 
	 * @throws NullPointerException
	 *             if the modifier is <code>null</code>
	 */
	public abstract void setSelectModifyer(UnaryOperator<Icon> modifier);

	/**
	 * Adds to an {@link Element} a listener to scroll through this element. The
	 * value to scroll forwards by clicking the button can be specified. If the
	 * user shift-clicks the button, the value will be multiplied by 2.
	 * 
	 * You can also set the value to a number smaller than 0 to scroll backwards
	 * through this element by clicking the button.
	 * 
	 * @param btn
	 *            add the listener to
	 * @param value
	 *            scrolling through this element by clicking
	 * 
	 * @throws NullPointerException
	 *             if the button is <code>null</code>
	 */
	public abstract void addScroler(Element btn, int value);

	/**
	 * Returns the index of the first displayed entry (at the beginning of this
	 * element).
	 * 
	 * @return the index of the first displayed entry
	 */
	public abstract int getStartIndex();

	/**
	 * Returns the currently selected index of this element. If there is no
	 * selected entry, this method returns -1.
	 * 
	 * @return the currently selected index of this element
	 */
	public abstract int getSelectedIndex();

	/**
	 * Returns the color of this element. By the default, it is
	 * {@link Color#NONE}
	 * 
	 * @return the color of this element
	 */
	public abstract Color getColor();

	public abstract Displayable getSelectedItem();

	public abstract Displayable getItem(int index);
	
	public abstract UnaryOperator<Icon> getSelectModifier();
	
	public abstract <T> T getSelected();

	public abstract <T> T get(int index);
	
	public abstract List<Displayable> getItems();
	
	@Override
	public default void select() {
		if (this.getSize() != 0) {
			this.select(0);
		} else {
			throw new ArrayIndexOutOfBoundsException("Cannot select an item in this list!");
		}
	}

	@Override
	public default Iterator<Displayable> iterator() {
		return this.getItems().iterator();
	}

	public default int getSize() {
		return this.getItems().size();
	}
	
	public default ElementItem newScroller(ElementCreator creator, Point point, int value) {
		ElementItem item = creator.newItem();
		item.setPoint(point);
		item.setIcon(value < 0 ? HeadsDatabase.arrowWoodLeft() : HeadsDatabase.arrowWoodRight());
		
		this.addScroler(item, value);
		return item;
	}

	public default <T> T getSelected(Class<T> cls) {
		return this.getSelected();
	}

	public default <T> T get(Class<T> cls, int index) {
		return this.get(index);
	}
	
	public default <T extends Displayable> List<T> getItems(Class<T> cls) {
		return this.getItems().stream().map(cls::cast).collect(Collectors.toList());
	}

}
