package de.rincewind.interfaceapi.elements.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.Test;

import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceapi.gui.elements.util.PointIterator;
import junit.framework.Assert;

public class PointIncrementorTest {
	
	private static Set<Point> newSet(Point... points) {
		Set<Point> result = new HashSet<>();
		result.addAll(Arrays.asList(points));
		return result;
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInitNegativX() {
		new PointIterator(-1, 5);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInitNegativY() {
		new PointIterator(6, -1);
	}
	
	@Test(expected = NullPointerException.class)
	public void testInitNullWindow() {
		new PointIterator(null);
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testIterateNoElement() {
		PointIterator incrementor = new PointIterator(Point.NULL, Point.NULL);
		Assert.assertEquals(Point.NULL, incrementor.next());
		incrementor.next();
	}
	
	@Test
	public void testIterateZero() {
		Set<Point> points = PointIncrementorTest.newSet(new Point(2, 1));
		int carry = 0;
		
		for (Point point : new PointIterator(2, 1, 2, 1)) {
			points.remove(point);
			carry = carry + 1;
		}
		
		Assert.assertEquals(0, points.size());
		Assert.assertEquals(1, carry);
	}
	
	@Test
	public void testIterateX() {
		Set<Point> points = PointIncrementorTest.newSet(new Point(-1, 1), new Point(0, 1), new Point(1, 1));
		int carry = 0;
		
		for (Point point : new PointIterator(-1, 1, 1, 1)) {
			points.remove(point);
			carry = carry + 1;
		}
		
		Assert.assertEquals(0, points.size());
		Assert.assertEquals(3, carry);
	}
	
	@Test
	public void testIterateY() {
		Set<Point> points = PointIncrementorTest.newSet(new Point(0, -3), new Point(0, -2), new Point(0, -1));
		int carry = 0;
		
		for (Point point : new PointIterator(0, -3, 0, -1)) {
			points.remove(point);
			carry = carry + 1;
		}
		
		Assert.assertEquals(0, points.size());
		Assert.assertEquals(3, carry);
	}
	
	@Test
	public void testIterateSquare() {
		Set<Point> points = PointIncrementorTest.newSet(new Point(-1, -3), new Point(-1, -2), new Point(0, -3), new Point(0, -2));
		int carry = 0;
		
		for (Point point : new PointIterator(-1, -3, 0, -2)) {
			points.remove(point);
			carry = carry + 1;
		}
		
		Assert.assertEquals(0, points.size());
		Assert.assertEquals(4, carry);
	}
	
}
