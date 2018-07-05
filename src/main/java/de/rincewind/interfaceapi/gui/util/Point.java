package de.rincewind.interfaceapi.gui.util;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import de.rincewind.interfaceplugin.Validate;

public final class Point extends Pair2D implements Comparable<Point> {

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
	
	public static Point calculate(int position, int width, int height) {
		if (width <= 0) {
			throw new IllegalArgumentException("The width is smaller than 1");
		}
		
		if (height <= 0) {
			throw new IllegalArgumentException("The height is smaller than 1");
		}
		
		if (position < 0) {
			return null;
		}
		
		Point point = new Point(position % width, position / width);
		
		if (point.first >= height) {
			return null;
		} else {
			return point;
		}
	}
	
	public static Point of(int x, int y) {
		if (x == 0 && y == 0) {
			return Point.NULL;
		} else {
			return new Point(x, y);
		}
	}
	
	public Point(int x, int y) {
		super(x, y);
	}
	
	@Override
	public int compareTo(Point other) {
		int firstCompare = Integer.compare(this.first, other.first);
		return firstCompare == 0 ? Integer.compare(this.second, other.second) : firstCompare;
	}

	@Override
	public String toString() {
		return "Point{x=" + this.first + ";y=" + this.second + "}";
	}
	
	public boolean isPositive() {
		return this.first >= 0 && this.second >= 0;
	}

	public boolean isBiggerThan(Point point) {
		return this.compareTo(point) > 0;
	}
	
	public int getX() {
		return this.first;
	}

	public int getY() {
		return this.second;
	}

	public Point add(int x, int y) {
		return this.add(new Point(x, y));
	}

	public Point add(Point point) {
		return new Point(this.first + point.getX(), this.second + point.getY());
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
		
		return this.square(target.getX() - this.first + 1, target.getY() - this.second + 1);
	}
	
	public Set<Point> square(Bounds bounds) {
		Validate.notNull(bounds, "The bounds cannot be null");
		
		return this.square(bounds.getWidth(), bounds.getHeight());
	}

	public Set<Point> square(int width, int height) {
		if (width <= 0) {
			throw new IllegalArgumentException("The width cannot be smaller or equal to zero!");
		}

		if (height <= 0) {
			throw new IllegalArgumentException("The width cannot be smaller or equal to zero!");
		}
		
		Set<Point> result = new HashSet<>();
		
		for (int x = this.first; x < this.first + width; x++) {
			for (int y = this.second; y < this.second + height; y++) {
				result.add(new Point(x, y));
			}
		}

		return result;
	}

}
