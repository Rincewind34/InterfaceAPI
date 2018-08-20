package de.rincewind.interfaceplugin.gui.elements;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.junit.Before;
import org.junit.Test;

import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.components.DisplayableDisabled;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.util.Color;
import de.rincewind.interfaceapi.gui.util.Point;
import de.rincewind.interfaceapi.util.InterfaceUtils;
import de.rincewind.test.TestWindowSizeable;
import junit.framework.Assert;

public class CraftElementMapTest {

	private CraftElementMap element;

	@Before
	public void initElement() {
		this.element = (CraftElementMap) new TestWindowSizeable().elementCreator().newMap();
		this.element.setComponentValue(Element.WIDTH, 3);
	}

	@Test
	public void testVarient() {
		Assert.assertEquals(0, this.element.size());
		Assert.assertEquals(-1, this.element.getSelectedIndex());
		Assert.assertNull(this.element.getSelected());
		Assert.assertEquals(-1, this.element.getSelectedIndex());
		Assert.assertEquals(DisplayableDisabled.default_icon, this.element.getDisabledIcon());
		Assert.assertEquals(Color.TRANSLUCENT, this.element.getColor());

		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(0, 0)));
		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(1, 0)));
		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(2, 0)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddItem_Null() {
		this.element.addItem(null);
	}

	@Test
	public void testAddItem_One() {
		Icon icon = new Icon(Material.APPLE);

		this.element.addItem(icon);

		Assert.assertEquals(1, this.element.size());
		Assert.assertEquals(-1, this.element.getSelectedIndex());
		Assert.assertNull(this.element.getSelected());

		Assert.assertSame(icon, this.element.getIcon(Point.of(0, 0)));
		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(1, 0)));
		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(2, 0)));
	}

	@Test
	public void testAddItem_Multiple() {
		Icon icon1 = new Icon(Material.APPLE);
		Icon icon2 = new Icon(Material.STONE);
		Icon icon3 = new Icon(Material.INK_SAC);
		Icon icon4 = new Icon(Material.DIRT);

		this.element.addItem(icon1);
		this.element.addItem(icon2);
		this.element.addItem(icon3);
		this.element.addItem(icon4);

		Assert.assertEquals(4, this.element.size());
		Assert.assertSame(icon1, this.element.getIcon(Point.of(0, 0)));
		Assert.assertSame(icon2, this.element.getIcon(Point.of(1, 0)));
		Assert.assertSame(icon3, this.element.getIcon(Point.of(2, 0)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddItems_Null() {
		this.element.addItems(null);
	}

	@Test
	public void testAddItems_GameMode() {
		this.element.addItems(Stream.of(GameMode.values()).map(Displayable::of).collect(Collectors.toList()));

		Assert.assertEquals(4, this.element.size());
		Assert.assertEquals(InterfaceUtils.convertGameMode(GameMode.CREATIVE), this.element.getIcon(Point.of(0, 0)));
		Assert.assertEquals(InterfaceUtils.convertGameMode(GameMode.SURVIVAL), this.element.getIcon(Point.of(1, 0)));
		Assert.assertEquals(InterfaceUtils.convertGameMode(GameMode.ADVENTURE), this.element.getIcon(Point.of(2, 0)));
	}
	
//	@Test TODO
//	public void testSetStartIndex_MultipleElements1() {
//		Icon icon1 = new Icon(Material.APPLE);
//		Icon icon2 = new Icon(Material.STONE);
//		Icon icon3 = new Icon(Material.INK_SAC);
//		Icon icon4 = new Icon(Material.DIRT);
//
//		this.element.addItem(icon1);
//		this.element.addItem(icon2);
//		this.element.addItem(icon3);
//		this.element.addItem(icon4);
//		this.element.setStartIndex(1);
//		
//		Assert.assertSame(icon2, this.element.getIcon(Point.of(0, 0)));
//		Assert.assertSame(icon3, this.element.getIcon(Point.of(1, 0)));
//		Assert.assertSame(icon4, this.element.getIcon(Point.of(2, 0)));
//	}
//	
//	@Test
//	public void testSetStartIndex_MultipleElements2() {
//		Icon icon1 = new Icon(Material.APPLE);
//		Icon icon2 = new Icon(Material.STONE);
//		Icon icon3 = new Icon(Material.INK_SAC);
//		Icon icon4 = new Icon(Material.DIRT);
//
//		this.element.addItem(icon1);
//		this.element.addItem(icon2);
//		this.element.addItem(icon3);
//		this.element.addItem(icon4);
//		this.element.setStartIndex(2);
//		
//		Assert.assertSame(icon3, this.element.getIcon(Point.of(0, 0)));
//		Assert.assertSame(icon4, this.element.getIcon(Point.of(1, 0)));
//		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(2, 0)));
//	}
//	
//	@Test
//	public void testSetStartIndex_OneElement() {
//		Icon icon = new Icon(Material.APPLE);
//
//		this.element.addItem(icon);
//		this.element.setStartIndex(2);
//		
//		Assert.assertEquals(0, this.element.getStartIndex());
//		Assert.assertSame(icon, this.element.getIcon(Point.of(0, 0)));
//		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(1, 0)));
//		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(2, 0)));
//	}
//	
//	@Test
//	public void testSetStartIndex_Negative() {
//		Icon icon = new Icon(Material.APPLE);
//
//		this.element.addItem(icon);
//		this.element.setStartIndex(-3);
//		
//		Assert.assertEquals(0, this.element.getStartIndex());
//		Assert.assertSame(icon, this.element.getIcon(Point.of(0, 0)));
//		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(1, 0)));
//		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(2, 0)));
//	}
//	
//	@Test
//	public void testSetStartIndex_PersitantNegative() {
//		Icon icon1 = new Icon(Material.APPLE);
//		Icon icon2 = new Icon(Material.STONE);
//		Icon icon3 = new Icon(Material.INK_SAC);
//		Icon icon4 = new Icon(Material.DIRT);
//
//		this.element.addItem(icon1);
//		this.element.addItem(icon2);
//		this.element.addItem(icon3);
//		this.element.addItem(icon4);
//		this.element.setStartIndex(2);
//		this.element.setStartIndex(-1);
//		
//		Assert.assertSame(icon3, this.element.getIcon(Point.of(0, 0)));
//		Assert.assertSame(icon4, this.element.getIcon(Point.of(1, 0)));
//		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(2, 0)));
//	}
//	
//	@Test
//	public void testSelect_OneElement() {
//		Icon icon = new Icon(Material.APPLE);
//
//		this.element.addItem(icon);
//		this.element.select(0);
//		
//		Assert.assertSame(SelectModifiers.MAGENTA_GLASS.apply(icon), this.element.getIcon(Point.of(0, 0)));
//		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(1, 0)));
//		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(2, 0)));
//	}
//	
//	@Test
//	public void testSelect_MultipleElements() {
//		Icon icon1 = new Icon(Material.APPLE);
//		Icon icon2 = new Icon(Material.STONE);
//		Icon icon3 = new Icon(Material.INK_SAC);
//
//		this.element.addItem(icon1);
//		this.element.addItem(icon2);
//		this.element.addItem(icon3);
//		this.element.addItem(new Icon(Material.DIRT));
//		this.element.select(0);
//		
//		Assert.assertSame(SelectModifiers.MAGENTA_GLASS.apply(icon1), this.element.getIcon(Point.of(0, 0)));
//		Assert.assertSame(icon2, this.element.getIcon(Point.of(1, 0)));
//		Assert.assertSame(icon3, this.element.getIcon(Point.of(2, 0)));
//	}
//
//	@Test
//	public void testSelect_NegativeOne() {
//		this.element.addItems(GameMode.class);
//		this.element.select(0);
//		this.element.select(-1);
//
//		Assert.assertEquals(InterfaceUtils.convertGameMode(GameMode.CREATIVE), this.element.getIcon(Point.of(0, 0)));
//		Assert.assertEquals(InterfaceUtils.convertGameMode(GameMode.SURVIVAL), this.element.getIcon(Point.of(1, 0)));
//		Assert.assertEquals(InterfaceUtils.convertGameMode(GameMode.ADVENTURE), this.element.getIcon(Point.of(2, 0)));
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void testSelect_OutOfRange() {
//		this.element.addItems(GameMode.class);
//		this.element.select(5);
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void testSelect_Nagative() {
//		this.element.addItems(GameMode.class);
//		this.element.select(-2);
//	}
//	
//	@Test
//	public void testDeselect() {
//		this.element.addItems(GameMode.class);
//		this.element.select(0);
//		this.element.deselect();
//
//		Assert.assertEquals(InterfaceUtils.convertGameMode(GameMode.CREATIVE), this.element.getIcon(Point.of(0, 0)));
//		Assert.assertEquals(InterfaceUtils.convertGameMode(GameMode.SURVIVAL), this.element.getIcon(Point.of(1, 0)));
//		Assert.assertEquals(InterfaceUtils.convertGameMode(GameMode.ADVENTURE), this.element.getIcon(Point.of(2, 0)));
//	}
//	
//	@Test
//	public void testSelect_MultipleSelects() {
//		Icon icon1 = new Icon(Material.APPLE);
//		Icon icon2 = new Icon(Material.STONE);
//		Icon icon3 = new Icon(Material.INK_SAC);
//
//		this.element.addItem(icon1);
//		this.element.addItem(icon2);
//		this.element.addItem(icon3);
//		this.element.addItem(new Icon(Material.DIRT));
//		this.element.select(0);
//		this.element.select(2);
//		
//		Assert.assertSame(icon1, this.element.getIcon(Point.of(0, 0)));
//		Assert.assertSame(icon2, this.element.getIcon(Point.of(1, 0)));
//		Assert.assertSame(SelectModifiers.MAGENTA_GLASS.apply(icon3), this.element.getIcon(Point.of(2, 0)));
//		
//		this.element.deselect();
//		
//		Assert.assertSame(icon1, this.element.getIcon(Point.of(0, 0)));
//		Assert.assertSame(icon2, this.element.getIcon(Point.of(1, 0)));
//		Assert.assertSame(icon3, this.element.getIcon(Point.of(2, 0)));
//		
//		this.element.select(1);
//		
//		Assert.assertSame(icon1, this.element.getIcon(Point.of(0, 0)));
//		Assert.assertSame(SelectModifiers.MAGENTA_GLASS.apply(icon2), this.element.getIcon(Point.of(1, 0)));
//		Assert.assertSame(icon3, this.element.getIcon(Point.of(2, 0)));
//	}
//
//	@Test
//	public void testSetType_Vertical() {
//		Icon icon = new Icon(Material.APPLE);
//
//		this.element.addItem(icon);
//		this.element.setType(Direction.VERTICAL);
//
//		Assert.assertSame(icon, this.element.getIcon(Point.of(0, 0)));
//		Assert.assertSame(icon, this.element.getIcon(Point.of(1, 0)));
//		Assert.assertSame(icon, this.element.getIcon(Point.of(2, 0)));
//
//		this.element.select(0);
//
//		Assert.assertEquals(SelectModifiers.MAGENTA_GLASS.apply(icon), this.element.getIcon(Point.of(0, 0)));
//		Assert.assertEquals(SelectModifiers.MAGENTA_GLASS.apply(icon), this.element.getIcon(Point.of(1, 0)));
//		Assert.assertEquals(SelectModifiers.MAGENTA_GLASS.apply(icon), this.element.getIcon(Point.of(2, 0)));
//
//		this.element.deselect();
//
//		Assert.assertSame(icon, this.element.getIcon(Point.of(0, 0)));
//		Assert.assertSame(icon, this.element.getIcon(Point.of(1, 0)));
//		Assert.assertSame(icon, this.element.getIcon(Point.of(2, 0)));
//	}

}
