package de.rincewind.api.gui.elements.abstracts;

import java.util.function.Consumer;

import org.bukkit.event.inventory.InventoryAction;

import de.rincewind.api.gui.components.EventBased;
import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.util.ClickBlocker;
import de.rincewind.api.gui.elements.util.Point;
import de.rincewind.api.gui.windows.abstracts.WindowColorable;

/**
 * This is the basic element. All other elements extend from this class.
 * Although this class contains the methods {@link Element#getWidth()} and
 * {@link Element#getHeight()} you cannot change the size in this basic-element.
 * 
 * @author Rincewind34
 * @since 2.3.3
 * 
 * @see ElementDisplayable
 * @see ElementSlot
 */
public abstract interface Element extends EventBased {
	
	/**
	 * Returns the x-coordinate of this element.
	 * 
	 * @return the x-coordinate of this element.
	 * 
	 * @deprecated because of the new class {@link Point} summarizing
	 * 				the x and y coordinate.
	 */
	@Deprecated
	public abstract int getX();
	
	/**
	 * Returns the y-coordinate of this element.
	 * 
	 * @return the y-coordinate of this element.
	 * 
	 * @deprecated because of the new class {@link Point} summarizing
	 * 				the x and y coordinate.
	 */
	@Deprecated
	public abstract int getY();
	
	/**
	 * Sets the position of this element.
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * 
	 * @deprecated because of the new class {@link Point} summarizing
	 * 				the x and y coordinate.
	 * 
	 * @see Element#setPoint(Point)
	 */
	@Deprecated
	public abstract void setPosition(int x, int y);
	
	/**
	 * Returns the position of this element.
	 * 
	 * @return the position of this element
	 */
	public abstract Point getPoint();
	
	/**
	 * Sets the position of this element. Before changing the position,
	 * {@link Modifyable#clearItemsFrom(Element)} will be called and
	 * {@link Modifyable#readItemsFrom(Element)} after changing the position.
	 * 
	 * If the x or y coordinate is negative or greater than the size of for example
	 * the window added this element to, this element may not be rendered.
	 * 
	 * @param point to set
	 * 
	 * @throws NullPointerException if the point is <code>null</code>
	 */
	public abstract void setPoint(Point point);
	
	public abstract void priorize();
	
	/**
	 * Returns the width of this element. In this class, you cannot
	 * change the size so the width is 1.
	 * 
	 * @return the width of this element
	 */
	public abstract int getWidth();
	
	/**
	 * Returns the height of this element. In this class, you cannot
	 * change the size so the height is 1.
	 * 
	 * @return the height of this element
	 */
	public abstract int getHeight();
	
	/**
	 * Sets the {@link ClickBlocker} for this element. The blocker blocks some
	 * {@link InventoryAction}-s for example like pickup or drop from/to the slot.
	 * By the default all actions are canceled.
	 * 
	 * @param blocker to set
	 * 
	 * @throws NullPointerException if the blocker is null
	 */
	public abstract void setBlocker(ClickBlocker blocker);
	
	/**
	 * Sets the visibility of this element. If the visibility is set to
	 * <code>false</code>, the element will be replaced for example in the
	 * {@link WindowColorable} with the background of the window this element
	 * is added to.
	 * 
	 * @param visible hiding this element if the value is <code>false</code>.
	 */
	public abstract void setVisible(boolean visible);
	
	/**
	 * Runs a consumer for each point locatable in this element.
	 * 
	 * @param action to run.
	 * 
	 * @throws NullPointerException if the action is null
	 */
	public abstract void iterate(Consumer<Point> action);
	
	/**
	 * Calls {@link Element#setEnabled(boolean)} to set this element
	 * enabled.
	 */
	public abstract void enable();
	
	/**
	 * Calls {@link Element#setEnabled(boolean)} to set this element
	 * disabled.
	 */
	public abstract void disable();
	
	/**
	 * Disables or enables this element. If an element many actions like
	 * pressing a button will be blocked.
	 * 
	 * @param value to set
	 */
	public abstract void setEnabled(boolean value);
	
	/**
	 * Returns <code>true</code> if this element is enabled and 
	 * <code>false</code> if not.
	 * 
	 * @return <code>true</code> if this element is enabled and 
	 * 			<code>false</code> if not
	 */
	public abstract boolean isEnabled();
	
	/**
	 * Returns <code>true</code> if this element is visible and 
	 * <code>false</code> if not.
	 * 
	 * @return <code>true</code> if this element is visible and 
	 * 			<code>false</code> if not
	 */
	public abstract boolean isVisible();
	
	/**
	 * Returns the {@link ClickBlocker} set to this element.
	 * 
	 * @return the {@link ClickBlocker} set to this element
	 */
	public abstract ClickBlocker getBlocker();
	
}
