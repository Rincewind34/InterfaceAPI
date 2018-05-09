package de.rincewind.interfaceapi.gui.elements.abstracts;

import org.bukkit.inventory.ItemStack;

import de.rincewind.interfaceapi.gui.components.DisplayableDisabled;
import de.rincewind.interfaceapi.gui.elements.util.ClickBlocker;

/**
 * The element is an empty slot to let the user drop items in
 * or pick something out of it. If this element is disabled, in the slot will be
 * set a barrier and the {@link ClickBlocker} will be locked.
 * 
 * @author Rincewind34
 * @since 2.3.3
 */
public abstract interface ElementSlot extends Element, DisplayableDisabled {
	
	/**
	 * Returns <code>true</code> if the slot is empty and <code>false</code>
	 * if not.
	 * 
	 * @return <code>true</code> if the slot is empty and <code>false</code>
	 * 			if not
	 */
	public abstract boolean isEmpty();
	
	public abstract ItemStack getCurrentContent();
	
}
