package de.rincewind.test;

import org.bukkit.inventory.Inventory;

import de.rincewind.interfaceplugin.ReflectionUtil;
import de.rincewind.interfaceplugin.gui.windows.CraftWindowSizeable;
import de.rincewind.interfaceplugin.gui.windows.abstracts.CraftWindowContainer;

public class TestWindowSizeable extends CraftWindowSizeable {

	private Inventory reference;

	public TestWindowSizeable() {
		this.reference = (Inventory) ReflectionUtil.createObject(ReflectionUtil.getDeclaredField(CraftWindowContainer.class, "inventory"), this);
	}

	@Override
	public Inventory newInventory() {
		return new TestInventory(this.getName(), this.getWidth() * this.getHeight());
	}

	public Inventory getReference() {
		return this.reference;
	}

}
