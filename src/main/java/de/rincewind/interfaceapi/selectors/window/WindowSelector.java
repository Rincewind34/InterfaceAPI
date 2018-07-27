package de.rincewind.interfaceapi.selectors.window;

import java.util.Collection;
import java.util.function.Consumer;

import org.bukkit.plugin.Plugin;

import de.rincewind.interfaceapi.InterfaceAPI;
import de.rincewind.interfaceapi.selectors.SelectHandler;
import de.rincewind.interfaceapi.selectors.Selector;
import de.rincewind.interfaceplugin.Validate;
import de.rincewind.interfaceplugin.gui.windows.CraftWindowSizeable;

public class WindowSelector<T> extends CraftWindowSizeable implements Selector<T> {

	private boolean selectSameValue;
	private boolean selectMultibleTimes;

	private final Consumer<? super T> action;
	private final SelectHandler<T> handler;
	
	public WindowSelector(Plugin plugin, Consumer<? super T> action, Collection<? extends T> elements, boolean defaultSet, T current, Class<T> selectingType) {
		super(plugin);

		Validate.notNull(action, "The action cannot be null");
		
		this.action = action;
		this.handler = new SelectHandler<>(this, elements, true, selectingType, defaultSet, current);
	}

	@Override
	public final void select(T select) {
		if (this.handler.validateSelection(select)) {
			if (!this.selectMultipleTimes()) {
				InterfaceAPI.getSetup(this.getUser()).close(this);
			}

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
	public final boolean allowCreation() {
		return this.handler.allowCreation();
	}

	@Override
	public boolean selectMultipleTimes() {
		return this.selectMultibleTimes;
	}

	@Override
	public boolean selectSameValue() {
		return this.selectSameValue;
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

	public void setSelectMultibleTimes(boolean selectMultibleTimes) {
		this.selectMultibleTimes = selectMultibleTimes;
	}

	public void setSelectSameValue(boolean selectSameValue) {
		this.selectSameValue = selectSameValue;
	}

}