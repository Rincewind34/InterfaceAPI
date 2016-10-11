package de.rincewind.api.gui.elements.abstracts;

import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.elements.ElementInput;
import de.rincewind.api.gui.elements.ElementOutput;
import de.rincewind.api.gui.elements.ElementScanner;
import de.rincewind.api.gui.elements.util.ClickBlocker;

/**
 * The element is an empty slot to let the user drop items in
 * or pick something out of it. If this element is disabled, in the slot will be
 * set a barrier and the {@link ClickBlocker} will be locked.
 * 
 * @author Rincewind34
 * @since 2.3.3
 * 
 * @see ElementScanner
 * @see ElementInput
 * @see ElementOutput
 */
public abstract interface ElementSlot extends Element {
	
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
