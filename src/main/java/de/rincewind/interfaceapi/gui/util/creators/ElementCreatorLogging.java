package de.rincewind.interfaceapi.gui.util.creators;

import java.util.ArrayList;
import java.util.Collection;

import de.rincewind.interfaceapi.gui.elements.ElementContentSlot;
import de.rincewind.interfaceapi.gui.elements.ElementCounter;
import de.rincewind.interfaceapi.gui.elements.ElementInput;
import de.rincewind.interfaceapi.gui.elements.ElementItem;
import de.rincewind.interfaceapi.gui.elements.ElementList;
import de.rincewind.interfaceapi.gui.elements.ElementMap;
import de.rincewind.interfaceapi.gui.elements.ElementMultiButton;
import de.rincewind.interfaceapi.gui.elements.ElementOutput;
import de.rincewind.interfaceapi.gui.elements.ElementSelector;
import de.rincewind.interfaceapi.gui.elements.ElementSwitcher;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;

public class ElementCreatorLogging implements ElementCreator {
	
	private final ElementCreator wrapper;
	private final Collection<Element> log;
	
	public ElementCreatorLogging(ElementCreator wrapper) {
		this(wrapper, new ArrayList<>());
	}
	
	public ElementCreatorLogging(ElementCreator wrapper, Collection<Element> log) {
		assert wrapper != null : "The wrapper is null";
		assert log != null : "The log is null";
		assert log.isEmpty() : "The log collection is alread filled";
		
		this.wrapper = wrapper;
		this.log = log;
	}

	@Override
	public <T extends Element> T newElement(Class<T> elementCls) {
		T element = this.wrapper.newElement(elementCls);
		this.log.add(element);
		return element;
	}

	@Override
	public ElementCounter newCounter() {
		ElementCounter element = this.wrapper.newCounter();
		this.log.add(element);
		return element;
	}

	@Override
	public ElementInput newInput() {
		ElementInput element = this.wrapper.newInput();
		this.log.add(element);
		return element;
	}

	@Override
	public ElementList newList() {
		ElementList element = this.wrapper.newList();
		this.log.add(element);
		return element;
	}

	@Override
	public ElementOutput newOutput() {
		ElementOutput element = this.wrapper.newOutput();
		this.log.add(element);
		return element;
	}

	@Override
	public ElementSwitcher newSwitcher() {
		ElementSwitcher element = this.wrapper.newSwitcher();
		this.log.add(element);
		return element;
	}

	@Override
	public ElementItem newItem() {
		ElementItem element = this.wrapper.newItem();
		this.log.add(element);
		return element;
	}

	@Override
	public ElementMultiButton newMultiButton() {
		ElementMultiButton element = this.wrapper.newMultiButton();
		this.log.add(element);
		return element;
	}

	@Override
	public ElementSelector newSelector() {
		ElementSelector element = this.wrapper.newSelector();
		this.log.add(element);
		return element;
	}

	@Override
	public ElementMap newMap() {
		ElementMap element = this.wrapper.newMap();
		this.log.add(element);
		return element;
	}

	@Override
	public ElementContentSlot newContentSlot() {
		ElementContentSlot element = this.wrapper.newContentSlot();
		this.log.add(element);
		return element;
	}
	
	public Collection<Element> getLog() {
		return this.log;
	}
	
}