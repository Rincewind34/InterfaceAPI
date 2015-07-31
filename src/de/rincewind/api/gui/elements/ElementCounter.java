package de.rincewind.api.gui.elements;

import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.components.Modifyable;

public class ElementCounter extends ElementSwitcher {

	private int count;
	
	private int minCount;
	private int maxCount;
	
	/**
	 * 
	 * @param handle The window for this element
	 */
	public ElementCounter(Modifyable handle) {
		super(handle);
		
		this.minCount = 1;
		this.maxCount = 10;
	}
	
	@Override
	public void setIcon(ItemStack icon) {
		super.setIcon(icon);
		this.configurateItems();
	}
	
	@Override
	public void next() {
		if (super.getSwitchid() == this.maxCount - this.minCount) {
			return;
		}
		super.next();
	}
	
	@Override
	public void back() {
		if (super.getSwitchid() == 0) {
			return;
		}
		super.back();
	}
	
	/**
	 * 
	 * @return The current count
	 */
	public int getCount() {
		return this.count;
	}
	
	/**
	 * 
	 * @return The maximum count
	 */
	public int getMaxCount() {
		return this.maxCount;
	}
	
	/**
	 * 
	 * @return The minimum count
	 */
	public int getMinCount() {
		return this.minCount;
	}
	
	/**
	 * Sets a maximum count
	 * 
	 * @param maxCount The new maximum count
	 */
	public void setMaxCount(int maxCount) {
		if(maxCount > 64) {
			this.setMaxCount(64);
		}
		
		this.maxCount = maxCount;
		this.configurateItems();
		this.setCount(this.count);
	}
	
	/**
	 * Sets a minimum count
	 * 
	 * @param minCount The new minimum count
	 */
	public void setMinCount(int minCount) {
		if(minCount < 1) {
			this.setMinCount(1);
		}
		
		this.minCount = minCount;
		this.configurateItems();
		this.setCount(this.count);
	}
	
	/**
	 * Sets a current count of this element
	 * 
	 * @param count the target count
	 */
	public void setCount(int count) {
		if(count > this.maxCount) {
			this.setCount(count - 1);
		}
		
		if(count < this.minCount) {
			this.setCount(count + 1);
		}
		
		this.count = count;
		super.setSwitchId(count - this.minCount);
	}
	
	private void configurateItems() {
		super.clear();
		
		for (int i = this.minCount; i <= this.maxCount; i++) {
			ItemStack item = super.getIcon().clone();
			item.setAmount(i);
			super.addSwitch(item);
		}
	}

}
