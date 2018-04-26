package de.rincewind.interfaceapi.gui.elements.util;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import de.rincewind.interfaceplugin.Validate;

public class Point implements Comparable<Point>, Cloneable {

	public static final Point NULL = new Point(0, 0);

	public static void increase(int width, int height, Consumer<Point> action) {
		Point.increase(Point.NULL, width, height, action);
	}

	public static void increase(Point point, int width, int height, Consumer<Point> action) {
		Validate.notNull(point, "The point cannot be null!");
		Validate.notNull(action, "The action cannot be null!");

		if (width < 0) {
			throw new IllegalArgumentException("The width cannot be smaller than zero!");
		}

		if (height < 0) {
			throw new IllegalArgumentException("The width cannot be smaller than zero!");
		}
		
		for (int x = point.getX(); x <= point.getX() + width; x++) {
			for (int y = point.getY(); y <= point.getY() + height; y++) {
				action.accept(new Point(x, y));
			}
		}
	}
	
	private final int x;
	private final int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int compareTo(Point other) {
		int yCompare = Integer.compare(this.y, other.y);
		return yCompare == 0 ? Integer.compare(this.x, other.x) : yCompare;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}

		Point other = (Point) obj;

		if (this.x != other.x || this.y != other.y) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.x;
		result = prime * result + this.y;
		return result;
	}

	@Override
	public String toString() {
		return "Point{x=" + this.x + ";y=" + this.y + "}";
	}

	@Override
	public Point clone() {
		try {
			return (Point) super.clone();
		} catch (CloneNotSupportedException exception) {
			assert false : "Unreachable code";
			return null;
		}
	}

	public final int getX() {
		return this.x;
	}

	public final int getY() {
		return this.y;
	}
	
	public boolean isPositive() {
		return this.x >= 0 && this.y >= 0;
	}

	public boolean isBiggerThan(Point point) {
		return this.compareTo(point) > 0;
	}

	public Point add(int x, int y) {
		return this.add(new Point(x, y));
	}

	public Point add(Point point) {
		return new Point(this.x + point.getX(), this.y + point.getY());
	}

	public Point subtract(int x, int y) {
		return this.add(-1 * x, -1 * y);
	}

	public Point subtract(Point point) {
		return this.subtract(point.getX(), point.getY());
	}

	public Set<Point> square(Point target) {
		Validate.notNull(target, "The point cannot be null");
		
		if (this.compareTo(target) > 0) {
			throw new IllegalArgumentException("The target point is smaller than this point!");
		}
		
		return this.square(target.getX() - this.x + 1, target.getY() - this.y + 1);
	}

	public Set<Point> square(int width, int height) {
		if (width <= 0) {
			throw new IllegalArgumentException("The width cannot be smaller or equal to zero!");
		}

		if (height <= 0) {
			throw new IllegalArgumentException("The width cannot be smaller or equal to zero!");
		}
		
		Set<Point> result = new HashSet<>();
		
		for (int x = this.x; x < this.x + width; x++) {
			for (int y = this.y; y < this.y + height; y++) {
				result.add(new Point(x, y));
			}
		}

		return result;
	}

}
