package de.rincewind.interfaceapi.gui.elements.util;

public class PointIncrementor {

	public static Point calculate(int minX, int minY, int maxX, int maxY, int currentIndex) {
		if (currentIndex < 0) {
			return null;
		}

		int x = minX;
		int y = minY;

		for (int i = 0; i < currentIndex; i++) {
			x = x + 1;

			if (x > maxX) {
				y = y + 1;
				x = minX;
			}

			if (y > maxY) {
				return null;
			}
		}

		return new Point(x, y);
	}
	
	
	private int minX;
	private int minY;

	private int maxX;
	private int maxY;

	private int currentIndex;

	public PointIncrementor(int maxX, int maxY) {
		this(0, 0, maxX, maxY);
	}

	public PointIncrementor(int minX, int minY, int maxX, int maxY) {
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	public void setMinX(int minX) {
		this.minX = minX;
	}

	public void setMinY(int minY) {
		this.minY = minY;
	}

	public void setMaxX(int maxX) {
		this.maxX = maxX;
	}

	public void setMaxY(int maxY) {
		this.maxY = maxY;
	}

	public boolean hasNext() {
		return PointIncrementor.calculate(this.minX, this.minY, this.maxX, this.maxY, this.currentIndex + 1) != null;
	}

	public boolean hasPrevious() {
		return PointIncrementor.calculate(this.minX, this.minY, this.maxX, this.maxY, this.currentIndex - 1) != null;
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

	public int getCurrentIndex() {
		return this.currentIndex;
	}

	public Point current() {
		return PointIncrementor.calculate(this.minX, this.minY, this.maxX, this.maxY, this.currentIndex);
	}

	public Point next() {
		this.setCurrentIndex(this.currentIndex + 1);
		return this.current();
	}

	public Point previous() {
		this.setCurrentIndex(this.currentIndex - 1);
		return this.current();
	}

}
