package de.rincewind.interfaceapi.gui.util.creators;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import de.rincewind.interfaceapi.exceptions.ElementCreationException;
import de.rincewind.interfaceapi.gui.elements.ElementContentSlot;
import de.rincewind.interfaceapi.gui.elements.ElementCounter;
import de.rincewind.interfaceapi.gui.elements.ElementInput;
import de.rincewind.interfaceapi.gui.elements.ElementItem;
import de.rincewind.interfaceapi.gui.elements.ElementItemSelector;
import de.rincewind.interfaceapi.gui.elements.ElementList;
import de.rincewind.interfaceapi.gui.elements.ElementMap;
import de.rincewind.interfaceapi.gui.elements.ElementObjectSelector;
import de.rincewind.interfaceapi.gui.elements.ElementOutput;
import de.rincewind.interfaceapi.gui.elements.ElementPager;
import de.rincewind.interfaceapi.gui.elements.ElementSwitcher;
import de.rincewind.interfaceapi.gui.elements.ElementToolbar;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowEditor;
import de.rincewind.interfaceplugin.Validate;
import de.rincewind.interfaceplugin.gui.elements.CraftElementContentSlot;
import de.rincewind.interfaceplugin.gui.elements.CraftElementCounter;
import de.rincewind.interfaceplugin.gui.elements.CraftElementInput;
import de.rincewind.interfaceplugin.gui.elements.CraftElementItem;
import de.rincewind.interfaceplugin.gui.elements.CraftElementItemSelector;
import de.rincewind.interfaceplugin.gui.elements.CraftElementList;
import de.rincewind.interfaceplugin.gui.elements.CraftElementMap;
import de.rincewind.interfaceplugin.gui.elements.CraftElementObjectSelector;
import de.rincewind.interfaceplugin.gui.elements.CraftElementOutput;
import de.rincewind.interfaceplugin.gui.elements.CraftElementPager;
import de.rincewind.interfaceplugin.gui.elements.CraftElementSwitcher;
import de.rincewind.interfaceplugin.gui.elements.CraftElementToolbar;
import de.rincewind.interfaceplugin.gui.windows.abstracts.CraftWindowEditor;

public class ElementCreatorBlank implements ElementCreator {

	private CraftWindowEditor handle;

	public ElementCreatorBlank(CraftWindowEditor handle) {
		this.handle = handle;
	}

	@Override
	public <T extends Element> T newElement(Class<T> elementCls, Object... parameters) {
		Validate.notNull(elementCls, "The element class cannot be null");

		if (elementCls.isInterface() || Modifier.isAbstract(elementCls.getModifiers())) {
			throw new IllegalArgumentException("The element class " + elementCls + " is abstract");
		}

		for (Constructor<?> constructor : elementCls.getDeclaredConstructors()) {
			if (constructor.getParameterTypes().length == (1 + parameters.length) && WindowEditor.class.isAssignableFrom(constructor.getParameterTypes()[0])) {
				boolean invalid = false;
				
				for (int i = 0; i < parameters.length; i++) {
					if (!parameters[i].getClass().isAssignableFrom(constructor.getParameterTypes()[i + 1])) {
						invalid = true;
						break;
					}
				}
				
				if (invalid) {
					continue;
				}
				
				try {
					Constructor<T> target = elementCls.getConstructor(constructor.getParameterTypes());
					target.setAccessible(true);

					T element = target.newInstance(target.getParameterTypes()[0].cast(this.handle), parameters);
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

	@Override
	public ElementCounter newCounter() {
		ElementCounter element = new CraftElementCounter(this.handle);
		this.handle.addElement(element);
		return element;
	}

	@Override
	public ElementInput newInput() {
		ElementInput element = new CraftElementInput(this.handle);
		this.handle.addElement(element);
		return element;
	}

	@Override
	public ElementList newList() {
		ElementList element = new CraftElementList(this.handle);
		this.handle.addElement(element);
		return element;
	}

	@Override
	public ElementOutput newOutput() {
		ElementOutput counter = new CraftElementOutput(this.handle);
		this.handle.addElement(counter);
		return counter;
	}

	@Override
	public ElementSwitcher newSwitcher() {
		ElementSwitcher element = new CraftElementSwitcher(this.handle);
		this.handle.addElement(element);
		return element;
	}

	@Override
	public ElementItem newItem() {
		ElementItem element = new CraftElementItem(this.handle);
		this.handle.addElement(element);
		return element;
	}

	@Override
	public ElementItemSelector newItemSelector() {
		ElementItemSelector element = new CraftElementItemSelector(this.handle);
		this.handle.addElement(element);
		return element;
	}

	@Override
	public <T> ElementObjectSelector<T> newObjectSelector(Class<T> objectClass) {
		ElementObjectSelector<T> element = new CraftElementObjectSelector<>(this.handle, objectClass);
		this.handle.addElement(element);
		return element;
	}

	@Override
	public ElementMap newMap() {
		ElementMap element = new CraftElementMap(this.handle);
		this.handle.addElement(element);
		return element;
	}

	@Override
	public ElementContentSlot newContentSlot() {
		ElementContentSlot element = new CraftElementContentSlot(this.handle);
		this.handle.addElement(element);
		return element;
	}
	
	@Override
	public ElementToolbar newToolbar() {
		ElementToolbar element = new CraftElementToolbar(this.handle);
		this.handle.addElement(element);
		return element;
	}
	
	@Override
	public ElementPager newPager() {
		ElementPager element = new CraftElementPager(this.handle);
		this.handle.addElement(element);
		return element;
	}

}