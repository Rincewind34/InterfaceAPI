package de.rincewind.interfaceapi.gui.elements.util;

import java.util.function.BiConsumer;

import de.rincewind.interfaceapi.exceptions.ElementComponentException;

public class ElementComponent<T> {

	private boolean enabled;
	private boolean allowNull;

	private T value;
	private T defaultValue;

	private Class<T> cls;

	private BiConsumer<T, T> onChange;

	public ElementComponent(Class<T> cls, T defaultValue, BiConsumer<T, T> onChange) {
		assert cls != null : "Class is null";
		assert onChange != null : "Callback is null";
		
		this.defaultValue = defaultValue;
		this.value = defaultValue;
		this.onChange = onChange;
		this.cls = cls;
		this.enabled = true;
		this.allowNull = this.defaultValue == null;
	}

	public void reset() {
		this.setValue(this.defaultValue);
	}

	public void setValue(T value) {
		this.validateEnabled();

		if (value == null && !this.allowNull) {
			throw new IllegalArgumentException("Null is not allowed as value");
		}

		if (this.value != value) {
			T valueOld = this.value;
			this.value = value;
			this.onChange.accept(valueOld, this.value);
		}
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setAllowNull(boolean allowNull) {
		this.allowNull = allowNull;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public T getValue() {
		return this.enabled ? this.value : this.defaultValue;
	}

	public T getDefaultValue() {
		this.validateEnabled();

		return this.defaultValue;
	}

	public Class<T> getTypeClass() {
		return this.cls;
	}

	private void validateEnabled() {
		if (!this.enabled) {
			throw new ElementComponentException("This component is disabled");
		}
	}

	public static class PositiveNumberElementComponent<T extends Number> extends ElementComponent<T> {

		public PositiveNumberElementComponent(Class<T> cls, T defaultValue, BiConsumer<T, T> onChange) {
			super(cls, defaultValue, onChange);
		}
		
		@Override
		public void setValue(T value) {
			if (value != null && value.doubleValue() <= 0) {
				throw new IllegalArgumentException("The value cannot be smaller than 1");
			}
			
			super.setValue(value);
		}

	}

}
