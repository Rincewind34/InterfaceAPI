package de.rincewind.interfaceapi.selectors;

import java.util.Collection;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Objects;

import de.rincewind.interfaceapi.exceptions.SelectorInterfaceException;
import de.rincewind.interfaceplugin.Validate;

public final class SelectHandler<T> {

	private boolean selected;
	private T lastSelection;

	private final boolean defaultSet;
	private final T defaultValue;

	private final Selector<T> handler;
	private final Class<T> selectingClass;
	private final Collection<? extends T> elements;

	public SelectHandler(Selector<T> handler, Collection<? extends T> elements, Class<T> selectingType, boolean defaultSet, T current) {
		Validate.notNull(elements, "The element collection cannot be null");
		Validate.notNull(selectingType, "The type class cannot be null");
		Validate.notNull(handler, "The handler cannot be null");
		
		this.defaultSet = defaultSet;
		this.defaultValue = current;
		this.selectingClass = selectingType;
		this.handler = handler;
		
		this.elements = Collections.unmodifiableCollection(elements);

		if (!this.handler.isNullSelectable()) {
			if (this.elements.isEmpty()) {
				throw new IllegalArgumentException("The elements are empty");
			}

			if (this.elements.contains(null)) {
				throw new IllegalArgumentException("Inconsistent null handling");
			}
		}

		// Do not check if default value is selectable
	}

	/*
	 * Does not check, if the value is selectable
	 */
	public boolean validateSelection(T select) {
		if (this.selected && !this.handler.selectMultipleTimes()) {
			throw new SelectorInterfaceException("There is already a value selected");
		}

		if (!this.handler.isSelectable(select)) {
			throw new IllegalArgumentException(select + " is not allowed");
		}

		if (!this.handler.selectSameValue() && (this.selected && Objects.equals(select, this.lastSelection))
				|| (this.defaultSet && Objects.equals(select, this.defaultValue))) {

			return false;
		}

		return true;
	}

	public boolean isSelected() {
		return this.selected;
	}

	public boolean isDefaultSet() {
		return this.defaultSet;
	}

	public T getCurrentlySelected() {
		if (this.selected) {
			return this.lastSelection;
		} else if (this.defaultSet) {
			return this.defaultValue;
		} else {
			throw new NoSuchElementException("There were no value selected");
		}
	}

	public Class<T> getSelectingClass() {
		return this.selectingClass;
	}

	public Collection<? extends T> getSelectableObjects() {
		return this.elements;
	}

}