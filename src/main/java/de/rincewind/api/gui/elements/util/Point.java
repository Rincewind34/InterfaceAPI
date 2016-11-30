package de.rincewind.api.gui.elements.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import lib.securebit.Validate;

public class Point {
	
	public static final Point NULL = new Point(0, 0);
	
	
	private int x;
	private int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public boolean isSimilar(Point point) {
		return point.getX() == this.x && point.getY() == this.y;
	}
	
	public boolean isBiggerThan(Point point) {
		return this.x > point.getX() && this.y > point.getY();
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
	
	public List<Point> square(Point target) {
		if (!this.isBiggerThan(target)) {
			throw new RuntimeException("The target point is not bigger than this instance!");
		}
		
		return this.square(target.getX() + 1, target.getY() + 1);
	}
	
	public List<Point> square(int width, int height) {
		List<Point> result = new ArrayList<>();
		
		Point.increase(this, width, height, (target) -> {
			result.add(target);
		});
		
		return result;
	}
	
	@Override
	public String toString() {
		return "X: " + x + ", Y: " + y;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Point) {
			return ((Point) obj).getX() == this.x && ((Point) obj).getY() == this.y;
		} else {
			return false;
		}
	}
	
	public static void increase(int width, int height, Consumer<Point> action) {
		Point.increase(Point.NULL, width, height, action);
	}
	
	public static void increase(Point point, int width, int height, Consumer<Point> action) {
		Validate.notNull(point, "The point cannot be null!");
		Validate.notNull(action, "The action cannot be null!");
		
		if (width <= 0) {
			throw new RuntimeException("The width cannot be smaller than one!");
		}
		
		if (height <= 0) {
			throw new RuntimeException("The width cannot be smaller than one!");
		}
		
		for (int x = point.getX(); x < width; x++) {
			for (int y = point.getY(); y < height; y++) {
				action.accept(point);
			}
		}
	}
	
}
