package de.rincewind.interfaceplugin.gui.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.components.Modifyable;
import de.rincewind.interfaceapi.gui.elements.ElementSwitcher;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceapi.handling.element.ButtonPressEvent;
import de.rincewind.interfaceapi.handling.element.SwitchChangeEvent;
import de.rincewind.interfaceplugin.Validate;
import de.rincewind.interfaceplugin.gui.elements.abstracts.CraftElement;

public class CraftElementSwitcher extends CraftElement implements ElementSwitcher {
	
	private int switchIndex;
	
	private Icon disabledIcon;
	
	private List<Displayable> items;
	
	public CraftElementSwitcher(Modifyable handle) {
		super(handle);
		
		this.switchIndex = -1;
		this.disabledIcon = Icon.AIR;
		this.items = new ArrayList<>();
		
		this.getComponent(Element.ENABLED).setEnabled(true);
		
		this.registerListener();
	}
	
	@Override
	public void setDisabledIcon(Icon icon) {
		if (icon != null) {
			this.disabledIcon = icon;
		} else {
			this.disabledIcon = Icon.AIR;
		}
	}

	@Override
	public Displayable next() {
		this.setSwitchIndex(this.switchIndex + 1);
		return this.getCurrentSwitch();
	}

	@Override
	public Displayable back() {
		this.setSwitchIndex(this.switchIndex - 1);
		return this.getCurrentSwitch();
	}

	@Override
	public void setSwitchIndex(int index) {
		this.setSwitchIndex(index, true);
	}

	@Override
	public void setSwitchIndex(int index, boolean fireEvent) {
		if (this.size() == 0) {
			index = -1;
		}
		
		if (fireEvent) {
			this.getEventManager().callEvent(SwitchChangeEvent.class, new SwitchChangeEvent(this, index));
		}
		
		this.switchIndex = index;
		
		if (this.size() != 0) {
			if (this.switchIndex < 0) {
				this.switchIndex = this.size() - 1;
			} else if (this.size() >= this.switchIndex) {
				this.switchIndex = 0;
			}
		}

		this.update();
	}

	@Override
	public int getSwitchIndex() {
		return this.switchIndex;
	}

	@Override
	public void addSwitch(Displayable item) {
		Validate.notNull(item, "The switchitem cannot be null!");

		this.items.add(item);
		
		if (this.switchIndex == -1) {
			this.setSwitchIndex(0);
		}
	}

	@Override
	public void removeSwitch(Displayable item) {
		Validate.notNull(item, "The switchitem cannot be null!");

		this.items.remove(item);
		this.setSwitchIndex(this.switchIndex);
	}

	@Override
	public void clear() {
		this.items.clear();
		this.update();
	}

	@Override
	public int size() {
		return this.items.size();
	}
	
	@Override
	public Icon getDisabledIcon() {
		return this.disabledIcon;
	}

	@Override
	@SuppressWarnings(value = "unchecked")
	public <T extends Displayable> T getCurrentSwitch() {
		if (this.size() == 0) {
			return null;
		} else {
			return (T) this.items.get(this.switchIndex);
		}
	}

	@Override
	@SuppressWarnings(value = "unchecked")
	public <T extends Displayable> T getSwitch(int index) {
		return (T) this.items.get(index);
	}
	
	@Override
	public List<Displayable> getSwitches() {
		return Collections.unmodifiableList(this.items);
	}
	
	@Override
	public Icon getIcon(Point point) {
		super.getIcon(point);
		
		if (this.isEnabled()) {
			if (this.switchIndex != -1) {
				return this.items.get(this.switchIndex).getIcon();
			} else {
				return null;
			}
		} else {
			return this.disabledIcon;
		}
	}

	protected void registerListener() {
		this.getEventManager().registerListener(ButtonPressEvent.class, (event) -> {
			if (event.isRightClick()) {
				this.back();

				if (event.isShiftClick()) {
					this.back();
				}
			} else {
				this.next();

				if (event.isShiftClick()) {
					this.next();
				}
			}
		}).addAfter();
	}
	
}
