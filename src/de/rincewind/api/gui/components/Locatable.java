package de.rincewind.api.gui.components;

public abstract interface Locatable {
	
	/**
	 * @return The x-coordinate
	 */
	@Deprecated
	public abstract int getX();
	
	/**
	 * 
	 * @return The y-coordinate
	 */
	@Deprecated
	public abstract int getY();
	
	/**
	 * 
	 * Set the position in a window to P(x|y)
	 * 
	 * @param x The x-coordinate
	 * @param y die y-coordinate
	 */
	@Deprecated
	public abstract void setPosition(int x, int y);
	
	
	public abstract Point getPoint();
	
	public abstract void setPoint(Point point);
	
	public static class Point {
		
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
		
		public Point remove(int x, int y) {
			return this.add(-1 * x, -1 * y);
		}
		
		public Point remove(Point point) {
			return this.remove(point.getX(), point.getY());
		}
		
		@Override
		public String toString() {
			return "X: " + x + ", Y: " + y;
		}
		
		
		public static Point atNull() {
			return new Point(0, 0);
		}
		
	}
	
}
