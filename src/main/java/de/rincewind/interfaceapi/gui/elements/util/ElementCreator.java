package de.rincewind.interfaceapi.gui.elements.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import de.rincewind.interfaceapi.exceptions.ElementCreationException;
import de.rincewind.interfaceapi.gui.components.Modifyable;
import de.rincewind.interfaceapi.gui.elements.ElementButton;
import de.rincewind.interfaceapi.gui.elements.ElementContentSlot;
import de.rincewind.interfaceapi.gui.elements.ElementCounter;
import de.rincewind.interfaceapi.gui.elements.ElementInfo;
import de.rincewind.interfaceapi.gui.elements.ElementInput;
import de.rincewind.interfaceapi.gui.elements.ElementItem;
import de.rincewind.interfaceapi.gui.elements.ElementList;
import de.rincewind.interfaceapi.gui.elements.ElementMap;
import de.rincewind.interfaceapi.gui.elements.ElementMultiButton;
import de.rincewind.interfaceapi.gui.elements.ElementOutput;
import de.rincewind.interfaceapi.gui.elements.ElementScanner;
import de.rincewind.interfaceapi.gui.elements.ElementSelector;
import de.rincewind.interfaceapi.gui.elements.ElementSwitcher;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceplugin.gui.elements.CraftElementButton;
import de.rincewind.interfaceplugin.gui.elements.CraftElementContentSlot;
import de.rincewind.interfaceplugin.gui.elements.CraftElementCounter;
import de.rincewind.interfaceplugin.gui.elements.CraftElementInfo;
import de.rincewind.interfaceplugin.gui.elements.CraftElementInput;
import de.rincewind.interfaceplugin.gui.elements.CraftElementItem;
import de.rincewind.interfaceplugin.gui.elements.CraftElementList;
import de.rincewind.interfaceplugin.gui.elements.CraftElementMap;
import de.rincewind.interfaceplugin.gui.elements.CraftElementMultiButton;
import de.rincewind.interfaceplugin.gui.elements.CraftElementOutput;
import de.rincewind.interfaceplugin.gui.elements.CraftElementScanner;
import de.rincewind.interfaceplugin.gui.elements.CraftElementSelector;
import de.rincewind.interfaceplugin.gui.elements.CraftElementSwitcher;

/**
 * With this object you are able to create elements. The elements will be
 * automatically added to the handler ({@link Modifyable}).
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

	public <T extends Element> T newElement(Class<T> elementCls) {
		if (elementCls.isInterface() || Modifier.isAbstract(elementCls.getModifiers())) {
			throw new IllegalArgumentException("The element class " + elementCls + " is abstract");
		}

		for (Constructor<?> constructor : elementCls.getDeclaredConstructors()) {
			if (constructor.getParameterTypes().length == 1 && Modifyable.class.isAssignableFrom(constructor.getParameterTypes()[0])) {
				try {
					Constructor<T> target = elementCls.getConstructor(constructor.getParameterTypes());
					target.setAccessible(true);

					T element = target.newInstance(target.getParameterTypes()[0].cast(this.handle));
					this.handle.addElement(element);
					return element;
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException exception) {
					assert false : "Should be unreachable: " + exception.getMessage();
					return null;
				} catch (InstantiationException exception) {
					assert false : "Abstract class should not have come here: " + exception.getMessage();
					return null;
				} catch (InvocationTargetException exception) {
					throw new ElementCreationException(exception);
				} catch (ClassCastException exception) {
					throw new ElementCreationException("The element " + elementCls + " is not allowed for handler " + this.handle.getClass());
				}
			}
		}
		
		throw new IllegalArgumentException("The element class " + elementCls + " does not provide a valid constructor");
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

	public ElementMap newMap() {
		ElementMap map = new CraftElementMap(this.handle);
		this.handle.addElement(map);
		return map;
	}

	public ElementContentSlot newContentSlot() {
		ElementContentSlot slot = new CraftElementContentSlot(this.handle);
		this.handle.addElement(slot);
		return slot;
	}

}