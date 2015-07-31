package de.rincewind.plugin.gui.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.Element;
import de.rincewind.api.gui.elements.ElementSizeable;
import de.rincewind.plugin.gui.CraftElement;

public final class CraftModifyable implements Modifyable {
	
	private Map<Integer, Element> elements;
	
	private Map<Integer, Map<Integer, ItemStack>> items;
	
	public CraftModifyable() {
		this.elements = new HashMap<Integer, Element>();
		this.items = new HashMap<Integer, Map<Integer,ItemStack>>();
	}
	
	@Override
	public List<Element> getElements() {
		List<Element> result = new ArrayList<Element>();
		
		for (Element e : this.elements.values()) {
			result.add(e);
		}
		
		return result;
	}

	@Override
	public boolean hasSpaceAt(int x, int y) {
		return !this.hasItem(x, y);
	}
	
	@Override
	public int addElement(Element element) {
		if (((CraftElement) element).getId() != -1) {
			return -1;
		}
		
		int id = 0;
		
		while (this.elements.containsKey(id)) {
			id++; // Sucht eine freie id von 0 aufwaerts
		}
		
		this.elements.put(id, element);
		element.onAdd();
		this.updateItemMap(element);
		
		((CraftElement) element).setId(id);
		return id;
	}


	@Override
	public ItemStack getItemAt(int x, int y) {
		if (!this.hasItem(x, y)) {
			return null;
		} else{
			return items.get(x).get(y);
		}
	}
	
	@Override
	public Element getElementAt(int x, int y) {
		if (this.hasSpaceAt(x, y)) {
			return null;
		}
		
		for (Element element : this.elements.values()) {
			if (element.getX() <= x && x < element.getX() + (element instanceof ElementSizeable ? ((ElementSizeable) element).getWidth() : 1)) {
				if (element.getY() <= y && y < element.getY() + (element instanceof ElementSizeable ? ((ElementSizeable) element).getHeigth() : 1)){
					return element;
				}
			}
		}
		
		return null;
	}

	@Override
	public void updateItemMap() {
		for (Integer id : this.elements.keySet()) {
			this.updateItemMap(id);
		}
	}
	
	@Override
	public void updateItemMap(Element element) {
		if (element == null) {
			return;
		}
		
		if (!this.elements.containsValue(element)) {
			return;
		}
		
		for (int x = element.getX(); x < element.getX() + (element instanceof ElementSizeable ? ((ElementSizeable) element).getWidth() : 1); x++) {
			for (int y = element.getY(); y < element.getY() + (element instanceof ElementSizeable ? ((ElementSizeable) element).getHeigth() : 1); y++) {
				if (this.hasItem(x, y) && this.getElementAt(x, y) != null && !this.getElementAt(x, y).equals(element)){
					continue;
				}
				
				int transx = x - element.getX();
				int transy = y - element.getY();
				
				if (element.getItemAt(transx, transy) != null) {
					this.setItem(x, y, element.isVisible() ? element.getItemAt(transx, transy) : Modifyable.INVISIBLE_ELEMENT);
				} else {
					if (element.isEnabled()) {
						this.setItem(x, y, element.isVisible() ? element.getIcon() : Modifyable.INVISIBLE_ELEMENT);
					} else {
						if(element.getDisabledIcon() != null) this.setItem(x, y, element.isVisible() ? element.getDisabledIcon() : Modifyable.INVISIBLE_ELEMENT);
						else this.setItem(x, y, element.isVisible() ? element.getIcon() : Modifyable.INVISIBLE_ELEMENT);
					}
				}
			}
		}
	}

	@Override
	public void updateItemMap(int id) {
		for (int targetId : this.elements.keySet()) {
			if (targetId == id) {
				this.updateItemMap(this.elements.get(id));
				break;
			}
		}
	}
	
	public boolean hasItem(int x, int y) {
		if (!items.containsKey(x)) {
			return false;
		}
		
		if (!items.get(x).containsKey(y)) {
			return false;
		} else {
			return true;
		}
	}
	
	public void setItem(int x, int y, ItemStack item) {
		if (!items.containsKey(x)) {
			this.items.put(x, new HashMap<Integer, ItemStack>());
		}
		
		this.items.get(x).put(y, item);
	}

}
