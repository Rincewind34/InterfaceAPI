package de.rincewind.plugin.gui.elements.abstracts;

import java.util.function.Consumer;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.abstracts.ElementSizeable;
import de.rincewind.defaults.exceptions.InvalidSizeException;

public abstract class CraftElementSizeable extends CraftElementDisplayable implements ElementSizeable {
	
	private int width;
	private int height;
	
	/**
	 * ElementSizable: An element, which size can be customized
	 * @param handle The window for this element
	 */
	public CraftElementSizeable(Modifyable handle) {
		super(handle);
		
		this.width = 1;
		this.height = 1;
	}
	
	@Override
	public void updateItemMap() {
		this.updateItemMap(true);
	}
	
	@Override
	public int getWidth() {
		return this.width;
	}

	@Override
	public int getHeight() {
		return this.height;
	}
	
	@Override
	public void setSize(int width, int height) {
		if (width <= 0 || height <= 0) {
			throw new InvalidSizeException(width, height, ElementSizeable.class);
		}
		
		this.width = width;
		this.height = height;
	}
	
	@Override
	public boolean isInside(int x, int y) {
		if (x < this.width && y < this.height) {
			return true;
		} else {
			return false;
		}
	}
	
	protected final void updateItemMap(boolean withIcon) {
		this.iterate((point) -> {
			this.updateItemMap(point, withIcon);
		});
	}
	
	protected final void iterate(Consumer<Point> action) {
		for (int x = 0; x < super.getWidth(); x++) {
			for (int y = 0; y < super.getHeight(); y++) {
				action.accept(new Point(x, y));
			}
		}
	}

}
