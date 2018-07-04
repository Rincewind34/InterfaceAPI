package de.rincewind.interfaceapi.gui.util.creators;

import de.rincewind.interfaceapi.gui.components.Displayable;
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
import de.rincewind.interfaceapi.gui.elements.util.lore.SimpleLore;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowEditor;

/**
 * With this object you are able to create elements. The elements will be
 * automatically added to the handler ({@link WindowEditor}).
 * 
 * @author Rincewind34
 * @since 2.3.3
 */
public interface ElementCreator {

	public abstract <T extends Element> T newElement(Class<T> elementCls);

	public abstract ElementCounter newCounter();

	public abstract ElementInput newInput();

	public abstract ElementList newList();

	public abstract ElementOutput newOutput();

	public abstract ElementSwitcher newSwitcher();

	public abstract ElementItem newItem();

	public abstract ElementMultiButton newMultiButton();

	public abstract ElementSelector newSelector();

	public abstract ElementMap newMap();

	public abstract ElementContentSlot newContentSlot();
	
	public default ElementSwitcher newBooleanSwitcher(String disabledDisplay, String enabledDisplay) {
		return this.newBooleanSwitcher(disabledDisplay, enabledDisplay, false);
	}

	public default ElementSwitcher newBooleanSwitcher(String disabledDisplay, String enabledDisplay, boolean current) {
		ElementSwitcher switcher = this.newSwitcher();
		switcher.addSwitch(Displayable.of(false, "§c" + disabledDisplay));
		switcher.addSwitch(Displayable.of(true, "§a" + enabledDisplay));
		switcher.setSwitchIndex(current ? 1 : 0);
		return switcher;
	}

	public default ElementSwitcher newBooleanSwitcher(String disabledDisplay, SimpleLore disabledLore, String enabledDisplay, SimpleLore enabledLore, boolean current) {
		ElementSwitcher switcher = this.newSwitcher();
		switcher.addSwitch(Displayable.of(false, "§c" + disabledDisplay, disabledLore));
		switcher.addSwitch(Displayable.of(true, "§a" + enabledDisplay, enabledLore));
		switcher.setSwitchIndex(current ? 1 : 0);
		return switcher;
	}

}