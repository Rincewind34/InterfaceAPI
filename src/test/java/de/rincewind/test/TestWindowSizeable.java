package de.rincewind.test;

import org.bukkit.inventory.Inventory;

import de.rincewind.interfaceplugin.gui.windows.CraftWindowSizeable;

public class TestWindowSizeable extends CraftWindowSizeable {

	private Inventory reference;

	public TestWindowSizeable() {
		super(null);
	}

	@Override
	public Inventory newInventory() {
		return this.reference = new TestInventory(this.getName(), this.getWidth() * this.getHeight());
	}

	public Inventory getReference() {
		return this.reference;
	}
	
}
