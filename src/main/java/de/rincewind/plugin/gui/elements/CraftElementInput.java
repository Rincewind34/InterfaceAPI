package de.rincewind.plugin.gui.elements;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.ElementInput;
import de.rincewind.plugin.gui.elements.abstracts.CraftElementSlot;

public class CraftElementInput extends CraftElementSlot implements ElementInput {
	
	private List<ItemStack> items;
	
	public CraftElementInput(Modifyable handle) {
		super(handle);
		
		super.getBlocker().unlock();
		this.items = new ArrayList<>();
	}
	
	public boolean canInject() {
		return this.isEmpty();
	}
	
	public ItemStack inject() {
		if (!this.canInject()) {
			return null;
		} else {
			ItemStack input = this.getContent();
			
			this.items.add(input);
			this.setContent(Modifyable.EMPTY_USED_SLOT);
			
			return input;
		}
	}
	
	public ItemStack fromMemory(int index) {
		if (index < 0 || index >= this.items.size()) {
			throw new ArrayIndexOutOfBoundsException();
		} else {
			return this.items.get(index);
		}
	}
	
	public void clearMemory() {
		this.items.clear();
	}
	
	public int lastIndex() {
		return this.items.size() - 1;
	}

}
