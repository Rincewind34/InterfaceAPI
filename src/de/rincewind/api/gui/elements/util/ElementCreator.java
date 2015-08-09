package de.rincewind.api.gui.elements.util;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.ElementButton;
import de.rincewind.api.gui.elements.ElementCounter;
import de.rincewind.api.gui.elements.ElementInput;
import de.rincewind.api.gui.elements.ElementList;
import de.rincewind.api.gui.elements.ElementOutput;
import de.rincewind.api.gui.elements.ElementScanner;
import de.rincewind.api.gui.elements.ElementSwitcher;
import de.rincewind.plugin.gui.elements.CraftElementButton;
import de.rincewind.plugin.gui.elements.CraftElementCounter;
import de.rincewind.plugin.gui.elements.CraftElementInput;
import de.rincewind.plugin.gui.elements.CraftElementList;
import de.rincewind.plugin.gui.elements.CraftElementOutput;
import de.rincewind.plugin.gui.elements.CraftElementScanner;
import de.rincewind.plugin.gui.elements.CraftElementSwitcher;

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
	
	public ElementList newList() {
		ElementList element = new CraftElementList(this.handle);
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
	
	public ElementSwitcher newSwitcher() {
		ElementSwitcher element = new CraftElementSwitcher(this.handle);
		this.handle.addElement(element);
		return element;
	}
	
}
