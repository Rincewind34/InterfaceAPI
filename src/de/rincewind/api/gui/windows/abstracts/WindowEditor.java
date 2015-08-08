package de.rincewind.api.gui.windows.abstracts;

import java.util.List;

import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.abstracts.Element;
import de.rincewind.plugin.gui.components.CraftModifyable;

public abstract class WindowEditor extends WindowContainer implements Modifyable {
	
	private Modifyable modifyable;
	
	/**
	 * WindowEditor: Allows to add/remove elements from the window
	 */
	public WindowEditor() {
		super();
		this.modifyable = new CraftModifyable();
	}
	
	@Override
	public List<Element> getElements() {
		return this.modifyable.getElements();
	}

	@Override
	public boolean hasSpaceAt(int x, int y) {
		return this.modifyable.hasSpaceAt(x, y);
	}

	@Override
	public int addElement(Element element) {
		int id = this.modifyable.addElement(element);
		this.updateBukkitInventory();
		return id;
	}
	
	@Override
	public ItemStack getItemAt(int x, int y) {
		return this.modifyable.getItemAt(x, y);
	}

	@Override
	public Element getElementAt(int x, int y) {
		return this.modifyable.getElementAt(x, y);
	}

	@Override
	public void updateItemMap() {
		this.modifyable.updateItemMap();
	}

	@Override
	public void updateItemMap(Element element) {
		this.modifyable.updateItemMap(element);
		this.updateBukkitInventory();
	}

	@Override
	public void updateItemMap(int id) {
		this.modifyable.updateItemMap(id);
	}
	
	public abstract int getPositionX(int slot);

	public abstract int getPositionY(int slot);
	
}
