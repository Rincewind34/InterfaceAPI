package de.rincewind.interfaceapi.gui.elements.util;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Sets;

import de.rincewind.interfaceapi.gui.util.Point;
public class PointTest {
	
	@Test
	public void testCalculate1() {
		Assert.assertEquals(Point.of(0, 0), Point.calculate(0, 1, 1));
	}
	
	@Test
	public void testCalculate2() {
		Assert.assertEquals(Point.of(0, 0), Point.calculate(0, 4, 3));
	}
	
	@Test
	public void testCalculate3() {
		Assert.assertEquals(Point.of(1, 0), Point.calculate(1, 4, 3));
	}
	
	@Test
	public void testCalculate4() {
		Assert.assertEquals(Point.of(3, 0), Point.calculate(3, 4, 3));
	}
	
	@Test
	public void testCalculate5() {
		Assert.assertEquals(Point.of(0, 1), Point.calculate(4, 4, 3));
	}
	
	@Test
	public void testCalculate6() {
		Assert.assertEquals(Point.of(3, 2), Point.calculate(11, 4, 3));
	}
	
	@Test
	public void testCalculate7() {
		Assert.assertEquals(null, Point.calculate(12, 4, 3));
	}
	
	@Test
	public void testPointEquals() {
		Point point1 = Point.of(0, 0);
		Point point2 = Point.of(0, 0);
		
		Assert.assertTrue(point1.equals(point2));
		Assert.assertTrue(point2.equals(point1));
		
		point1 = Point.of(1, 2);
		
		Assert.assertFalse(point1.equals(point2));
		Assert.assertFalse(point2.equals(point1));
		
		point2 = Point.of(1, 2);
		
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
		
		point1 = Point.of(1, 2);
		
		Assert.assertTrue(point1.compareTo(point2) > 0);
		Assert.assertTrue(point2.compareTo(point1) < 0);
		Assert.assertTrue(point1.isBiggerThan(point2));
		Assert.assertFalse(point2.isBiggerThan(point1));
		
		point2 = Point.of(1, 2);
		
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
		
		point = point.add(Point.of(-3, 7));
		
		Assert.assertEquals(1, point.getX());
		Assert.assertEquals(12, point.getY());
		
		point = point.subtract(Point.of(2, -4));
		
		Assert.assertEquals(-1, point.getX());
		Assert.assertEquals(16, point.getY());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSquareSmaller() {
		Point.of(1, 0).square(Point.NULL);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSquareNegativWidth() {
		Point.NULL.square(-1, 3);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSquareNegativHeight() {
		Point.NULL.square(5, -2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSquareZero() {
		Point.NULL.square(0, 0);
	}
	
	@Test
	public void testSquareSame() {
		Assert.assertEquals(Sets.newHashSet(Point.NULL), Point.NULL.square(Point.NULL));
		Assert.assertEquals(Sets.newHashSet(Point.of(3, 2)), Point.of(3, 2).square(Point.of(3, 2)));
		Assert.assertEquals(Sets.newHashSet(Point.of(-1, 4)), Point.of(-1, 4).square(Point.of(-1, 4)));
	}
	
	@Test
	public void testSquarePositive() {
		Set<Point> points = Point.NULL.square(Point.of(2, 0));
		Assert.assertEquals(3, points.size());
		points.remove(Point.of(2, 0));
		points.remove(Point.of(1, 0));
		points.remove(Point.of(0, 0));
		Assert.assertEquals(0, points.size());
		
		points = Point.NULL.square(Point.of(0, 1));
		Assert.assertEquals(2, points.size());
		points.remove(Point.of(0, 1));
		points.remove(Point.of(0, 0));
		Assert.assertEquals(0, points.size());
		
		points = Point.of(1, 1).square(Point.of(2, 2));
		Assert.assertEquals(4, points.size());
		points.remove(Point.of(1, 1));
		points.remove(Point.of(1, 2));
		points.remove(Point.of(2, 1));
		points.remove(Point.of(2, 2));
		Assert.assertEquals(0, points.size());
	}
	
}
