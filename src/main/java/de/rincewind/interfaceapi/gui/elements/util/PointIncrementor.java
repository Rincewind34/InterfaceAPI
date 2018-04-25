package de.rincewind.interfaceapi.gui.elements.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

import de.rincewind.interfaceapi.gui.windows.WindowSizeable;

public class PointIncrementor implements Iterator<Point>, Iterable<Point>, Cloneable {

	private final int minX;
	private final int minY;

	private final int maxX;
	private final int maxY;

	private Point current;

	public PointIncrementor(WindowSizeable window) {
		this(0, 0, window.getWidth() - 1, window.getHeight() - 1);
	}

	public PointIncrementor(int maxX, int maxY) {
		this(0, 0, maxX, maxY);
	}

	public PointIncrementor(Point minPoint, Point maxPoint) {
		this(minPoint.getX(), minPoint.getY(), maxPoint.getX(), maxPoint.getY());
	}

	public PointIncrementor(int minX, int minY, int maxX, int maxY) {
		if (minX > maxX) {
			throw new IllegalArgumentException("The maximal x has to be greater or equal to minimal x");
		}

		if (minY > maxY) {
			throw new IllegalArgumentException("The maximal y has to be greater or equal to minimal y");
		}

		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}

	@Override
	public boolean hasNext() {
		return this.current == null || this.current.getX() != this.maxX || this.current.getY() != this.maxY;
	}

	@Override
	public Point next() {
		if (!this.hasNext()) {
			throw new NoSuchElementException();
		}

		if (this.current == null) {
			return this.current = new Point(this.minX, this.minY);
		} else {
			return this.current = new Point(this.current.getX() == this.maxX ? this.minX : this.current.getX() + 1,
					this.current.getX() == this.maxX ? this.current.getY() + 1 : this.current.getY());
		}
	}

	@Override
	public Iterator<Point> iterator() {
		return this.current != null ? this.clone() : this;
	}

	@Override
	public PointIncrementor clone() {
		try {
			PointIncrementor incrementor = (PointIncrementor) super.clone();
			incrementor.current = null;
			return incrementor;
		} catch (CloneNotSupportedException exception) {
			assert false : "Unreachable code";
			return null;
		}
	}

	public int getMinX() {
		return this.minX;
	}

	public int getMinY() {
		return this.minY;
	}

	public int getMaxX() {
		return this.maxX;
	}

	public int getMaxY() {
		return this.maxY;
	}

	public Point current() {
		return this.current;
	}

}
