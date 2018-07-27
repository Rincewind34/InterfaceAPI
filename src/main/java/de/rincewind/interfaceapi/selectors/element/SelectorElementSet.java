package de.rincewind.interfaceapi.selectors.element;

import java.util.Collection;
import java.util.function.Consumer;

import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.ElementSet;
import de.rincewind.interfaceapi.selectors.SelectHandler;
import de.rincewind.interfaceapi.selectors.Selector;
import de.rincewind.interfaceplugin.Validate;

public class SelectorElementSet<T> extends ElementSet implements Selector<T> {

	private final Consumer<? super T> action;
	private final SelectHandler<T> handler;

	public SelectorElementSet(Collection<Element> windowElements, Consumer<? super T> action, Collection<? extends T> elements, Class<T> selectingType,
			boolean defaultSet, T current) {
		
		super(windowElements);

		Validate.notNull(action, "The action cannot be null");

		this.action = action;
		this.handler = new SelectHandler<>(this, elements, false, selectingType, defaultSet, current);
	}

	@Override
	public final void select(T select) {
		if (this.handler.validateSelection(select)) {
			this.action.accept(select);
		}
	}

	@Override
	public final boolean isSelected() {
		return this.handler.isSelected();
	}

	@Override
	public final boolean isDefaultSet() {
		return this.handler.isDefaultSet();
	}

	@Override
	public boolean selectMultipleTimes() {
		return true;
	}
	
	@Override
	public boolean allowCreation() {
		return this.handler.allowCreation();
	}

	@Override
	public boolean selectSameValue() {
		return false;
	}

	@Override
	public final T getCurrentlySelected() {
		return this.handler.getCurrentlySelected();
	}

	@Override
	public final Class<T> getSelectingClass() {
		return this.handler.getSelectingClass();
	}

	@Override
	public final Collection<? extends T> getSelectableObjects() {
		return this.handler.getSelectableObjects();
	}

}