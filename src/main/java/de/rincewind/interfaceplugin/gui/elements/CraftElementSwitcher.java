package de.rincewind.interfaceplugin.gui.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.components.DisplayableDisabled;
import de.rincewind.interfaceapi.gui.elements.ElementSwitcher;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.util.Point;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowEditor;
import de.rincewind.interfaceapi.handling.element.ElementInteractEvent;
import de.rincewind.interfaceapi.handling.element.SwitchChangeEvent;
import de.rincewind.interfaceplugin.Validate;
import de.rincewind.interfaceplugin.gui.elements.abstracts.CraftElement;

public class CraftElementSwitcher extends CraftElement implements ElementSwitcher {

	public static String DEFAULT_INSTRUCTIONS = "§7§lLK: §7§oNächster Wert \\n§7§lRK: §7§oVorheriger Wert\\n§7§l+S: §7§oEinen Wert überspringen";

	private int switchIndex;

	private Displayable disabledIcon;

	private final List<Displayable> items;

	public CraftElementSwitcher(WindowEditor handle) {
		super(handle);

		this.switchIndex = -1;
		this.disabledIcon = DisplayableDisabled.default_icon;
		this.items = new ArrayList<>();

		this.getComponent(Element.ENABLED).setEnabled(true);
		this.getComponent(Element.INSTRUCTIONS).setEnabled(true);

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
		this.disabledIcon = Displayable.checkNull(icon);

		if (!this.isEnabled()) {
			this.update();
		}
	}

	@Override
	public Displayable next() {
		this.setSwitchIndex(this.switchIndex + 1, true);
		return this.getCurrentSwitch();
	}

	@Override
	public Displayable back() {
		this.setSwitchIndex(this.switchIndex - 1, true);
		return this.getCurrentSwitch();
	}

	@Override
	public void setSwitchIndex(int index) {
		this.setSwitchIndex(index, true);
	}

	@Override
	public void setSwitchIndex(int index, boolean fireEvent) {
		if (this.switchIndex == index || this.size() == 0) {
			return;
		}

		int previousIndex = this.switchIndex;
		this.switchIndex = index;

		if (this.size() != 0) {
			if (this.switchIndex < 0) {
				this.switchIndex = this.size() - 1;
			} else if (this.switchIndex >= this.size()) {
				this.switchIndex = 0;
			}
		}
		
		if (fireEvent) {
			this.getEventManager().callEvent(SwitchChangeEvent.class, new SwitchChangeEvent(this, previousIndex, this.switchIndex));
		}

		this.update();
	}

	@Override
	public void setSwitch(Object switchItem) {
		this.setSwitch(switchItem, true);
	}

	@Override
	public void setSwitch(Object switchItem, boolean fireEvent) {
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

		if (this.items.contains(item)) {
			return;
		}

		this.items.add(item);

		if (this.switchIndex == -1) {
			this.setSwitchIndex(0, true);
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

		if (this.items.contains(item)) {
			if (item.hasStaticIcon()) {
				item.getIcon().getLore().setEnd(null);
			}

			this.items.remove(item);
			this.setSwitchIndex(this.switchIndex);
		}
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
		return this.disabledIcon.getIcon();
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
				return this.updateInstructions(this.items.get(this.switchIndex).getIcon(), CraftElementSwitcher.DEFAULT_INSTRUCTIONS);
			} else {
				return Icon.AIR; /* TODO Error Icon */
			}
		} else {
			return this.getDisabledIcon();
		}
	}

}
