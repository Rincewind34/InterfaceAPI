package de.rincewind.interfaceplugin.gui.elements;

import java.util.Objects;
import java.util.function.UnaryOperator;

import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import de.rincewind.interfaceapi.gui.elements.ElementItemSelector;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.util.Point;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowEditor;
import de.rincewind.interfaceapi.handling.element.ElementInteractEvent;
import de.rincewind.interfaceapi.handling.element.ItemSelectEvent;
import de.rincewind.interfaceplugin.gui.elements.abstracts.CraftElementDisplayable;

public class CraftElementItemSelector extends CraftElementDisplayable implements ElementItemSelector {

	public static String INSTRUCTIONS_SELECT = "§7§lLK: §7§oItem auf Courser auswählen";
	public static String INSTRUCTIONS_UNSELECT = "§7§lLK: §7§oAusgwähltes Item abwählen";
	public static String INSTRUCTIONS_COLLECT = "§7§lMK: §7§oAusgewähltes Item aufsammeln";

	public static UnaryOperator<ItemStack> cloneFunction = (item) -> {
		return item.clone();
	};

	private boolean canUnselect;
	private boolean canCollect;
	private boolean copyAmount;
	private boolean displaySelectedItem;

	private ItemStack selected;

	public CraftElementItemSelector(WindowEditor handle) {
		super(handle);

		this.canUnselect = true;
		this.canCollect = true;
		this.selected = null;

		this.getComponent(Element.INSTRUCTIONS).setEnabled(true);

		this.setIcon(new Icon(Material.FISHING_ROD, "§eWähle ein Item aus"));

		this.getEventManager().registerListener(ElementInteractEvent.class, (event) -> {
			if (event.getClickType() == ClickType.LEFT) {
				if (event.getCourserItem() != null) {
					this.setSelected(event.getCourserItem());
				} else if (this.canUnselect) {
					this.setSelected(null);
				}
			} else if (event.getClickType() == ClickType.MIDDLE) {
				if (this.canCollect && this.selected != null && event.getCourserItem() == null) {
					event.setCourserItem(CraftElementItemSelector.cloneFunction.apply(this.selected));
				}
			}
		}).monitor();
	}

	@Override
	public void canUnselect(boolean value) {
		if (this.canUnselect != value) {
			this.canUnselect = value;
			this.update();
		}
	}

	@Override
	public void copyAmount(boolean value) {
		this.copyAmount = value;
	}

	@Override
	public void canCollect(boolean value) {
		if (this.canCollect != value) {
			this.canCollect = value;
			this.update();
		}
	}

	@Override
	public void displaySelectedItem(boolean value) {
		this.displaySelectedItem = value;
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
	public boolean canCollect() {
		return this.canCollect;
	}

	@Override
	public boolean displaySelectedItem() {
		return this.displaySelectedItem;
	}

	@Override
	public boolean isItemSelected() {
		return this.selected != null;
	}

	@Override
	public ItemStack getSelected() {
		return this.selected;
	}

	@Override
	protected String currentInstructions() {
		if (this.selected != null) {
			return (this.canUnselect ? CraftElementItemSelector.INSTRUCTIONS_UNSELECT : "") + (this.canCollect ? CraftElementItemSelector.INSTRUCTIONS_COLLECT : "");
		} else {
			return CraftElementItemSelector.INSTRUCTIONS_SELECT;
		}
	}

	@Override
	protected Icon getIcon0(Point point) {
		if (this.isEnabled() && this.selected != null && this.displaySelectedItem) {
			return new Icon(this.selected);
		}

		return super.getIcon0(point);
	}

}
