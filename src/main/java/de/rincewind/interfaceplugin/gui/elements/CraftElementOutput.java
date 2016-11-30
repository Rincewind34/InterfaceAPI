package de.rincewind.interfaceplugin.gui.elements;

import org.bukkit.inventory.ItemStack;

import de.rincewind.interfaceapi.gui.components.Modifyable;
import de.rincewind.interfaceapi.gui.elements.ElementOutput;
import de.rincewind.interfaceapi.gui.elements.util.ClickAction;
import de.rincewind.interfaceapi.handling.element.ElementInteractEvent;
import de.rincewind.interfaceapi.handling.element.OutputConsumeEvent;
import de.rincewind.interfaceplugin.Validate;
import de.rincewind.interfaceplugin.gui.elements.abstracts.CraftElementSlot;

public class CraftElementOutput extends CraftElementSlot implements ElementOutput {
	
	private boolean empty;
	
	public CraftElementOutput(Modifyable handle) {
		super(handle);
		
		this.empty = true;
		
		this.getBlocker().unlock();
		this.getBlocker().addAction(ClickAction.PLACE);
		
		this.getEventManager().registerListener(ElementInteractEvent.class, (event) -> {
			if (!this.empty && this.isEmpty()) {
				this.empty = true;
				this.getEventManager().callEvent(OutputConsumeEvent.class, new OutputConsumeEvent(this, event.getPlayer()));
			}
		}).addAfter();
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
			this.empty = false;
		}
	}
	
}
