package de.rincewind.interfaceplugin.gui.elements;

import java.util.function.Predicate;

import org.bukkit.inventory.ItemStack;

import de.rincewind.interfaceapi.gui.elements.ElementSelector;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceapi.gui.windows.abstracts.Window;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowEditor;
import de.rincewind.interfaceapi.handling.element.ElementInteractEvent;
import de.rincewind.interfaceapi.handling.element.ItemSelectEvent;
import de.rincewind.interfaceapi.handling.window.WindowClickEvent;
import de.rincewind.interfaceplugin.gui.elements.abstracts.CraftElementDisplayable;

public class CraftElementSelector extends CraftElementDisplayable implements ElementSelector {
	
	private boolean canUnselect;
	private boolean isPullingOnlyOne;

	private ItemStack selected;

	private Predicate<ItemStack> selector;

	public CraftElementSelector(WindowEditor handle) {
		super(handle);

		this.canUnselect = false;
		this.isPullingOnlyOne = false;
		this.selected = null;
		this.selector = (item) -> {
			return true;
		};
		
		this.getComponent(Element.ENABLED).setEnabled(true);
		
		this.getEventManager().registerListener(ElementInteractEvent.class, (event) -> {
			if (this.canUnselect) {
				this.setSelected(null);
			}
		}).addAfter();
	}

	@Override
	public void pullOnlyOne(boolean value) {
		this.isPullingOnlyOne = value;
	}

	@Override
	public void canUnselect(boolean value) {
		this.canUnselect = value;
	}

	@Override
	public void setSelected(ItemStack item) {
		this.setSelected(item, true);
	}

	@Override
	public void setSelected(ItemStack item, boolean fireEvent) {
		if (item != null && this.isPullingOnlyOne && item.getAmount() > 1) {
			ItemStack select = item.clone();
			select.setAmount(1);
			this.setSelected(select);
		}

		this.selected = item;
		this.update();

		if (fireEvent) {
			this.getEventManager().callEvent(ItemSelectEvent.class, new ItemSelectEvent(this));
		}
	}

	@Override
	public void registerTarget(Window window) {
		window.getEventManager().registerListener(WindowClickEvent.class, (event) -> {
			if (this.isEnabled()) {
				if (!event.isInInterface() && event.getItem() != null && this.selector.test(event.getItem())) {
					this.setSelected(event.getItem());
					event.cancelInteraction();
				}
			}
		}).addAfter();
	}

	@Override
	public void setSelector(Predicate<ItemStack> selector) {
		this.selector = selector;
	}

	@Override
	public boolean isPullingOnlyOne() {
		return this.isPullingOnlyOne;
	}

	@Override
	public boolean canUnselect() {
		return this.canUnselect;
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
