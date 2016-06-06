package de.rincewind.plugin.gui.elements;

import lib.securebit.Validate;

import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.ElementOutput;
import de.rincewind.api.gui.elements.util.ClickAction;
import de.rincewind.plugin.gui.elements.abstracts.CraftElementSlot;

public class CraftElementOutput extends CraftElementSlot implements ElementOutput {

	public CraftElementOutput(Modifyable handle) {
		super(handle);
		
		this.getBlocker().unlock();
		this.getBlocker().addAction(ClickAction.PLACE);
	}
	
	@Override
	public void output(ItemStack item) {
		this.output(item, false);
	}
	
	@Override
	public void output(ItemStack item, boolean flag) {
		Validate.notNull(item, "The item cannot be null!");
		
		if (!flag && !this.isEmpty()){
			return;
		} else {
			this.setContent(item);
		}
	}
	
}
