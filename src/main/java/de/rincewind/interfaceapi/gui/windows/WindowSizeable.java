package de.rincewind.interfaceapi.gui.windows;

import de.rincewind.interfaceapi.gui.components.Sized;
import de.rincewind.interfaceapi.gui.util.Bounds;
import de.rincewind.interfaceapi.gui.util.Point;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowColorable;

/**
 * This window is a ChestInventory or something like this, depends on this size.
 * The valid sizes are:
 * <ul>
 * <li>3 x 3 (Dropper/Dispenser)</li>
 * <li>5 x 1 (Hopper)</li>
 * <li>9 x (i e {1, 2, 3, 4, 5, 6})</li>
 * </ul>
 * 
 * To set elements in this window, the point P(0, 0) is in the upper left
 * corner.
 * 
 * @author Rincewind34
 * @since 2.3.3
 */
public interface WindowSizeable extends WindowColorable, Sized {

	public static int calculateHeight(int objectAmount) {
		if (objectAmount <= 0) {
			throw new IllegalArgumentException("The object amount is smaller than 1");
		}

		return (objectAmount + (objectAmount % 9 == 0 ? 0 : 9)) / 9;
	}

	public abstract void setSize(Bounds bounds);

	/**
	 * Returns <code>true</code> if the size is valid and <code>false</code> if
	 * not.
	 * 
	 * @param width
	 *            to check
	 * @param height
	 *            to check
	 * 
	 * @return <code>true</code> if the size is valid and <code>false</code> if
	 *         not
	 */
	public abstract boolean checkSize(Bounds bounds);
	
	@Override
	public default int getWidth() {
		return this.getBounds().getWidth();
	}
	
	@Override
	public default int getHeight() {
		return this.getBounds().getHeight();
	}
	
	@Override
	public default boolean isInside(Point point) {
		return this.getBounds().includes(point);
	}
	
	public default void setSize(int width, int height) {
		this.setSize(Bounds.of(width, height));
	}

}
