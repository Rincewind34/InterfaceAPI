package de.rincewind.plugin.gui.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.components.Locatable.Point;
import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.abstracts.Element;
import de.rincewind.plugin.gui.elements.abstracts.CraftElement;

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
		((CraftElement) element).onAdd();
		this.updateItemMap(element);
		
		((CraftElement) element).setId(id);
		return id;
	}


	@Override
	public ItemStack getItemAt(int x, int y) {
		if (!this.hasItem(x, y)) {
			return null;
		} else{
			return this.items.get(x).get(y);
		}
	}
	
	@Override
	public Element getElementAt(int x, int y) {
		if (this.hasSpaceAt(x, y)) {
			return null;
		}
		
		for (Element element : this.elements.values()) {
			if (element.getPoint().getX() <= x && x < element.getPoint().getX() + element.getWidth()) {
				if (element.getPoint().getY() <= y && y < element.getPoint().getY() + element.getHeight()){
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
		
		CraftElement craftElement = (CraftElement) element;
		craftElement.updateItemMap();
		
		for (int x = craftElement.getPoint().getX(); x < craftElement.getPoint().getX() + element.getWidth(); x++) {
			for (int y = craftElement.getPoint().getY(); y < craftElement.getPoint().getY() + element.getHeight(); y++) {
				if (this.hasItem(x, y) && this.getElementAt(x, y) != null && !this.getElementAt(x, y).equals(element)){
					continue;
				}
				
				int transx = x - craftElement.getPoint().getX();
				int transy = y - craftElement.getPoint().getY();
				
				Point trans = new Point(transx, transy);
				
				this.setItem(x, y, craftElement.getItemAt(trans));
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
		if (!this.items.containsKey(x)) {
			return false;
		}
		
		if (!this.items.get(x).containsKey(y)) {
			return false;
		} else {
			return true;
		}
	}
	
	public void setItem(int x, int y, ItemStack item) {
		if (!this.items.containsKey(x)) {
			this.items.put(x, new HashMap<Integer, ItemStack>());
		}
		
		this.items.get(x).put(y, item);
	}

}
