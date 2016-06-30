package de.rincewind.plugin.gui.windows.abstracts;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lib.securebit.ReflectionUtil;
import lib.securebit.Validate;

import org.bukkit.inventory.ItemStack;

import de.rincewind.api.exceptions.APIException;
import de.rincewind.api.gui.elements.abstracts.Element;
import de.rincewind.api.gui.elements.util.ElementCreator;
import de.rincewind.api.gui.elements.util.Point;
import de.rincewind.api.gui.windows.abstracts.WindowEditor;
import de.rincewind.api.handling.events.WindowMaximizeEvent;
import de.rincewind.api.handling.listener.WindowMaximizeListener;
import de.rincewind.plugin.gui.elements.abstracts.CraftElement;

public abstract class CraftWindowEditor extends CraftWindowContainer implements WindowEditor {

	private List<Element> elements;
	
	private Map<Integer, Map<Integer, ItemStack>> items;
	
	private ElementCreator creator;
	
	public CraftWindowEditor() {
		super();
		
		this.elements = new ArrayList<>();
		this.items = new HashMap<Integer, Map<Integer,ItemStack>>();
		this.creator = new ElementCreator(this);
		
		this.getEventManager().registerListener(new WindowMaximizeListener() {
			
			@Override
			public void onFire(WindowMaximizeEvent event) {
				CraftWindowEditor.this.readItemsFromAll();
			}
		}).addBefore();
	}
	
	@Override
	public List<Element> getElements() {
		List<Element> result = new ArrayList<Element>();
		
		for (Element e : this.elements) {
			result.add(e);
		}
		
		return result;
	}

	@Override
	public void addElement(Element element) {
		Validate.notNull(element, "The element cannot be null!");
		
		if (((CraftElement) element).getId() != -1) {
			throw new APIException("The element is already added in a Window!");
		}
		
		List<Integer> ids = new ArrayList<Integer>();
		
		for (Element target : this.elements) {
			ids.add(((CraftElement) target).getId());
		}
		
		int id = 0;
		
		while (ids.contains(id)) {
			id++; // Sucht eine freie id von 0 aufwaerts
		}
		
		this.elements.add(element);
		this.injectId(element, id);
		
		((CraftElement) element).onCreate();
		this.readItemsFrom(element);
	}


	@Override
	@Deprecated
	public Element getElementAt(Point point) {
		Validate.notNull(point, "The point cannot be null!");
		
		for (Element element : this.elements) {
			if (element.getPoint().getX() <= point.getX() && point.getX() < element.getPoint().getX() + element.getWidth()) {
				if (element.getPoint().getY() <= point.getY() && point.getY() < element.getPoint().getY() + element.getHeight()){
					return element;
				}
			}
		}
		
		return null;
	}
	
	@Override
	public List<Element> getElementsAt(Point point) {
		Validate.notNull(point, "The point cannot be null!");
		
		List<Element> elements = new ArrayList<>();
		
		for (Element element : this.elements) {
			if (element.getPoint().getX() <= point.getX() && point.getX() < element.getPoint().getX() + element.getWidth()) {
				if (element.getPoint().getY() <= point.getY() && point.getY() < element.getPoint().getY() + element.getHeight()){
					elements.add(element);
				}
			}
		}
		
		return elements;
	}
	
	@Override
	public Element getVisibleElementAt(Point point) {
		Validate.notNull(point, "The point cannot be null!");
		
		for (Element element : this.getElementsAt(point)) {
			if (element.isVisible()) {
				return element;
			}
		}
		
		return null;
	}
	
	@Override
	public void readItemsFromAll() {
		for (Element element : this.elements) {
			this.readItemsFrom(element);
		}
	}
	
	@Override
	public void readItemsFrom(Element element) {
		Validate.notNull(element, "The element cannot be null!");
		
		if (!this.elements.contains(element)) {
			return;
		}
		
		CraftElement craftElement = (CraftElement) element;
		craftElement.updateItemMap();
		
		craftElement.iterate((point) -> {
			Point trans = point.add(craftElement.getPoint().getX(), craftElement.getPoint().getY());
			
			if (!craftElement.isVisible()) {
				if (this.hasVisibleElementAt(trans)) {
					this.readItemsFrom(this.getVisibleElementAt(trans));
					return;
				}
			}
			
			if (!this.hasVisibleElementAt(trans) || this.getVisibleElementAt(trans).equals(craftElement)) { /* Vlt überlappen sich Elemente */
				this.setItem(trans, craftElement.getItemAt(point));
			}
		});
		
		this.updateBukkitInventory();
	}
	
	@Override
	public void clearItemsFrom(Element element) {
		Validate.notNull(element, "The element cannot be null!");
		
		if (!this.elements.contains(element)) {
			throw new APIException("The element is not added in this Window!");
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
	public void clearElements() {
		for (Element element : this.elements) {
			this.removeElement(element);
		}
	}
	
	@Override
	public void removeElement(Element element) {
		Validate.notNull(element, "The element cannot be null!");
		
		if (!this.elements.contains(element)) {
			throw new APIException("The element is not added in this Window!");
		}
		
		this.clearItemsFrom(element);
		this.elements.remove(element);
		this.injectId(element, -1);
	}
	
	@Override
	public void priorize(Element element) {
		Validate.notNull(element, "The element cannot be null!");
		
		if (!this.elements.contains(element)) {
			throw new APIException("The element is not added in this Window!");
		}
		
		List<Element> newList = new ArrayList<>();
		newList.add(element);
		
		for (Element target : this.elements) {
			if (!target.equals(element)) {
				newList.add(target);
			}
		}
		
		this.elements = newList;
		this.clearItemsFromAll();
		this.readItemsFromAll();
	}
	
	@Override
	public boolean hasVisibleElementAt(Point point) {
		Validate.notNull(point, "The point cannot be null!");
		
		return this.getVisibleElementAt(point) != null;
	}

	public void setItem(Point point, ItemStack item) {
		Validate.notNull(point, "The point cannot be null!");
		
		if (!this.items.containsKey(point.getX())) {
			this.items.put(point.getX(), new HashMap<Integer, ItemStack>());
		}
		
		this.items.get(point.getX()).put(point.getY(), item);
	}
	
	public boolean hasSpaceAt(Point point) {
		Validate.notNull(point, "The point cannot be null!");
		
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
		Validate.notNull(point, "The point cannot be null!");
		
		if (this.hasSpaceAt(point)) {
			return null;
		} else{
			return this.items.get(point.getX()).get(point.getY());
		}
	}
	
	public void clearItemsFromAll() {
		for (Element element : this.elements) {
			this.clearItemsFrom(element);
		}
	}
	
	private void injectId(Element element, int id) {
		Validate.notNull(element, "The element cannot be null!");
		
		Field fieldId = ReflectionUtil.getDeclaredField(CraftElement.class, "id");
		ReflectionUtil.setValue(fieldId, element, id);
	}
	
}
