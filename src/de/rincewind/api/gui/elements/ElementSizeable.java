package de.rincewind.api.gui.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.components.Sizeable;
import de.rincewind.api.gui.elements.Element.ElementExtendable;
import de.rincewind.plugin.gui.components.CraftSizeable;

public class ElementSizeable extends ElementExtendable implements Sizeable {

	private Sizeable sizeable;
	
	/**
	 * ElementSizable: An element, which size can be customized
	 * @param handle The window for this element
	 */
	public ElementSizeable(Modifyable handle) {
		super(handle);
		
		this.sizeable = new CraftSizeable();
	}
	
	@Override
	public int getWidth() {
		return this.sizeable.getWidth();
	}

	@Override
	public int getHeigth() {
		return this.sizeable.getHeigth();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean setSize(int width, int heigth) {
		if(!this.sizeable.setSize(width, heigth)) return false;
		
		ItemStack[][] old = items.clone();
		HashMap<Integer, Map<Integer, List<InventoryAction>>> oldBlocked = (HashMap<Integer, Map<Integer, List<InventoryAction>>>) blocked.clone();
		
		super.items = new ItemStack[this.sizeable.getWidth()][this.sizeable.getHeigth()];
		super.blocked = new HashMap<Integer, Map<Integer,List<InventoryAction>>>();
		
		for(int x = 0; x < width; x++) {
			this.blocked.put(x, new HashMap<Integer, List<InventoryAction>>());
			
			for(int y = 0; y < heigth; y++) {
				this.blocked.get(x).put(y, new ArrayList<InventoryAction>());
				this.blocked.get(x).get(y).add(InventoryAction.UNKNOWN);
			}
		}
		
		for(int x = 0; x < old.length; x++) {
			for(int y = 0; y < old[x].length; y++) {
				if(this.hasItemAt(x, y)) {
					this.items[x][y] = old[x][y];
					this.blocked.get(x).get(y).clear();
					this.blocked.get(x).get(y).addAll(oldBlocked.get(x).get(y));
				}
			}
		}
		
		return true;
	}
	
	@Override
	public boolean isInside(int x, int y) {
		return this.sizeable.isInside(x, y);
	}
	
}
