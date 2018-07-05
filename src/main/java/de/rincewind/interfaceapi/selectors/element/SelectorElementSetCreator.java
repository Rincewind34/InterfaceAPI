package de.rincewind.interfaceapi.selectors.element;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;

import de.rincewind.interfaceapi.gui.elements.util.ElementSet;
import de.rincewind.interfaceapi.gui.util.Bounds;
import de.rincewind.interfaceapi.gui.util.creators.ElementCreator;
import de.rincewind.interfaceplugin.Validate;

public abstract class SelectorElementSetCreator<T> {

	private final Class<T> selectingClass;
	private final Supplier<Collection<? extends T>> elementGetter;

	public SelectorElementSetCreator(Class<T> selectingClass) {
		this(selectingClass, null);
	}

	public SelectorElementSetCreator(Class<T> selectingClass, Supplier<Collection<? extends T>> elementGetter) {
		Validate.notNull(selectingClass, "The type class cannot be null");

		this.selectingClass = selectingClass;
		this.elementGetter = elementGetter;
	}

	public SelectorElementSet<T> newSelector(ElementCreator creator, Bounds bounds, Consumer<? super T> action) {
		if (this.elementGetter == null) {
			throw new UnsupportedOperationException("No default element getter set");
		}

		return this.newSelector(creator, bounds, action, this.elementGetter.get());
	}

	public SelectorElementSet<T> newSelector(ElementCreator creator, Bounds bounds, Consumer<? super T> action, T currentValue) {
		if (this.elementGetter == null) {
			throw new UnsupportedOperationException("No default element getter set");
		}

		return this.newSelector(creator, bounds, action, this.elementGetter.get(), currentValue);
	}

	public SelectorElementSet<T> newSelector(ElementCreator creator, Bounds bounds, Consumer<? super T> action, Collection<? extends T> elements) {
		return this.newSelector(creator, bounds, action, elements, false, null);
	}

	public SelectorElementSet<T> newSelector(ElementCreator creator, Bounds bounds, Consumer<? super T> action, Collection<? extends T> elements, T currentValue) {
		return this.newSelector(creator, bounds, action, elements, true, currentValue);
	}

	protected abstract void setup(ElementCreator creator, Bounds bounds, boolean defaultSet, T defaultValue);

	private SelectorElementSet<T> newSelector(ElementCreator creator, Bounds bounds, Consumer<? super T> action, Collection<? extends T> elements, boolean defaultSet,
			T current) {

		return ElementSet.wrapCreation(creator, bounds, (wrappedCreator, wrappedBounds) -> {
			this.setup(wrappedCreator, wrappedBounds, defaultSet, current);
		}, (windowElements) -> {
			return new SelectorElementSet<>(windowElements, action, elements, this.selectingClass, defaultSet, current);
		});
	}

}
