package de.rincewind.plugin.gui.elements;

import lib.securebit.Validate;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.ElementButton;
import de.rincewind.api.gui.elements.ElementCounter;
import de.rincewind.api.gui.elements.util.ElementDefaults;
import de.rincewind.api.gui.elements.util.Icon;
import de.rincewind.api.gui.elements.util.Point;
import de.rincewind.api.handling.events.ButtonPressEvent;
import de.rincewind.api.handling.listener.ButtonPressListener;

public class CraftElementCounter extends CraftElementButton implements ElementCounter {

	private int count;
	
	private int minCount;
	private int maxCount;
	
	public CraftElementCounter(Modifyable handle) {
		super(handle);
		
		this.minCount = ElementDefaults.COUNTER_MINIMUM;
		this.maxCount = ElementDefaults.COUNTER_MAXIMUM;
	}
	
	@Override
	public void updateItemMap() {
		ItemStack item = this.getIcon().toItem();
		item.setAmount(this.count);
		
		this.iterate((point) -> {
			this.setItemAt(point, item);
		});
		
		this.updateItemMap(false);
	}

	@Override
	public int getCount() {
		return this.count;
	}

	@Override
	public int getMaxCount() {
		return this.maxCount;
	}

	@Override
	public int getMinCount() {
		return this.minCount;
	}

	@Override
	public void setMaxCount(int maxCount) {
		if (maxCount > 64) {
			this.setMaxCount(64);
		}
		
		//TODO use ElementCounter.MAXIMUM_COUNT
		//TODO check for smaller than 0
		
		this.maxCount = maxCount;
		this.setCount(this.count);
	}

	@Override
	public void setMinCount(int minCount) {
		if (minCount < 1) {
			this.setMinCount(1);
		}
		
		//TODO check for bigger than 64
		
		this.minCount = minCount;
		this.setCount(this.count);
	}

	@Override
	public void setCount(int count) {
		if (count > this.maxCount) {
			this.setCount(this.maxCount);
			return;
		}
		
		if (count < this.minCount) {
			this.setCount(this.minCount);
			return;
		}
		
		this.count = count;
		this.getHandle().readItemsFrom(this);
	}
	
	@Override
	public void increment() {
		this.setCount(this.count + 1);
	}

	@Override
	public void countdown() {
		this.setCount(this.count - 1);
	}

	@Override
	public void addIncrementer(ElementButton btn, int value) {
		Validate.notNull(btn, "The button cannot be null!");
		
		//TODO Exception if value is 0
		
		btn.getEventManager().registerListener(new ActionHandler(value)).addAfter();
	}
	
	@Override
	public void addIncrementer(ElementButton btn, int value, Point point) {
		btn.setIcon(new Icon(Material.ARROW, 0, (value < 0 ? "§c- " : "§a+ ") + "§7" + Math.abs(value) + " " + this.getIcon().getName()));
		btn.setPoint(point);
		
		this.addIncrementer(btn, value);
	}
	
	
	private class ActionHandler extends ButtonPressListener {

		private int value;
		
		private ActionHandler(int value) {
			this.value = value;
		}
		
		@Override
		public void onFire(ButtonPressEvent event) {
			CraftElementCounter.this.setCount(CraftElementCounter.this.getCount() + this.value * (event.isShiftClick() ? 2 : 1));
		}
		
	}

}
