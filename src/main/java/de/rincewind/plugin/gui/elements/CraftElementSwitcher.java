package de.rincewind.plugin.gui.elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.rincewind.api.exceptions.APIException;
import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.ElementSwitcher;
import de.rincewind.api.gui.elements.util.Icon;
import de.rincewind.api.handling.events.ButtonPressEvent;
import de.rincewind.api.handling.events.SwitchEvent;
import lib.securebit.Validate;

public class CraftElementSwitcher<T> extends CraftElementButton implements ElementSwitcher<T> {

	private List<SwitchItem<T>> items;
	private int switchid;

	public CraftElementSwitcher(Modifyable handle) {
		super(handle);

		this.items = new ArrayList<>();
		this.registerListener();
	}

	@Override
	public void setIcon(Icon icon) {
		throw new APIException("Cannot set icon of elementswitch!");
	}

	@Override
	public void updateItemMap() {
		this.iterate((point) -> {
			if (this.items != null && this.getSwitch() != null) {
				this.setItemAt(point, this.getSwitch().getItem());
			}
		});

		this.updateItemMap(false);
	}

	@Override
	public SwitchItem<T> next() {
		this.setSwitchId(this.switchid + 1);
		return this.getSwitch();
	}

	@Override
	public SwitchItem<T> back() {
		this.setSwitchId(this.switchid - 1);
		return this.getSwitch();
	}

	@Override
	public void setSwitchId(int index) {
		this.setSwitchId(index, true);
	}

	@Override
	public void setSwitchId(int index, boolean fireEvent) {
		this.switchid = index;

		if (this.switchid < 0) {
			this.switchid = this.size() - 1;
		} else if (this.size() == this.switchid) {
			this.switchid = 0;
		}

		if (fireEvent) {
			this.getEventManager().callEvent(SwitchEvent.class, new SwitchEvent(this));
		}

		this.getHandle().readItemsFrom(this);
	}

	@Override
	public int getSwitchIndex() {
		return this.switchid;
	}

	@Override
	public void addSwitch(SwitchItem<T> item) {
		Validate.notNull(item, "The switchitem cannot be null!");

		this.items.add(item);
		this.getHandle().readItemsFrom(this);
	}

	@Override
	public void removeSwitch(SwitchItem<T> item) {
		Validate.notNull(item, "The switchitem cannot be null!");

		this.items.remove(item);
		this.setSwitchId(this.switchid);
	}

	@Override
	public void clear() {
		this.items.clear();
	}

	@Override
	public int size() {
		return this.items.size();
	}

	@Override
	public SwitchItem<T> getSwitch() {
		if (this.size() == 0) {
			return null;
		} else {
			return this.items.get(this.switchid);
		}
	}

	@Override
	public Iterator<SwitchItem<T>> iterator() {
		return this.items.iterator();
	}
	
	protected void registerListener() {
		this.getEventManager().registerListener(ButtonPressEvent.class, (event) -> {
			if (event.isRightClick()) {
				back();

				if (event.isShiftClick()) {
					back();
				}
			} else {
				next();

				if (event.isShiftClick()) {
					next();
				}
			}
		}).addAfter();
	}
	
}
