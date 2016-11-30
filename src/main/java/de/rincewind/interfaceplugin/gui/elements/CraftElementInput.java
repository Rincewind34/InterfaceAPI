package de.rincewind.interfaceplugin.gui.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import de.rincewind.interfaceapi.gui.components.Modifyable;
import de.rincewind.interfaceapi.gui.elements.ElementInput;
import de.rincewind.interfaceplugin.gui.elements.abstracts.CraftElementSlot;

public class CraftElementInput extends CraftElementSlot implements ElementInput {
	
	private List<ItemStack> items;
	
	public CraftElementInput(Modifyable handle) {
		super(handle);
		
		super.getBlocker().unlock();
		this.items = new ArrayList<>();
	}
	
	@Override
	public void clearMemory() {
		this.items.clear();
	}
	
	@Override
	public ItemStack inject() {
		if (!this.isEmpty()) {
			return null;
		} else {
			ItemStack input = this.getContent();
			
			this.items.add(input);
			this.setContent(Modifyable.EMPTY_USED_SLOT);
			
			return input;
		}
	}

	@Override
	public List<ItemStack> getMemory() {
		return Collections.unmodifiableList(this.items);
	}
	
}
