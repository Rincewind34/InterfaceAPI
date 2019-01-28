package de.rincewind.interfaceplugin.gui.elements;

import org.bukkit.Material;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.util.Point;
import de.rincewind.test.TestWindowSizeable;

public class CraftElementCounterTest {

	private CraftElementCounter element;

	@Before
	public void initElement() {
		this.element = (CraftElementCounter) new TestWindowSizeable().elementCreator().newCounter();
	}

	@Test
	public void testVarient() {
		Assert.assertEquals(0, this.element.getCount());
		Assert.assertEquals(0, this.element.getMinCount());
		Assert.assertEquals(Integer.MAX_VALUE, this.element.getMaxCount());
		Assert.assertNull(this.element.getFallback());
		Assert.assertSame(Icon.AIR, this.element.getIcon(Point.NULL));
	}

	@Test
	public void testIcon_CountOne() {
		Icon icon = new Icon(Material.APPLE);
		this.element.setIcon(icon);
		this.element.setCount(1);

		Assert.assertEquals(1, this.element.getCount());
		Assert.assertNull(this.element.getFallback());
		Assert.assertEquals(icon, this.element.getIcon(Point.NULL));
		Assert.assertSame(1, this.element.getIcon(Point.NULL).getAmount());
	}

	@Test
	public void testIcon_CountZero() {
		Icon icon = new Icon(Material.APPLE);
		this.element.setIcon(icon);
		this.element.setCount(0);

		Assert.assertEquals(0, this.element.getCount());
		Assert.assertNull(this.element.getFallback());
		Assert.assertEquals(icon, this.element.getIcon(Point.NULL));
		Assert.assertSame(0, this.element.getIcon(Point.NULL).getAmount());
	}

	@Test
	public void testIcon_CountFive() {
		Icon icon = new Icon(Material.APPLE);
		this.element.setIcon(icon);
		this.element.setCount(5);

		Assert.assertEquals(5, this.element.getCount());
		Assert.assertNull(this.element.getFallback());
		Assert.assertEquals(icon, this.element.getIcon(Point.NULL));
		Assert.assertSame(5, this.element.getIcon(Point.NULL).getAmount());
	}
	
}