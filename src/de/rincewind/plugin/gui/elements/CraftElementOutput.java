package de.rincewind.plugin.gui.elements;

import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.ClickAction;
import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.ElementOutput;
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
		if (!flag && !this.isEmpty()){
			return;
		} else {
			this.setContent(item);
		}
	}
	
	@Override
	public void onAdd() {
		
	}

}
