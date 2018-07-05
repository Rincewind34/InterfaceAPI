package de.rincewind.interfaceplugin.gui.elements.abstracts;

import org.bukkit.Material;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Sets;

import de.rincewind.interfaceapi.exceptions.ElementComponentException;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.util.Point;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowEditor;
import de.rincewind.test.TestWindowSizeable;
import junit.framework.Assert;

public class CraftElementTest {

	private CraftElement element;

	@Before
	public void initElement() {
		this.element = new TestWindowSizeable().elementCreator().newElement(TestElement.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNull() {
		new TestElement(null);
	}

	@Test(expected = ElementComponentException.class)
	public void testSetEnabled() {
		this.element.setComponentValue(Element.ENABLED, true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetEnabledNull() {
		this.element.getComponent(Element.ENABLED).setEnabled(true);
		this.element.setComponentValue(Element.ENABLED, null);
	}

	@Test(expected = ElementComponentException.class)
	public void testSetWidth() {
		this.element.setComponentValue(Element.WIDTH, 2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetWidthNull() {
		this.element.getComponent(Element.WIDTH).setEnabled(true);
		this.element.setComponentValue(Element.WIDTH, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetWidthZero() {
		this.element.setComponentValue(Element.WIDTH, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetWidthNegative() {
		this.element.setComponentValue(Element.WIDTH, -6);
	}
	
	@Test(expected = ElementComponentException.class)
	public void testSetHeight() {
		this.element.setComponentValue(Element.HEIGHT, 2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetHeightNull() {
		this.element.getComponent(Element.HEIGHT).setEnabled(true);
		this.element.setComponentValue(Element.HEIGHT, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetHeightZero() {
		this.element.setComponentValue(Element.HEIGHT, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetHeightNegative() {
		this.element.setComponentValue(Element.HEIGHT, -2);
	}

	@Test
	public void testSetBasic() {
		Assert.assertEquals(1, this.element.getWidth());
		Assert.assertEquals(1, this.element.getHeight());
		Assert.assertEquals(true, this.element.isVisible());
		Assert.assertEquals(true, this.element.isEnabled());
		Assert.assertEquals(Point.NULL, this.element.getPoint());
		Assert.assertEquals(Sets.newHashSet(Point.NULL), this.element.getPoints());
		Assert.assertEquals(new Icon(Material.APPLE), this.element.getIcon(Point.NULL));
	}

	@Test
	public void testEnabled() {
		this.element.getComponent(Element.ENABLED).setEnabled(true);
		this.element.setComponentValue(Element.ENABLED, false);

		Assert.assertEquals(false, this.element.isEnabled());

		this.element.getComponent(Element.ENABLED).reset();

		Assert.assertEquals(true, this.element.isEnabled());
	}

	@Test
	public void testWidth() {
		this.element.getComponent(Element.WIDTH).setEnabled(true);
		this.element.setComponentValue(Element.WIDTH, 7);

		Assert.assertEquals(7, this.element.getWidth());

		this.element.getComponent(Element.WIDTH).reset();

		Assert.assertEquals(1, this.element.getWidth());
	}

	@Test
	public void testHeight() {
		this.element.getComponent(Element.HEIGHT).setEnabled(true);
		this.element.setComponentValue(Element.HEIGHT, 5);

		Assert.assertEquals(5, this.element.getHeight());

		this.element.getComponent(Element.HEIGHT).reset();

		Assert.assertEquals(1, this.element.getHeight());
	}

	@Test
	public void testGetPoints() {
		this.element.getComponent(Element.WIDTH).setEnabled(true);
		this.element.getComponent(Element.HEIGHT).setEnabled(true);

		this.element.setComponentValue(Element.WIDTH, 2);

		Assert.assertEquals(Sets.newHashSet(Point.NULL, Point.of(1, 0)), this.element.getPoints());

		this.element.setComponentValue(Element.WIDTH, 4);

		Assert.assertEquals(Sets.newHashSet(Point.NULL, Point.of(1, 0), Point.of(2, 0), Point.of(3, 0)), this.element.getPoints());

		this.element.setComponentValue(Element.WIDTH, 2);
		this.element.setComponentValue(Element.HEIGHT, 3);

		Assert.assertEquals(Sets.newHashSet(Point.NULL, Point.of(1, 0), Point.of(0, 1), Point.of(1, 1), Point.of(0, 2), Point.of(1, 2)),
				this.element.getPoints());
	}
	
	@Test
	public void testIsInside() {
		this.element.getComponent(Element.WIDTH).setEnabled(true);
		this.element.getComponent(Element.HEIGHT).setEnabled(true);
		
		Assert.assertTrue(this.element.isInside(Point.NULL));
		Assert.assertFalse(this.element.isInside(Point.of(0, 1)));
		Assert.assertFalse(this.element.isInside(Point.of(1, 0)));
		Assert.assertFalse(this.element.isInside(Point.of(1, 1)));
		Assert.assertFalse(this.element.isInside(Point.of(0, -1)));
		Assert.assertFalse(this.element.isInside(Point.of(-1, 0)));
		Assert.assertFalse(this.element.isInside(Point.of(-1, -1)));
		Assert.assertFalse(this.element.isInside(Point.of(6, -71)));
		Assert.assertFalse(this.element.isInside(Point.of(-45, 33)));
		Assert.assertFalse(this.element.isInside(Point.of(45, 26)));
		Assert.assertFalse(this.element.isInside(Point.of(-56, -64)));
		
		this.element.setComponentValue(Element.HEIGHT, 3);
		
		Assert.assertTrue(this.element.isInside(Point.NULL));
		Assert.assertTrue(this.element.isInside(Point.of(0, 1)));
		Assert.assertTrue(this.element.isInside(Point.of(0, 2)));
		Assert.assertFalse(this.element.isInside(Point.of(0, 3)));
		Assert.assertFalse(this.element.isInside(Point.of(1, 0)));
		Assert.assertFalse(this.element.isInside(Point.of(1, 1)));
		Assert.assertFalse(this.element.isInside(Point.of(0, -1)));
		Assert.assertFalse(this.element.isInside(Point.of(-1, 0)));
		Assert.assertFalse(this.element.isInside(Point.of(-1, -1)));
		Assert.assertFalse(this.element.isInside(Point.of(6, -71)));
		Assert.assertFalse(this.element.isInside(Point.of(-45, 33)));
		Assert.assertFalse(this.element.isInside(Point.of(45, 26)));
		Assert.assertFalse(this.element.isInside(Point.of(-56, -64)));
		
		this.element.setComponentValue(Element.WIDTH, 2);
		
		Assert.assertTrue(this.element.isInside(Point.NULL));
		Assert.assertTrue(this.element.isInside(Point.of(0, 1)));
		Assert.assertTrue(this.element.isInside(Point.of(0, 2)));
		Assert.assertTrue(this.element.isInside(Point.of(1, 0)));
		Assert.assertTrue(this.element.isInside(Point.of(1, 1)));
		Assert.assertTrue(this.element.isInside(Point.of(1, 2)));
		Assert.assertFalse(this.element.isInside(Point.of(0, 3)));
		Assert.assertFalse(this.element.isInside(Point.of(0, -1)));
		Assert.assertFalse(this.element.isInside(Point.of(-1, 0)));
		Assert.assertFalse(this.element.isInside(Point.of(-1, -1)));
		Assert.assertFalse(this.element.isInside(Point.of(6, -71)));
		Assert.assertFalse(this.element.isInside(Point.of(-45, 33)));
		Assert.assertFalse(this.element.isInside(Point.of(45, 26)));
		Assert.assertFalse(this.element.isInside(Point.of(-56, -64)));
	}
	
	private static class TestElement extends CraftElement {

		public TestElement(WindowEditor handle) {
			super(handle);
		}

		@Override
		protected Icon getIcon0(Point point) {
			return new Icon(Material.APPLE);
		}

	}

}
