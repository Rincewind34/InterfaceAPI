package de.rincewind.plugin.gui.elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.ElementMultiButton;
import de.rincewind.api.gui.elements.util.ElementDefaults;
import de.rincewind.api.gui.elements.util.Icon;
import de.rincewind.api.handling.events.ButtonPressEvent;
import de.rincewind.api.handling.events.MultiButtonPressEvent;
import de.rincewind.api.item.ItemLibary;
import de.rincewind.api.item.ItemModifier;
import de.rincewind.api.item.ItemRefactor.Lore;
import lib.securebit.Validate;

public class CraftElementMultiButton extends CraftElementButton implements ElementMultiButton {

	private String defaultFormat;
	private String selectFormat;

	private ItemModifier selectModifier;

	private List<String> list;

	private int switchid;

	private boolean selected;

	private Icon iconSave;

	public CraftElementMultiButton(Modifyable handle) {
		super(handle);

		this.defaultFormat = ElementDefaults.MULTI_BUTTON_DEFAULT_FORMAT;
		this.selectFormat = ElementDefaults.MULTI_BUTTON_SELECTED_FORMAT;
		this.selected = false;
		this.list = new ArrayList<>();

		this.selectModifier = (item) -> {
			return ItemLibary.refactor().enchantItem(item, Enchantment.WATER_WORKER, 1, false);
		};

		this.registerListener();
	}

	@Override
	public void setIcon(Icon icon) {
		if (this.isSelected()) {
			Lore lore = new Lore();

			for (int i = 0; i < this.size(); i++) {
				if (i == this.switchid) {
					lore.add(String.format(this.selectFormat, this.list.get(i)));
				} else {
					lore.add(String.format(this.defaultFormat, this.list.get(i)));
				}
			}

			icon.describe(lore);
		} else {
			this.iconSave = icon;
		}

		super.setIcon(icon);
	}

	@Override
	public String next() {
		this.setSwitchId(this.switchid + 1); // TODO check, if this element is
												// selected
		return this.getSwitch();
	}

	@Override
	public String back() {
		this.setSwitchId(this.switchid - 1); // TODO check, if this element is
												// selected
		return this.getSwitch();
	}

	@Override
	public String getSwitch() {
		return this.list.get(this.switchid); // TODO check, if this element is
												// selected
	}

	@Override
	public void setSwitchId(int id) {
		this.switchid = id; // TODO check, if this element is selected

		if (this.switchid == -2) {
			if (this.isSelected()) {
				this.unselect();
			}
		} else {
			if (!this.isSelected()) {
				this.select();
			}

			if (this.switchid < 0) {
				this.switchid = this.size() - 1; // TODO check the frame at both
													// ends
			} else if (this.size() == this.switchid) {
				this.switchid = 0;
			}

			this.setIcon(this.getIcon());
		}

		this.getHandle().readItemsFrom(this);
	}

	@Override
	public void addSwitch(String item) {
		this.list.add(item);
		this.getHandle().readItemsFrom(this);
	}

	@Override
	public void removeSwitch(String item) {
		this.list.remove(item);
		this.setSwitchId(this.switchid);
	}

	@Override
	public void clear() {
		this.list.clear(); // TODO throw exception if the element is selected
	}

	@Override
	public int size() {
		return this.list.size();
	}

	@Override
	public int getSwitchId() {
		return this.switchid;
	}

	@Override
	public Iterator<String> iterator() {
		return this.list.iterator();
	}

	@Override
	public void select() {
		this.selected = true; // TODO throw exception is the entrylist is empty
		this.setSwitchId(0);
		this.setIcon(new Icon(this.modify(this.getIcon().toItem())));
	}

	@Override
	public void unselect() {
		this.selected = false; // TODO check, if this element is selected
		this.setSwitchId(-2);
		this.setIcon(this.iconSave);
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
	public void setSelectModifier(ItemModifier modifier) {
		Validate.notNull(modifier, "The modifier cannot be null!");

		this.selectModifier = modifier;
	}

	@Override
	public ItemStack modify(ItemStack item) {
		Validate.notNull(item, "The item cannot be null!");

		return this.selectModifier.modifyItem(item);
	}

	protected void registerListener() {
		this.getEventManager().registerListener(ButtonPressEvent.class, (event) -> {
			if (event.isRightClick()) {
				next();
			} else {
				if (!CraftElementMultiButton.this.isSelected()) {
					CraftElementMultiButton.this.select();
				} else {
					CraftElementMultiButton.this.getEventManager().callEvent(MultiButtonPressEvent.class,
							new MultiButtonPressEvent(CraftElementMultiButton.this));
				}
			}
		}).addAfter();
	}

}
