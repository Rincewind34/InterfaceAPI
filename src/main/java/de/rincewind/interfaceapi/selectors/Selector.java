package de.rincewind.interfaceapi.selectors;

import java.util.Collection;
import java.util.NoSuchElementException;

import de.rincewind.interfaceapi.exceptions.SelectorInterfaceException;

/**
 * This interface predefines actions/attributes of an object that is able to
 * select a value from a pool of objects.<br>
 * <br>
 * <b>Pool of objects</b><br>
 * The pool is defined as {@link Collection} {@link #getSelectableObjects()}.
 * The {@link Selector} does not define whether <code>null</code> is selectable
 * or not. This attribute is defined by {@link #isNullSelectable()}. Together
 * they define which value is selectable and which is not. The definition need
 * to be consistent for the whole life time of an {@link Selector}. For further
 * restrictions read {@link #isNullSelectable()} and
 * {@link #getSelectableObjects()}.<br>
 * <br>
 * <b>Default value</b><br>
 * The {@link Selector} provides functionality to handle an already selected
 * value / default value. The default value does not have to meet the
 * requirements for a selectable value. Therefore, {@link #isSelectable(Object)}
 * could return <code>false</code> on default value. <br>
 * <br>
 * <b>Operation behavior</b><br>
 * The method {@link #select(Object)} executes a selection. The selection does
 * validate the object if it is selectable and throws an error if it is not.
 * Multiple selections are only allowed if {@link #selectMultipleTimes()}
 * returns <code>true</code>. The {@link Selector} only handles already selected
 * values if {@link #selectSameValue()} returns <code>true</code>.
 * 
 * @param <T>
 *            The type of selectable objects
 */
public interface Selector<T> {
	
	/**
	 * Selected a specific value to this {@link Selector}. The exact handling of
	 * the value if the selections was successful is not specified. After
	 * successfully selecting the <code>value</code>,
	 * {@link #getCurrentlySelected()} will return the selected value until the
	 * next successful selection.<br>
	 * <br>
	 * <b>Error cases</b>:
	 * <ul>
	 * <li>Invalid/Unselectable value: If the <code>value</code> is not
	 * selectable (e.g. <code>value</code> is <code>null</code> and
	 * {@link #isNullSelectable()} returns <code>false</code>),
	 * {@link IllegalArgumentException} will be thrown.</li>
	 * <li>Already selected value: If the <code>value</code> is already selected
	 * (see {@link #getCurrentlySelected()}), the method will handle the
	 * selection, if {@link #selectSameValue()} returns <code>true</code>. An
	 * exception won't be thrown in this case.</li>
	 * <li>Multiple selections: If a value is already selected
	 * ({@link #isSelected()} returns <code>true</code>) and
	 * {@link #selectMultipleTimes()} returns <code>false</code>,
	 * {@link SelectorInterfaceException} will be thrown.</li>
	 * </ul>
	 * 
	 * @param value
	 *            The value to be selected
	 * 
	 * @throws IllegalArgumentException
	 *             if the value is invalid to be selected
	 * @throws SelectorInterfaceException
	 *             if the selection was not successful
	 * 
	 * @see #isSelected()
	 * @see #getCurrentlySelected()
	 */
	public abstract void select(T value);

	/**
	 * Returns <code>true</code> if there was a value successfully selected.
	 * This does not take into account, if a default value was specified.
	 * 
	 * @return <code>true</code> if a value is selected, <code>false</code> if
	 *         not
	 */
	public abstract boolean isSelected();

	/**
	 * Returns <code>true</code> if a default value was specified and
	 * <code>false</code> if not. The result is consistent over the whole
	 * lifetime of this object.
	 * 
	 * @return <code>true</code> if the default value was specified.
	 */
	public abstract boolean isDefaultSet();

	/**
	 * Returns <code>true</code> if the selector should allow multiple
	 * successful selections and <code>false</code> if not. The result is
	 * consistent over the whole lifetime of this object.
	 * 
	 * @return <code>true</code> if the selector should allow multiple
	 *         selections.
	 */
	public abstract boolean selectMultipleTimes();

	/**
	 * Returns <code>true</code> if the selector should handle reselecting the
	 * same value and <code>false</code> if not. Reselecting does not imply
	 * multiple selections but the default value is considered too. The result
	 * is consistent over the whole lifetime of this object.
	 * 
	 * @return <code>true</code> if the selector should handle reselecting an
	 *         already selected value.
	 * 
	 * @see #getCurrentlySelected()
	 */
	public abstract boolean selectSameValue();

	/**
	 * Returns the currently selected value. The result takes the default value
	 * into account. Therefore, if {@link #isSelected()} returns
	 * <code>false</code> the default value will be returned. If
	 * {@link #isDefaultSet()} also returns <code>false</code>, an error will be
	 * thrown.<br>
	 * <code>selector.getCurrentlySelected() == null</code> is <b>not</b> equal
	 * to {@link #isSelected()}. <br>
	 * The result can <b>only</b> be <code>null</code>, if
	 * {@link #isNullSelectable()} returns <code>true</code>.
	 * 
	 * @return the currently selected value including the default value
	 * 
	 * @throws NoSuchElementException
	 *             if there is no value to be returned ({@link #isSelected()}
	 *             and {@link #isDefaultSet()} returns <code>false</code>)
	 */
	public abstract T getCurrentlySelected();

	/**
	 * Returns a (super) type class of the selectable values.
	 * 
	 * @return a (super) type class of the selectable values
	 */
	public abstract Class<T> getSelectingClass();

	/**
	 * Returns a unmodifiable collection containing all values selectable by
	 * this selector. The collection is allowed to contain <code>null</code>, if
	 * {@link #isNullSelectable()} returns <code>true</code>. If
	 * {@link #isNullSelectable()} returns <code>false</code>, the collection
	 * needs to contain at least one object.<br>
	 * The default value, if defined, does not need to be contained in this
	 * collection.<br>
	 * 
	 * @return a unmodifiable collection containing all values selectable by
	 *         this selector
	 * 
	 * @see #isNullSelectable()
	 */
	public abstract Collection<? extends T> getSelectableObjects();

	/**
	 * Returns <code>true</code> if the <code>value</code> is selectable by this
	 * selector taking into account for example {@link #getSelectableObjects()}
	 * and {@link #isNullSelectable()} and <code>false</code> if not.
	 * 
	 * @param value
	 *            the value no be checked
	 * @return if the <code>value</code> is selectable
	 */
	public default boolean isSelectable(T value) {
		return (value == null && this.isNullSelectable()) || this.getSelectableObjects().contains(value);
	}

	/**
	 * Returns <code>true</code> if <code>null</code> is selectable by this
	 * selector and <code>false</code> if not. By default this method is equal
	 * to <code>selector.getSelectableObjects().contains(null)</code>.
	 * 
	 * @return if <code>null</code> is selectable
	 */
	public default boolean isNullSelectable() {
		return this.getSelectableObjects().contains(null);
	}

}
