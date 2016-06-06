package de.rincewind.api.gui.elements.util;

import java.util.List;

import org.bukkit.event.inventory.InventoryAction;

import de.rincewind.api.gui.elements.abstracts.Element;

/**
 * This class handles the inventory actions ({@link InventoryAction}) and decides for an element
 * if this action should be canceled or not.
 * 
 * Therefore, you can add {@link ClickAction}'s, that should be canceled and remove them.
 * 
 * As an extra, you can 'lock' the instance. If this instance is locked, all actions will automaticly
 * canceled. 
 * 
 * @author Rincewind34
 * @since 2.3.3
 * 
 * @see Element
 */
public interface ClickBlocker {
	
	/**
	 * Returns all blocked actions.
	 * 
	 * @return all blocked actions
	 */
	public abstract List<ClickAction> getBlocked();
	
	/**
	 * Adds an action to block to this instance.
	 * 
	 * @param action to add
	 * 
	 * @throws NullPointerException if the action is <code>null</code>
	 */
	public abstract void addAction(ClickAction action);
	
	/**
	 * Removes an action to block from this instance
	 *  
	 * @param action to remove
	 * 
	 * @throws NullPointerException if the action is <code>null</code>
	 */
	public abstract void removeAction(ClickAction action);
	
	/**
	 * Locks this instance.
	 * 
	 * If this instance is locked all actions will be blocked automatically.
	 * This method does not call {@link ClickBlocker#addAction(ClickAction)} or
	 * {@link ClickBlocker#removeAction(ClickAction)}.
	 */
	public abstract void lock();
	
	/**
	 * Unlocks this instance.
	 * 
	 * If this instance is locked all actions will be blocked automatically.
	 * This method does not call {@link ClickBlocker#addAction(ClickAction)} or
	 * {@link ClickBlocker#removeAction(ClickAction)}.
	 */
	public abstract void unlock();
	
	/**
	 * Returns <code>true</code> if this instance is locked and <code>false</code>
	 * if not.
	 * 
	 * @return <code>true</code> if this instance is locked and <code>false</code>
	 * 			if not
	 */
	public abstract boolean isLocked();

}