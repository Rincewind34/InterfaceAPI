package de.rincewind.api.gui.elements;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.inventory.InventoryClickEvent;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.listener.ElementActionListener;

public class ElementButton extends ElementSizeable {
	
	protected List<ElementActionListener> listeners;
	
	/**
	 * 
	 * @param handle The window for this element
	 */
	public ElementButton(Modifyable handle) {
		super(handle);
		
		this.listeners = new ArrayList<ElementActionListener>();
	}
	
	/**
	 * 
	 * @return The listenercount
	 */
	public int getListenerCount() {
		return this.listeners.size();
	}
	
	/**
	 * Adds an listener to this element.
	 * 
	 * @param listener The new Listener
	 */
	public void addActionListener(ElementActionListener listener) {
		this.listeners.add(listener);
	}
	
	/**
	 * Triggers a virtual press on the button
	 * 
	 * @param event 
	 */
	public void press(InventoryClickEvent event) {
		for (ElementActionListener listener : this.listeners) {
			if (event.isRightClick()) {
				listener.onRightClick(event, this);
			} else {
				listener.onLeftClick(event, this);
			}
		}
	}
	
	/**
	 * 
	 * @return All the registered listeners
	 */
	protected List<ElementActionListener> getListeners() {
		return this.listeners;
	}

}
