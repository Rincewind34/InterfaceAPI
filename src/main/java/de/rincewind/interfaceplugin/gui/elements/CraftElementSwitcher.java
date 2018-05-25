package de.rincewind.interfaceplugin.gui.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.elements.ElementSwitcher;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowEditor;
import de.rincewind.interfaceapi.handling.element.ElementInteractEvent;
import de.rincewind.interfaceapi.handling.element.SwitchChangeEvent;
import de.rincewind.interfaceplugin.Validate;
import de.rincewind.interfaceplugin.gui.elements.abstracts.CraftElement;

public class CraftElementSwitcher extends CraftElement implements ElementSwitcher {

	public static String ELEMENT_INFO = "§7§oKlicke, um den Wert zu ändern...";

	private boolean elementInfoEnabled;

	private int switchIndex;

	private Icon disabledIcon;

	private final List<Displayable> items;

	public CraftElementSwitcher(WindowEditor handle) {
		super(handle);

		this.switchIndex = -1;
		this.disabledIcon = Icon.AIR;
		this.items = new ArrayList<>();

		this.getComponent(Element.ENABLED).setEnabled(true);

		this.getEventManager().registerListener(ElementInteractEvent.class, (event) -> {
			if (event.isRightClick()) {
				this.back();

				if (event.isShiftClick() && this.items.size() > 2) {
					this.back();
				}
			} else if (event.isLeftClick()) {
				this.next();

				if (event.isShiftClick() && this.items.size() > 2) {
					this.next();
				}
			}
		}).addAfter();
	}

	@Override
	public void setDisabledIcon(Displayable icon) {
		this.disabledIcon = Displayable.validate(icon);
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
	public <T> void setSwitch(T switchItem) {
		this.setSwitch(switchItem, true);
	}
	
	@Override
	public <T> void setSwitch(T switchItem, boolean fireEvent) {
		for (int index = 0; index < this.items.size(); index++) {
			if (Displayable.readPayload(this.getSwitch(index)) == switchItem) {
				this.setSwitchIndex(index, fireEvent);
				return;
			}
		}
	}

	@Override
	public int getSwitchIndex() {
		return this.switchIndex;
	}

	@Override
	public void addSwitch(Displayable item) {
		Validate.notNull(item, "The switchitem cannot be null");

		this.items.add(item);

		if (this.elementInfoEnabled && item.hasStaticIcon()) {
			item.getIcon().getLore().setEnd(CraftElementSwitcher.ELEMENT_INFO);
		}

		if (this.switchIndex == -1) {
			this.setSwitchIndex(0);
		}
	}

	@Override
	public void addSwitches(Iterable<Displayable> items) {
		Validate.notNull(items, "The iterable cannot be null");

		for (Displayable item : items) {
			this.addSwitch(item);
		}
	}

	@Override
	public void removeSwitch(Displayable item) {
		Validate.notNull(item, "The switchitem cannot be null");

		if (this.elementInfoEnabled && item.hasStaticIcon() && CraftElementSwitcher.ELEMENT_INFO.equals(item.getIcon().getLore().getEnd())) {
			item.getIcon().getLore().setEnd(null);
		}

		this.items.remove(item);
		this.setSwitchIndex(this.switchIndex);
	}

	@Override
	public void clear() {
		this.items.clear();
		this.update();
	}

	@Override
	public void setElementInfoEnabled(boolean value) {
		if (this.elementInfoEnabled == value) {
			return;
		}

		this.elementInfoEnabled = value;

		if (this.elementInfoEnabled) {
			for (Displayable displayable : this.items) {
				if (displayable.hasStaticIcon()) {
					displayable.getIcon().getLore().setEnd(CraftElementSwitcher.ELEMENT_INFO);
				}
			}
		} else {
			for (Displayable displayable : this.items) {
				if (displayable.hasStaticIcon() && CraftElementSwitcher.ELEMENT_INFO.equals(displayable.getIcon().getLore().getEnd())) {
					displayable.getIcon().getLore().setEnd(null);
				}
			}
		}
	}

	@Override
	public boolean isElementInfoEnabled() {
		// TODO Auto-generated method stub
		return false;
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
	public Displayable getCurrentSwitch() {
		if (this.size() == 0) {
			return null;
		} else {
			return this.items.get(this.switchIndex);
		}
	}

	@Override
	public Displayable getSwitch(int index) {
		return this.items.get(index);
	}
	
	@Override
	public <T> T getCurrent() {
		if (this.size() == 0) {
			return null;
		} else {
			return Displayable.readPayload(this.items.get(this.switchIndex));
		}
	}

	@Override
	public <T> T get(int index) {
		return Displayable.readPayload(this.items.get(index));
	}

	@Override
	public List<Displayable> getSwitches() {
		return Collections.unmodifiableList(this.items);
	}

	@Override
	protected Icon getIcon0(Point point) {
		if (this.isEnabled()) {
			if (this.switchIndex != -1) {
				Displayable displayable = this.items.get(this.switchIndex);

				if (this.elementInfoEnabled && !displayable.hasStaticIcon()) {
					Icon icon = displayable.getIcon();
					icon.getLore().setEnd(CraftElementSwitcher.ELEMENT_INFO);
					return icon;
				} else {
					return displayable.getIcon();
				}
			} else {
				return Icon.AIR; /* TODO Error Icon */
			}
		} else {
			return this.disabledIcon;
		}
	}

}
