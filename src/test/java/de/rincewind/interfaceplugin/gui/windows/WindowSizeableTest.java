package de.rincewind.interfaceplugin.gui.windows;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceapi.gui.windows.WindowSizeable;
import de.rincewind.test.TestServer;

public class WindowSizeableTest {
	
	private CraftWindowSizeable window;
	
	@BeforeClass
	public static void initInterfaceAPI() {
		TestServer.setup();
	}
	
	@Before
	public void initWindow() {
		this.window = new CraftWindowSizeable(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCalculateHeight1() {
		WindowSizeable.calculateHeight(0);
	}
	
	@Test
	public void testCalculateHeight2() {
		Assert.assertEquals(1, WindowSizeable.calculateHeight(1));
	}
	
	@Test
	public void testCalculateHeight3() {
		Assert.assertEquals(1, WindowSizeable.calculateHeight(5));
	}
	
	@Test
	public void testCalculateHeight4() {
		Assert.assertEquals(1, WindowSizeable.calculateHeight(9));
	}
	
	@Test
	public void testCalculateHeight5() {
		Assert.assertEquals(2, WindowSizeable.calculateHeight(10));
	}
	
	@Test
	public void testCalculateHeight6() {
		Assert.assertEquals(2, WindowSizeable.calculateHeight(18));
	}
	
	@Test
	public void testCalculateHeight7() {
		Assert.assertEquals(3, WindowSizeable.calculateHeight(22));
	}
	
	@Test
	public void testGetPoints() {
		Assert.assertEquals(Point.NULL.square(9, 3), this.window.getPoints());
		
		this.window.setSize(9, 4);
		
		Assert.assertEquals(Point.NULL.square(9, 4), this.window.getPoints());
		
		this.window.setSize(5, 1);
		
		Assert.assertEquals(Point.NULL.square(5, 1), this.window.getPoints());
	}
	
	@Test
	public void testCheckSize() {
		Assert.assertFalse(this.window.checkSize(-1, 0));
		Assert.assertFalse(this.window.checkSize(Integer.MIN_VALUE, Integer.MAX_VALUE));
		Assert.assertFalse(this.window.checkSize(0, 0));
		Assert.assertFalse(this.window.checkSize(0, 2));
		Assert.assertFalse(this.window.checkSize(5, 0));
		Assert.assertFalse(this.window.checkSize(1, 1));
		Assert.assertFalse(this.window.checkSize(9, 7));
		Assert.assertFalse(this.window.checkSize(4, 6));

		Assert.assertTrue(this.window.checkSize(5, 1));
		Assert.assertTrue(this.window.checkSize(3, 3));
		Assert.assertTrue(this.window.checkSize(9, 4));
	}
	
	@Test
	public void testSetSize() {
		this.window.setSize(9, 4);
		this.window.setSize(5, 1);
		this.window.setSize(3, 3);
	}
	
}
