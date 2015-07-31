package de.rincewind.api.gui.elements;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.components.Modifyable;

public class ElementInput extends ElementSlot {
	
	private List<ItemStack> items;
	
	/**
	 * 
	 * @param handle The window for this element
	 */
	public ElementInput(Modifyable handle) {
		super(handle);
		
		super.setBlocked(getX(), getY(), false);
		
		this.items = new ArrayList<ItemStack>();
	}
	
	/**
	 * 
	 * @return If this element can remove an item out of the target slot.
	 */
	public boolean canInject() {
		return super.isEmpty();
	}
	
	/**
	 * Injects the item laing in the target slot
	 * 
	 * @return The injected item
	 */
	public ItemStack inject() {
		if (!this.canInject()) {
			return null;
		} else {
			ItemStack input = super.getItem();
			this.items.add(input);
			super.sendItem(new ItemStack(Material.AIR));
			return input;
		}
	}
	
	/**
	 * Gets an injected item out of the memory
	 * 
	 * @param index The scanindex (0 is the oldest scan)
	 * @return the Injected item
	 */
	public ItemStack fromMemory(int index) {
		if (index < 0 || index >= this.items.size()) {
			throw new ArrayIndexOutOfBoundsException();
		} else {
			return this.items.get(index);
		}
	}
	
	/**
	 * Clears the memory of this element
	 */
	public void clearMemory() {
		this.items.clear();
	}
	
	/**
	 * 
	 * @return The last scanindex of this element
	 */
	public int lastIndex() {
		return this.items.size() - 1;
	}

}
