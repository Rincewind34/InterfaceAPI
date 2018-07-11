package de.rincewind.interfaceapi.gui.util.creators;

import de.rincewind.interfaceapi.exceptions.ElementCreationException;
import de.rincewind.interfaceapi.gui.elements.ElementContentSlot;
import de.rincewind.interfaceapi.gui.elements.ElementCounter;
import de.rincewind.interfaceapi.gui.elements.ElementInput;
import de.rincewind.interfaceapi.gui.elements.ElementItem;
import de.rincewind.interfaceapi.gui.elements.ElementList;
import de.rincewind.interfaceapi.gui.elements.ElementMap;
import de.rincewind.interfaceapi.gui.elements.ElementObjectSelector;
import de.rincewind.interfaceapi.gui.elements.ElementOutput;
import de.rincewind.interfaceapi.gui.elements.ElementPager;
import de.rincewind.interfaceapi.gui.elements.ElementItemSelector;
import de.rincewind.interfaceapi.gui.elements.ElementSwitcher;
import de.rincewind.interfaceapi.gui.elements.ElementToolbar;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;

public class ElementCreatorCloseable implements ElementCreator {

	private boolean closed;

	private final ElementCreator wrapper;

	public ElementCreatorCloseable(ElementCreator wrapper) {
		assert wrapper != null : "The wrapper is null";
		
		this.wrapper = wrapper;
	}

	@Override
	public <T extends Element> T newElement(Class<T> elementCls, Object... parameters) {
		this.validateClosed();
		return this.wrapper.newElement(elementCls, parameters);
	}

	@Override
	public ElementCounter newCounter() {
		this.validateClosed();
		return this.wrapper.newCounter();
	}

	@Override
	public ElementInput newInput() {
		this.validateClosed();
		return this.wrapper.newInput();
	}

	@Override
	public ElementList newList() {
		this.validateClosed();
		return this.wrapper.newList();
	}

	@Override
	public ElementOutput newOutput() {
		this.validateClosed();
		return this.wrapper.newOutput();
	}

	@Override
	public ElementSwitcher newSwitcher() {
		this.validateClosed();
		return this.wrapper.newSwitcher();
	}

	@Override
	public ElementItem newItem() {
		this.validateClosed();
		return this.wrapper.newItem();
	}
	
	@Override
	public ElementItemSelector newItemSelector() {
		this.validateClosed();
		return this.wrapper.newItemSelector();
	}

	@Override
	public <T> ElementObjectSelector<T> newObjectSelector(Class<T> objectClass) {
		this.validateClosed();
		return this.wrapper.newObjectSelector(objectClass);
	}

	@Override
	public ElementMap newMap() {
		this.validateClosed();
		return this.wrapper.newMap();
	}

	@Override
	public ElementContentSlot newContentSlot() {
		this.validateClosed();
		return this.wrapper.newContentSlot();
	}

	@Override
	public ElementToolbar newToolbar() {
		this.validateClosed();
		return this.wrapper.newToolbar();
	}
	
	@Override
	public ElementPager newPager() {
		this.validateClosed();
		return this.wrapper.newPager();
	}

	public void close() {
		this.closed = true;
	}

	public boolean isClosed() {
		return this.closed;
	}

	private void validateClosed() {
		if (this.closed) {
			throw new ElementCreationException("The creator is already closed");
		}
	}

}
