package de.rincewind.interfaceapi.gui.util;

import de.rincewind.interfaceapi.gui.elements.util.PointIterator;

public final class Bounds extends Pair2D implements Comparable<Bounds> {

	public static final Bounds MIN = new Bounds(1, 1);

	public static Bounds square(int width) {
		return Bounds.of(width, width);
	}

	public static Bounds of(int width, int height) {
		if (width == 1 && height == 1) {
			return Bounds.MIN;
		} else {
			return new Bounds(width, height);
		}
	}

	public static Bounds of(Direction direction, int length) {
		if (direction == Direction.HORIZONTAL) {
			return Bounds.of(length, 1);
		} else {
			return Bounds.of(1, length);
		}
	}

	private Bounds(int width, int height) {
		super(width, height);

		if (width <= 0) {
			throw new IllegalArgumentException("The width cannot be smaller than 1");
		}

		if (height <= 0) {
			throw new IllegalArgumentException("The height cannot be smaller than 1");
		}
	}

	@Override
	public int compareTo(Bounds other) {
		return Integer.compare(this.squareSize(), other.squareSize());
	}

	@Override
	public String toString() {
		return "Bounds{width=" + this.first + ";height=" + this.second + "}";
	}

	public boolean includes(Point point) {
		return this.first >= point.getX() && this.second >= point.getY() && point.getX() >= 0 && point.getY() >= 0;
	}

	public boolean includes(Bounds bounds) {
		return this.first >= bounds.first && this.second >= bounds.second;
	}

	public boolean isBiggerThan(Bounds bounds) {
		return this.squareSize() > bounds.squareSize();
	}

	public int squareSize() {
		return this.first * this.second;
	}

	public int getWidth() {
		return this.first;
	}

	public int getHeight() {
		return this.second;
	}

	public int getLength(Direction direction) {
		if (direction == Direction.HORIZONTAL) {
			return this.first;
		} else {
			return this.second;
		}
	}

	public int indexOf(Point point) {
		return this.getWidth() * point.getY() + point.getX();
	}

	public Bounds resize(int offsetWidth, int offsetHeight) {
		return Bounds.of(this.first + offsetWidth, this.second + offsetHeight);
	}

	public PointIterator points() {
		return new PointIterator(this.first, this.second);
	}

}
