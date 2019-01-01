package de.rincewind.interfaceplugin.gui.elements;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.components.DisplayableDisabled;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.util.Color;
import de.rincewind.interfaceapi.gui.util.Point;
import de.rincewind.interfaceapi.util.InterfaceUtils;
import de.rincewind.test.TestPlayer;
import de.rincewind.test.TestServer;
import de.rincewind.test.TestWindowSizeable;

public class CraftElementMapTest {

	private CraftElementMap element;

	private CraftElementItem buttonNext;
	private CraftElementItem buttonPrev;
	
	@BeforeClass
	public static void initInterfaceAPI() {
		TestServer.setup();
	}

	@Before
	public void initElement() {
		TestWindowSizeable window = new TestWindowSizeable();

		this.element = (CraftElementMap) window.elementCreator().newMap();
		this.element.setComponentValue(Element.WIDTH, 3);
		this.element.setComponentValue(Element.HEIGHT, 2);

		this.buttonNext = (CraftElementItem) this.element.newNextFliper(window.elementCreator(), Point.of(0, 9));
		this.buttonPrev = (CraftElementItem) this.element.newPreviousFliper(window.elementCreator(), Point.of(1, 9));
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
		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(0, 1)));
		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(1, 1)));
		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(2, 1)));

		Assert.assertFalse(this.buttonNext.isEnabled());
		Assert.assertFalse(this.buttonPrev.isEnabled());
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
		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(0, 1)));
		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(1, 1)));
		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(2, 1)));

		Assert.assertFalse(this.buttonNext.isEnabled());
		Assert.assertFalse(this.buttonPrev.isEnabled());
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
		Assert.assertSame(icon4, this.element.getIcon(Point.of(0, 1)));
		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(1, 1)));
		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(2, 1)));

		Assert.assertFalse(this.buttonNext.isEnabled());
		Assert.assertFalse(this.buttonPrev.isEnabled());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddItems_Null() {
		this.element.addItems(null);
	}

	@Test
	public void testAddItems_Enum() {
		this.element.addItems(Stream.of(GameMode.values()).map(Displayable::of).collect(Collectors.toList()));

		Assert.assertEquals(4, this.element.size());
		Assert.assertEquals(InterfaceUtils.convertGameMode(GameMode.CREATIVE), this.element.getIcon(Point.of(0, 0)));
		Assert.assertEquals(InterfaceUtils.convertGameMode(GameMode.SURVIVAL), this.element.getIcon(Point.of(1, 0)));
		Assert.assertEquals(InterfaceUtils.convertGameMode(GameMode.ADVENTURE), this.element.getIcon(Point.of(2, 0)));
		Assert.assertEquals(InterfaceUtils.convertGameMode(GameMode.SPECTATOR), this.element.getIcon(Point.of(0, 1)));
		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(1, 1)));
		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(2, 1)));
	}

	@Test
	public void testAddItems_ToOneNewPage() {
		Icon icon1 = new Icon(Material.APPLE);
		Icon icon2 = new Icon(Material.STONE);
		Icon icon3 = new Icon(Material.INK_SAC);
		Icon icon4 = new Icon(Material.DIRT);
		Icon icon5 = new Icon(Material.RED_WOOL);
		Icon icon6 = new Icon(Material.GREEN_WOOL);
		Icon icon7 = new Icon(Material.BLUE_WOOL);
		Icon icon8 = new Icon(Material.YELLOW_WOOL);

		this.element.addItems(Arrays.asList(icon1, icon2, icon3, icon4, icon5, icon6, icon7, icon8));

		Assert.assertEquals(8, this.element.size());
		Assert.assertSame(icon1, this.element.getIcon(Point.of(0, 0)));
		Assert.assertSame(icon2, this.element.getIcon(Point.of(1, 0)));
		Assert.assertSame(icon3, this.element.getIcon(Point.of(2, 0)));
		Assert.assertSame(icon4, this.element.getIcon(Point.of(0, 1)));
		Assert.assertSame(icon5, this.element.getIcon(Point.of(1, 1)));
		Assert.assertSame(icon6, this.element.getIcon(Point.of(2, 1)));

		Assert.assertTrue(this.buttonNext.isEnabled());
		Assert.assertFalse(this.buttonPrev.isEnabled());

	}

	@Test
	public void testPushFliper_NextOnce_TwoPages() {
		Icon icon1 = new Icon(Material.APPLE);
		Icon icon2 = new Icon(Material.STONE);
		Icon icon3 = new Icon(Material.INK_SAC);
		Icon icon4 = new Icon(Material.DIRT);
		Icon icon5 = new Icon(Material.RED_WOOL);
		Icon icon6 = new Icon(Material.GREEN_WOOL);
		Icon icon7 = new Icon(Material.BLUE_WOOL);
		Icon icon8 = new Icon(Material.YELLOW_WOOL);

		this.element.addItems(Arrays.asList(icon1, icon2, icon3, icon4, icon5, icon6, icon7, icon8));
		this.buttonNext.pushAsButton(new TestPlayer("Test"), ClickType.LEFT);

		Assert.assertEquals(8, this.element.size());
		Assert.assertSame(icon7, this.element.getIcon(Point.of(0, 0)));
		Assert.assertSame(icon8, this.element.getIcon(Point.of(1, 0)));
		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(2, 0)));
		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(0, 1)));
		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(1, 1)));
		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(2, 1)));

		Assert.assertFalse(this.buttonNext.isEnabled());
		Assert.assertTrue(this.buttonPrev.isEnabled());

	}

}
