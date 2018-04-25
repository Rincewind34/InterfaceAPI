package de.rincewind.interfaceapi.elements.util;

import java.util.Set;

import org.junit.Test;

import de.rincewind.interfaceapi.gui.elements.util.Point;
import junit.framework.Assert;

public class PointTest {
	
	@Test
	public void testPointEquals() {
		Point point1 = Point.NULL;
		Point point2 = Point.NULL;
		
		Assert.assertTrue(point1.equals(point2));
		Assert.assertTrue(point2.equals(point1));
		
		point1 = new Point(1, 2);
		
		Assert.assertFalse(point1.equals(point2));
		Assert.assertFalse(point2.equals(point1));
		
		point2 = new Point(1, 2);
		
		Assert.assertTrue(point1.equals(point2));
		Assert.assertTrue(point2.equals(point1));
	}
	
	@Test
	public void testPointCompareTo() {
		Point point1 = Point.NULL;
		Point point2 = Point.NULL;
		
		Assert.assertEquals(0, point1.compareTo(point2));
		Assert.assertEquals(0, point2.compareTo(point1));
		Assert.assertFalse(point1.isBiggerThan(point2));
		Assert.assertFalse(point2.isBiggerThan(point1));
		
		point1 = new Point(1, 2);
		
		Assert.assertTrue(point1.compareTo(point2) > 0);
		Assert.assertTrue(point2.compareTo(point1) < 0);
		Assert.assertTrue(point1.isBiggerThan(point2));
		Assert.assertFalse(point2.isBiggerThan(point1));
		
		point2 = new Point(1, 2);
		
		Assert.assertEquals(0, point1.compareTo(point2));
		Assert.assertEquals(0, point2.compareTo(point1));
		Assert.assertFalse(point1.isBiggerThan(point2));
		Assert.assertFalse(point2.isBiggerThan(point1));
	}
	
	@Test
	public void testPointMath() {
		Point point = Point.NULL;
		point.add(Point.NULL);
		
		Assert.assertEquals(0, point.getX());
		Assert.assertEquals(0, point.getY());

		point = point.add(4, 5);
		
		Assert.assertEquals(4, point.getX());
		Assert.assertEquals(5, point.getY());
		
		point = point.add(new Point(-3, 7));
		
		Assert.assertEquals(1, point.getX());
		Assert.assertEquals(12, point.getY());
		
		point = point.subtract(new Point(2, -4));
		
		Assert.assertEquals(-1, point.getX());
		Assert.assertEquals(16, point.getY());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSquareSmaller() {
		new Point(1, 0).square(Point.NULL);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSquareNegativWidth() {
		Point.NULL.square(-1, 3);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSquareNegativHeight() {
		Point.NULL.square(5, -2);
	}
	
	@Test
	public void testSquareSuccess() {
		Set<Point> points = Point.NULL.square(Point.NULL);
		Assert.assertEquals(1, points.size());
		Assert.assertEquals(Point.NULL, points.iterator().next());
		
		points = Point.NULL.square(new Point(2, 0));
		Assert.assertEquals(3, points.size());
		points.remove(new Point(2, 0));
		points.remove(new Point(1, 0));
		points.remove(new Point(0, 0));
		Assert.assertEquals(0, points.size());
		
		points = Point.NULL.square(new Point(0, 1));
		Assert.assertEquals(2, points.size());
		points.remove(new Point(0, 1));
		points.remove(new Point(0, 0));
		Assert.assertEquals(0, points.size());
		
		points = new Point(1, 1).square(new Point(2, 2));
		Assert.assertEquals(4, points.size());
		points.remove(new Point(1, 1));
		points.remove(new Point(1, 2));
		points.remove(new Point(2, 1));
		points.remove(new Point(2, 2));
		Assert.assertEquals(0, points.size());
	}
	
}
