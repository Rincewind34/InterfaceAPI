package de.rincewind.interfaceplugin.gui.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.bukkit.Material;

import de.rincewind.interfaceapi.gui.elements.ElementMultiButton;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.elements.util.Lore;
import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowEditor;
import de.rincewind.interfaceapi.handling.element.ElementInteractEvent;
import de.rincewind.interfaceapi.handling.element.MultiButtonPressEvent;
import de.rincewind.interfaceplugin.Validate;

public class CraftElementMultiButton extends CraftElementItem implements ElementMultiButton {

	private boolean selected;

	private int selectedIndex;

	private String defaultFormat;
	private String selectFormat;

	private List<Object> list;

	private Function<Icon, Icon> selectModifier;

	public CraftElementMultiButton(WindowEditor handle) {
		super(handle);

		this.defaultFormat = "§f%s";
		this.selectFormat = "§5%s";
		this.selected = false;
		this.list = new ArrayList<>();

		this.selectModifier = (icon) -> {
			return icon.typecast(Material.STAINED_GLASS).damage(2);
		};

		this.registerListener();
		
		this.getComponent(Element.WIDTH).setEnabled(true);
		this.getComponent(Element.HEIGHT).setEnabled(true);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T next() {
		if (!this.isSelected()) {
			throw new RuntimeException("This element is not selected!");
		}
		
		this.setSelectedIndex(this.selectedIndex + 1);
		return (T) this.getSelected();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T back() {
		if (!this.isSelected()) {
			throw new RuntimeException("This element is not selected!");
		}
		
		this.setSelectedIndex(this.selectedIndex - 1);
		return (T) this.getSelected();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getSelected() {
		if (!this.isSelected()) {
			throw new RuntimeException("This element is not selected!");
		}
		
		return (T) this.list.get(this.selectedIndex);
	}

	@Override
	public void setSelectedIndex(int index) {
		if (!this.isSelected()) {
			throw new RuntimeException("This element is not selected!");
		}
		
		this.selectedIndex = index;

		if (this.selectedIndex == -2 && this.isSelected()) {
			this.deselect();
		} else {
			if (!this.isSelected()) {
				this.select();
			}

			if (this.selectedIndex < 0) {
				this.selectedIndex = this.size() - 1;
			} else if (this.size() >= this.selectedIndex) {
				this.selectedIndex = 0;
			}
		}

		this.update();
	}

	@Override
	public void addSwitch(Object item) {
		this.list.add(item);
		this.update();
	}

	@Override
	public void removeSwitch(Object item) {
		this.list.remove(item);
		this.setSelectedIndex(this.selectedIndex);
	}

	@Override
	public void clear() {
		if (this.isSelected()) {
			throw new RuntimeException("This element is selected!");
		}
		
		this.list.clear();
	}
	
	@Override
	public boolean canSelect() {
		return this.list.size() > 0;
	}

	@Override
	public int size() {
		return this.list.size();
	}

	@Override
	public int getSelectedIndex() {
		return this.selectedIndex;
	}

	@Override
	public void select() {
		if (this.isSelected()) {
			throw new RuntimeException("This element is selected!");
		}
		
		if (this.list.isEmpty()) {
			throw new RuntimeException("The switch list is empty!");
		}
		
		this.selected = true;
		this.setSelectedIndex(0);
		this.update();
	}

	@Override
	public void deselect() {
		if (!this.isSelected()) {
			throw new RuntimeException("This element is not selected!");
		}
		
		this.selected = false;
		this.setSelectedIndex(-2);
		this.update();
	}

	@Override
	public boolean isSelected() {
		return this.selected;
	}

	@Override
	public void setDefaultFormat(String str) {
		Validate.notNull(str, "The format cannot be null!");

		this.defaultFormat = str;
	}

	@Override
	public void setSelectFormat(String str) {
		Validate.notNull(str, "The format cannot be null!");

		this.selectFormat = str;
	}

	@Override
	public void setSelectModifier(Function<Icon, Icon> modifier) {
		Validate.notNull(modifier, "The modifier cannot be null!");

		this.selectModifier = modifier;
	}

	@Override
	public Icon modify(Icon icon) {
		Validate.notNull(icon, "The icon cannot be null!");

		return this.selectModifier.apply(icon);
	}
	
	@Override
	public List<Object> getSwitches() {
		return Collections.unmodifiableList(this.list);
	}

	@Override
	protected Icon getIcon0(Point point) {
		Icon icon = super.getIcon(point).clone();

		if (this.isEnabled() && this.isSelected()) {
			Lore lore = new Lore();

			for (int i = 0; i < this.size(); i++) {
				if (i == this.selectedIndex) {
					lore.add(String.format(this.selectFormat, this.list.get(i).toString()));
				} else {
					lore.add(String.format(this.defaultFormat, this.list.get(i).toString()));
				}
			}

			icon.describe(lore);
			return icon;
		} else {
			return icon;
		}
	}

	protected void registerListener() {
		this.getEventManager().registerListener(ElementInteractEvent.class, (event) -> {
			if (!event.isShiftClick()) {
				if (!this.isSelected()) {
					this.select();
					return;
				}

				if (event.isLeftClick()) {
					this.getEventManager().callEvent(MultiButtonPressEvent.class, new MultiButtonPressEvent(this, event.getPlayer()));
				} else {
					this.next();
				}
			} else {
				if (this.isSelected()) {
					this.deselect();
				}
			}
		}).addAfter();
	}

}
