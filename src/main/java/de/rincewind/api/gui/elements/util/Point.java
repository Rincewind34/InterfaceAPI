package de.rincewind.api.gui.elements.util;

public class Point {
	
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
	
	public static Point atNull() {
		return new Point(0, 0);
	}
	
}
