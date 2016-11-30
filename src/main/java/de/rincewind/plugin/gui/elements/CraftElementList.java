package de.rincewind.plugin.gui.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.bukkit.Material;

import de.rincewind.api.gui.components.Displayable;
import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.ElementButton;
import de.rincewind.api.gui.elements.ElementList;
import de.rincewind.api.gui.elements.abstracts.Element;
import de.rincewind.api.gui.elements.util.Icon;
import de.rincewind.api.gui.elements.util.Point;
import de.rincewind.api.gui.util.Color;
import de.rincewind.api.gui.util.Directionality;
import de.rincewind.api.handling.InterfaceListener;
import de.rincewind.api.handling.events.ButtonPressEvent;
import de.rincewind.api.handling.events.ElementInteractEvent;
import de.rincewind.api.handling.events.ListChangeSelectEvent;
import de.rincewind.plugin.gui.elements.abstracts.CraftElement;
import lib.securebit.Validate;

public class CraftElementList extends CraftElement implements ElementList {

	private Color color;
	
	private int selected;
	private int startIndex;
	
	private Icon disabledIcon;
	
	private Directionality type;
	
	private List<Displayable> items;
	private Function<Icon, Icon> modifier;
	
	public CraftElementList(Modifyable handle) {
		super(handle);
		
		this.selected = -1;
		this.startIndex = 0;
		this.type = Directionality.VERTICAL;
		this.color = Color.TRANSLUCENT;
		this.disabledIcon = Icon.AIR;
		this.items = new ArrayList<>();
		
		this.modifier = (icon) -> { 
			return icon.typecast(Material.STAINED_GLASS).damage(2);
		};
		
		this.getComponent(Element.ENABLED).setEnabled(true);
		this.getComponent(Element.WIDTH).setEnabled(true);
		this.getComponent(Element.HEIGHT).setEnabled(true);
		
		this.getEventManager().registerListener(ElementInteractEvent.class, (event) -> {
			int index;
			
			if (this.type == Directionality.HORIZONTAL) {
				index = event.getPoint().getY();
			} else {
				index = event.getPoint().getX();
			}
			
			index = index + this.startIndex;
			
			if (index == this.getSelectedIndex()) {
				this.unselect();
			} else if (index < this.getSize()) {
				this.select(index);
			}
		}).addAfter();
	}
	
	@Override
	public void setDisabledIcon(Icon icon) {
		this.disabledIcon = icon;
	}

	@Override
	public Icon getDisabledIcon() {
		return this.disabledIcon;
	}
	
	@Override
	public void setColor(Color color) {
		this.color = color;
	}
	
	@Override
	public void setSelectModifyer(Function<Icon, Icon> modifier) {
		Validate.notNull(modifier, "The modifier cannot be null!");
		
		this.modifier = modifier;
	}
	
	@Override
	public Icon modifyToSelect(Icon item) {
		Validate.notNull(item, "The item cannot be null!");
		
		return this.modifier.apply(item);
	}

	@Override
	public Color getColor() {
		return this.color;
	}

	@Override
	public void addItem(Displayable item) {
		Validate.notNull(item, "The item cannot be null!");
		
		this.items.add(item);
		this.update();
	}

	@Override
	public void removeItem(Displayable item) {
		Validate.notNull(item, "The item cannot be null!");
		
		this.items.remove(item);
		this.update();
	}

	@Override
	public void setType(Directionality type) {
		Validate.notNull(type, "The type cannot be null!");
		
		this.type = type;
		this.update();
	}

	@Override
	public void setStartIndex(int index) {
		if (0 > index || index > this.getSize() - 1) {
			return;
		}
		
		this.startIndex = index;
		this.update();
	}
	
	@Override
	public boolean canSelect() {
		return this.getSize() > 0;
	}
	
	@Override
	public int getStartIndex() {
		return this.startIndex;
	}

	@Override
	public int getSelectedIndex() {
		return this.selected;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends Displayable> T getSelected() {
		if (!this.isSelected()) {
			return null;
		} else {
			return (T) this.items.get(this.selected);
		}
	}


	@Override
	@SuppressWarnings("unchecked")
	public <T extends Displayable> T getItem(int index) {
		return (T) this.items.get(index);
	}
	
	@Override
	public void select(int index) {
		this.select(index, true);
	}
	
	@Override
	public void select(int index, boolean fireEvent) {
		if (fireEvent) {
			this.getEventManager().callEvent(ListChangeSelectEvent.class, new ListChangeSelectEvent(this, index));
		}
		
		this.selected = index;
		this.update();
	}

	@Override
	public void unselect() {
		this.unselect(true);
	}
	
	@Override
	public void unselect(boolean fireEvent) {
		this.select(-1, fireEvent);
	}

	@Override
	public List<Displayable> getItems() {
		return Collections.unmodifiableList(this.items);
	}
	
	@Override
	public void addScroler(ElementButton btn, int value) {
		Validate.notNull(btn, "The button cannot be null!");
		
		if (value == 0) {
			throw new RuntimeException("The value cannot be zero!");
		}
		
		btn.getEventManager().registerListener(ButtonPressEvent.class, new ActionHandler(value)).addAfter();
	}
	
	@Override
	public boolean isSelected() {
		return this.selected >= 0;
	}
	
	@Override
	public Icon getIcon(Point point) {
		if (!this.isEnabled()) {
			return this.disabledIcon;
		}
		
		int offset;
		
		if (this.type == Directionality.HORIZONTAL) {
			offset = point.getY();
		} else {
			offset = point.getX();
		}
		
		int index = this.startIndex + offset;
		
		if (index >= this.getSize()) {
			return this.color.asIcon();
		} else if (index == this.selected) {
			return this.modifier.apply(this.items.get(index).getIcon());
		} else {
			return this.items.get(index).getIcon();
		}
	}
	
	
	private class ActionHandler implements InterfaceListener<ButtonPressEvent> {

		private int value;
		
		private ActionHandler(int value) {
			this.value = value;
		}
		
		@Override
		public void onAction(ButtonPressEvent event) {
			CraftElementList.this.setStartIndex(CraftElementList.this.getStartIndex() + (this.value * (event.isShiftClick() ? 2 : 1)));
		}
		
	}

}
