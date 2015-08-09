package de.rincewind.plugin.gui.elements;

import org.bukkit.inventory.ItemStack;

import de.rincewind.api.events.ButtonPressEvent;
import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.ElementButton;
import de.rincewind.api.gui.elements.ElementCounter;
import de.rincewind.api.listener.ButtonPressListener;

public class CraftElementCounter extends CraftElementButton implements ElementCounter {

	private int count;
	
	private int minCount;
	private int maxCount;
	
	public CraftElementCounter(Modifyable handle) {
		super(handle);
		
		this.minCount = 1;
		this.maxCount = 10;
	}
	
	@Override
	public void updateItemMap() {
		ItemStack item = this.getIcon();
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
		if(maxCount > 64) {
			this.setMaxCount(64);
		}
		
		this.maxCount = maxCount;
		this.setCount(this.count);
	}

	@Override
	public void setMinCount(int minCount) {
		if(minCount < 1) {
			this.setMinCount(1);
		}
		
		this.minCount = minCount;
		this.setCount(this.count);
	}

	@Override
	public void setCount(int count) {
		if(count > this.maxCount) {
			this.setCount(this.maxCount);
			return;
		}
		
		if(count < this.minCount) {
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
		btn.getEventManager().addListener(new ActionHandler(value));
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
