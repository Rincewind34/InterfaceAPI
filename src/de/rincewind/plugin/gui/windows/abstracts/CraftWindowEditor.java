package de.rincewind.plugin.gui.windows.abstracts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lib.securebit.Validate;

import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.components.Locatable.Point;
import de.rincewind.api.gui.elements.abstracts.Element;
import de.rincewind.api.gui.elements.util.ElementCreator;
import de.rincewind.api.gui.windows.abstracts.WindowEditor;
import de.rincewind.plugin.gui.elements.abstracts.CraftElement;

public abstract class CraftWindowEditor extends CraftWindowContainer implements WindowEditor {

	
	private Map<Integer, Element> elements;
	
	private Map<Integer, Map<Integer, ItemStack>> items;
	
	private ElementCreator creator;
	
	public CraftWindowEditor() {
		super();
		
		this.elements = new HashMap<Integer, Element>();
		this.items = new HashMap<Integer, Map<Integer,ItemStack>>();
		this.creator = new ElementCreator(this);
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
	public void addElement(Element element) {
		if (((CraftElement) element).getId() != -1) {
			return;
		}
		
		int id = 0;
		
		while (this.elements.containsKey(id)) {
			id++; // Sucht eine freie id von 0 aufwaerts
		}
		
		this.elements.put(id, element);
		
		((CraftElement) element).onAdd();
		this.readItemsFrom(element);
		
		((CraftElement) element).setId(id);
	}


	@Override
	public Element getElementAt(Point point) {
		if (this.hasSpaceAt(point)) {
			return null;
		}
		
		for (Element element : this.elements.values()) {
			if (element.getPoint().getX() <= point.getX() && point.getX() < element.getPoint().getX() + element.getWidth()) {
				if (element.getPoint().getY() <= point.getY() && point.getY() < element.getPoint().getY() + element.getHeight()){
					return element;
				}
			}
		}
		
		return null;
	}

	@Override
	public void readItemsFromAll() {
		for (Integer id : this.elements.keySet()) {
			this.readItemsFrom(id);
		}
	}
	
	@Override
	public void readItemsFrom(Element element) {
		Validate.notNull(element, "The element cannot be null!");
		
		if (!this.elements.containsValue(element)) {
			return;
		}
		
		CraftElement craftElement = (CraftElement) element;
		craftElement.updateItemMap();
		
		craftElement.iterate((point) -> {
			this.setItem(point.add(craftElement.getPoint().getX(), craftElement.getPoint().getY()), craftElement.getItemAt(point));
		});
		
		this.updateBukkitInventory();
	}
	
	@Override
	public void clearItemsFrom(Element element) {
		Validate.notNull(element, "The element cannot be null!");
		
		if (!this.elements.containsValue(element)) {
			return;
		}
		
		CraftElement craftElement = (CraftElement) element;
		craftElement.updateItemMap();
		
		craftElement.iterate((point) -> {
			Point target = point.add(craftElement.getPoint().getX(), craftElement.getPoint().getY());
			
			if (this.getElementAt(target) == element) {
				this.setItem(target, null);
			}
		});
		
		this.updateBukkitInventory();
	}
	
	@Override
	public ElementCreator elementCreator() {
		return this.creator;
	}
	
	@Override
	public void maximize() {
		this.readItemsFromAll();
		super.maximize();
	}

	public void readItemsFrom(int id) {
		for (int targetId : this.elements.keySet()) {
			if (targetId == id) {
				this.readItemsFrom(this.elements.get(id));
				break;
			}
		}
	}
	
	public void setItem(Point point, ItemStack item) {
		if (!this.items.containsKey(point.getX())) {
			this.items.put(point.getX(), new HashMap<Integer, ItemStack>());
		}
		
		this.items.get(point.getX()).put(point.getY(), item);
	}
	
	public boolean hasSpaceAt(Point point) {
		if (this.items.containsKey(point.getX())) {
			if (this.items.get(point.getX()).containsKey(point.getY())) {
				if (this.items.get(point.getX()).get(point.getY()) != null) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public ItemStack getItemAt(Point point) {
		if (this.hasSpaceAt(point)) {
			return null;
		} else{
			return this.items.get(point.getX()).get(point.getY());
		}
	}

}
