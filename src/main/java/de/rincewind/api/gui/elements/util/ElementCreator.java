package de.rincewind.api.gui.elements.util;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.ElementButton;
import de.rincewind.api.gui.elements.ElementCounter;
import de.rincewind.api.gui.elements.ElementInfo;
import de.rincewind.api.gui.elements.ElementInput;
import de.rincewind.api.gui.elements.ElementItem;
import de.rincewind.api.gui.elements.ElementList;
import de.rincewind.api.gui.elements.ElementMap;
import de.rincewind.api.gui.elements.ElementMultiButton;
import de.rincewind.api.gui.elements.ElementOutput;
import de.rincewind.api.gui.elements.ElementScanner;
import de.rincewind.api.gui.elements.ElementSelector;
import de.rincewind.api.gui.elements.ElementSwitcher;
import de.rincewind.plugin.gui.elements.CraftElementButton;
import de.rincewind.plugin.gui.elements.CraftElementCounter;
import de.rincewind.plugin.gui.elements.CraftElementInfo;
import de.rincewind.plugin.gui.elements.CraftElementInput;
import de.rincewind.plugin.gui.elements.CraftElementItem;
import de.rincewind.plugin.gui.elements.CraftElementList;
import de.rincewind.plugin.gui.elements.CraftElementMap;
import de.rincewind.plugin.gui.elements.CraftElementMultiButton;
import de.rincewind.plugin.gui.elements.CraftElementOutput;
import de.rincewind.plugin.gui.elements.CraftElementScanner;
import de.rincewind.plugin.gui.elements.CraftElementSelector;
import de.rincewind.plugin.gui.elements.CraftElementSwitcher;

/**
 * With this object you are able to create elements. The elements will be automaticly
 * added to the handler ({@link Modifyable}).
 * 
 * @author Rincewind34
 * @since 2.3.3
 * 
 * @see Modifyable
 */
public class ElementCreator {
	
	private Modifyable handle;
	
	public ElementCreator(Modifyable handle) {
		this.handle = handle;
	}
	
	public ElementButton newButton() {
		ElementButton element = new CraftElementButton(this.handle);
		this.handle.addElement(element);
		return element;
	}
	
	public ElementCounter newCounter() {
		ElementCounter element = new CraftElementCounter(this.handle);
		this.handle.addElement(element);
		return element;
	}
	
	public ElementInput newInput() {
		ElementInput element = new CraftElementInput(this.handle);
		this.handle.addElement(element);
		return element;
	}
	
	public <T> ElementList<T> newList(Class<T> saveClass) {
		ElementList<T> element = new CraftElementList<T>(this.handle);
		this.handle.addElement(element);
		return element;
	}
	
	public ElementOutput newOutput() {
		ElementOutput counter = new CraftElementOutput(this.handle);
		this.handle.addElement(counter);
		return counter;
	}
	
	public ElementScanner newScanner() {
		ElementScanner element = new CraftElementScanner(this.handle);
		this.handle.addElement(element);
		return element;
	}
	
	public <T> ElementSwitcher<T> newSwitcher() {
		ElementSwitcher<T> element = new CraftElementSwitcher<T>(this.handle);
		this.handle.addElement(element);
		return element;
	}
	
	public <T> ElementSwitcher<T> newSwitcher(Class<T> saveClazz) {
		ElementSwitcher<T> element = new CraftElementSwitcher<T>(this.handle);
		this.handle.addElement(element);
		return element;
	}
	
	public ElementItem newItem() {
		ElementItem element = new CraftElementItem(this.handle);
		this.handle.addElement(element);
		return element;
	}
	
	public ElementMultiButton newMultiButton() {
		ElementMultiButton element = new CraftElementMultiButton(this.handle);
		this.handle.addElement(element);
		return element;
	}
	
	public ElementInfo newInfoPanel() {
		ElementInfo element = new CraftElementInfo(this.handle);
		this.handle.addElement(element);
		return element;
	}
	
	public ElementSelector newSelector() {
		ElementSelector element = new CraftElementSelector(this.handle);
		this.handle.addElement(element);
		return element;
	}
	
	public <T> ElementMap<T> newMap(Class<T> saveClass) {
		ElementMap<T> map = new CraftElementMap<>(this.handle);
		this.handle.addElement(map);
		return map;
	}
	
}