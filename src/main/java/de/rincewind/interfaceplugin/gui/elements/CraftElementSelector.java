package de.rincewind.interfaceplugin.gui.elements;

import java.util.Objects;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.rincewind.interfaceapi.gui.elements.ElementSelector;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowEditor;
import de.rincewind.interfaceapi.handling.element.ElementInteractEvent;
import de.rincewind.interfaceapi.handling.element.ItemSelectEvent;
import de.rincewind.interfaceplugin.gui.elements.abstracts.CraftElementDisplayable;

public class CraftElementSelector extends CraftElementDisplayable implements ElementSelector {

	private boolean canUnselect;
	private boolean copyAmount;

	private ItemStack selected;

	public CraftElementSelector(WindowEditor handle) {
		super(handle);

		this.canUnselect = false;
		this.selected = null;
		
		this.setIcon(new Icon(Material.FISHING_ROD, 0, "§7§oKlicke mit einem Item auf das Icon..."));

		this.getEventManager().registerListener(ElementInteractEvent.class, (event) -> {
			if (event.getCourserItem() != null) {
				this.setSelected(event.getCourserItem());
			} else if (this.canUnselect) {
				this.setSelected(null);
			}
		}).monitor();
	}

	@Override
	public void canUnselect(boolean value) {
		this.canUnselect = value;
	}
	
	@Override
	public void copyAmount(boolean value) {
		this.copyAmount = value;
	}

	@Override
	public void setSelected(ItemStack item) {
		this.setSelected(item, true);
	}

	@Override
	public void setSelected(ItemStack item, boolean fireEvent) {
		if (Objects.equals(this.selected, item)) {
			return;
		}

		if (item != null) {
			item = item.clone();

			if (!this.copyAmount) {
				item.setAmount(1);
			}
		}

		this.selected = item;
		this.update();

		if (fireEvent) {
			this.getEventManager().callEvent(ItemSelectEvent.class, new ItemSelectEvent(this));
		}
	}
	
	@Override
	public boolean canUnselect() {
		return this.canUnselect;
	}
	
	@Override
	public boolean copyAmount() {
		return this.copyAmount;
	}

	@Override
	public ItemStack getSelected() {
		return this.selected;
	}

	@Override
	public Icon getIcon0(Point point) {
		if (this.isEnabled() && this.selected != null) {
			return new Icon(this.selected);
		} else {
			return super.getIcon0(point);
		}
	}

}
