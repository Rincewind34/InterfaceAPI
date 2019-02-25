package de.rincewind.interfaceplugin.gui.elements;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.rincewind.interfaceapi.gui.components.DisplayableDisabled;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.elements.util.SelectModifiers;
import de.rincewind.interfaceapi.gui.util.Color;
import de.rincewind.interfaceapi.gui.util.Direction;
import de.rincewind.interfaceapi.gui.util.Point;
import de.rincewind.interfaceapi.handling.element.ElementInteractEvent;
import de.rincewind.interfaceapi.util.InterfaceUtils;
import de.rincewind.test.Success;
import de.rincewind.test.TestActionDispalyable;
import de.rincewind.test.TestPlayer;
import de.rincewind.test.TestServer;
import de.rincewind.test.TestWindowSizeable;

public class CraftElementListTest {

	private CraftElementList element;

	@BeforeClass
	public static void initInterfaceAPI() {
		TestServer.setup();
	}

	@Before
	public void initElement() {
		this.element = (CraftElementList) new TestWindowSizeable().elementCreator().newList();
		this.element.setComponentValue(Element.WIDTH, 3);
	}

	@Test
	public void testVarient() {
		Assert.assertEquals(0, this.element.getSize());
		Assert.assertEquals(-1, this.element.getSelectedIndex());
		Assert.assertNull(this.element.getSelected());
		Assert.assertNull(this.element.getSelectedItem());
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

		Assert.assertEquals(1, this.element.getSize());
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

		Assert.assertEquals(4, this.element.getSize());
		Assert.assertSame(icon1, this.element.getIcon(Point.of(0, 0)));
		Assert.assertSame(icon2, this.element.getIcon(Point.of(1, 0)));
		Assert.assertSame(icon3, this.element.getIcon(Point.of(2, 0)));
	}

	@Test
	public void testAddItem_BeforeSelected() {
		Icon icon1 = new Icon(Material.APPLE);
		Icon icon2 = new Icon(Material.STONE);
		Icon icon3 = new Icon(Material.INK_SAC);
		Icon icon4 = new Icon(Material.DIRT);
		Icon icon5 = new Icon(Material.STONE);
		Icon icon6 = new Icon(Material.RED_SAND);
		Icon icon7 = new Icon(Material.GREEN_BED);

		this.element.addItem(icon1);
		this.element.addItem(icon2);
		this.element.addItem(icon3);
		this.element.addItem(icon4);
		this.element.addItem(icon5);
		this.element.addItem(icon6);
		this.element.select(3);

		this.element.addItem(1, icon7);

		Assert.assertEquals(4, this.element.getSelectedIndex());
		Assert.assertSame(icon4, this.element.getSelectedItem());
	}

	@Test
	public void testAddItem_DirectBeforeSelected() {
		Icon icon1 = new Icon(Material.APPLE);
		Icon icon2 = new Icon(Material.STONE);
		Icon icon3 = new Icon(Material.INK_SAC);
		Icon icon4 = new Icon(Material.DIRT);
		Icon icon5 = new Icon(Material.STONE);
		Icon icon6 = new Icon(Material.RED_SAND);
		Icon icon7 = new Icon(Material.GREEN_BED);

		this.element.addItem(icon1);
		this.element.addItem(icon2);
		this.element.addItem(icon3);
		this.element.addItem(icon4);
		this.element.addItem(icon5);
		this.element.addItem(icon6);
		this.element.select(3);

		this.element.addItem(2, icon7);

		Assert.assertEquals(4, this.element.getSelectedIndex());
		Assert.assertEquals(icon4, this.element.getSelectedItem());
	}

	@Test
	public void testAddItem_AtSelected() {
		Icon icon1 = new Icon(Material.APPLE);
		Icon icon2 = new Icon(Material.STONE);
		Icon icon3 = new Icon(Material.INK_SAC);
		Icon icon4 = new Icon(Material.DIRT);
		Icon icon5 = new Icon(Material.STONE);
		Icon icon6 = new Icon(Material.RED_SAND);
		Icon icon7 = new Icon(Material.GREEN_BED);

		this.element.addItem(icon1);
		this.element.addItem(icon2);
		this.element.addItem(icon3);
		this.element.addItem(icon4);
		this.element.addItem(icon5);
		this.element.addItem(icon6);
		this.element.select(3);

		this.element.addItem(3, icon7);

		Assert.assertEquals(4, this.element.getSelectedIndex());
		Assert.assertEquals(icon4, this.element.getSelectedItem());
	}

	@Test
	public void testAddItem_AfterSelected() {
		Icon icon1 = new Icon(Material.APPLE);
		Icon icon2 = new Icon(Material.STONE);
		Icon icon3 = new Icon(Material.INK_SAC);
		Icon icon4 = new Icon(Material.DIRT);
		Icon icon5 = new Icon(Material.STONE);
		Icon icon6 = new Icon(Material.RED_SAND);
		Icon icon7 = new Icon(Material.GREEN_BED);

		this.element.addItem(icon1);
		this.element.addItem(icon2);
		this.element.addItem(icon3);
		this.element.addItem(icon4);
		this.element.addItem(icon5);
		this.element.addItem(icon6);
		this.element.select(3);

		this.element.addItem(5, icon7);

		Assert.assertEquals(3, this.element.getSelectedIndex());
		Assert.assertEquals(icon4, this.element.getSelectedItem());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddItems_Null() {
		this.element.addItems(null);
	}

	@Test
	public void testAddItems_GameMode() {
		this.element.addItems(GameMode.class);

		Assert.assertEquals(4, this.element.getSize());
		Assert.assertEquals(InterfaceUtils.convertGameMode(GameMode.CREATIVE),
				InterfaceUtils.stripInstructions(this.element.getIcon(Point.of(0, 0))));
		Assert.assertEquals(InterfaceUtils.convertGameMode(GameMode.SURVIVAL),
				InterfaceUtils.stripInstructions(this.element.getIcon(Point.of(1, 0))));
		Assert.assertEquals(InterfaceUtils.convertGameMode(GameMode.ADVENTURE),
				InterfaceUtils.stripInstructions(this.element.getIcon(Point.of(2, 0))));
	}

	@Test
	public void testSetStartIndex_MultipleElements1() {
		Icon icon1 = new Icon(Material.APPLE);
		Icon icon2 = new Icon(Material.STONE);
		Icon icon3 = new Icon(Material.INK_SAC);
		Icon icon4 = new Icon(Material.DIRT);

		this.element.addItem(icon1);
		this.element.addItem(icon2);
		this.element.addItem(icon3);
		this.element.addItem(icon4);
		this.element.setStartIndex(1);

		Assert.assertSame(icon2, this.element.getIcon(Point.of(0, 0)));
		Assert.assertSame(icon3, this.element.getIcon(Point.of(1, 0)));
		Assert.assertSame(icon4, this.element.getIcon(Point.of(2, 0)));
	}

	@Test
	public void testSetStartIndex_MultipleElements2() {
		Icon icon1 = new Icon(Material.APPLE);
		Icon icon2 = new Icon(Material.STONE);
		Icon icon3 = new Icon(Material.INK_SAC);
		Icon icon4 = new Icon(Material.DIRT);

		this.element.addItem(icon1);
		this.element.addItem(icon2);
		this.element.addItem(icon3);
		this.element.addItem(icon4);
		this.element.setStartIndex(2);

		Assert.assertSame(icon3, this.element.getIcon(Point.of(0, 0)));
		Assert.assertSame(icon4, this.element.getIcon(Point.of(1, 0)));
		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(2, 0)));
	}

	@Test
	public void testSetStartIndex_OneElement() {
		Icon icon = new Icon(Material.APPLE);

		this.element.addItem(icon);
		this.element.setStartIndex(2);

		Assert.assertEquals(0, this.element.getStartIndex());
		Assert.assertSame(icon, this.element.getIcon(Point.of(0, 0)));
		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(1, 0)));
		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(2, 0)));
	}

	@Test
	public void testSetStartIndex_Negative() {
		Icon icon = new Icon(Material.APPLE);

		this.element.addItem(icon);
		this.element.setStartIndex(-3);

		Assert.assertEquals(0, this.element.getStartIndex());
		Assert.assertSame(icon, this.element.getIcon(Point.of(0, 0)));
		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(1, 0)));
		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(2, 0)));
	}

	@Test
	public void testSetStartIndex_PersitantNegative() {
		Icon icon1 = new Icon(Material.APPLE);
		Icon icon2 = new Icon(Material.STONE);
		Icon icon3 = new Icon(Material.INK_SAC);
		Icon icon4 = new Icon(Material.DIRT);

		this.element.addItem(icon1);
		this.element.addItem(icon2);
		this.element.addItem(icon3);
		this.element.addItem(icon4);
		this.element.setStartIndex(2);
		this.element.setStartIndex(-1);

		Assert.assertSame(icon3, this.element.getIcon(Point.of(0, 0)));
		Assert.assertSame(icon4, this.element.getIcon(Point.of(1, 0)));
		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(2, 0)));
	}

	@Test
	public void testSelect_OneElement() {
		Icon icon = new Icon(Material.APPLE);

		this.element.addItem(icon);
		this.element.select(0);

		Assert.assertEquals(SelectModifiers.MAGENTA_GLASS.apply(new Icon(Material.APPLE)),
				InterfaceUtils.stripInstructions(this.element.getIcon(Point.of(0, 0))));
		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(1, 0)));
		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(2, 0)));
	}

	@Test
	public void testSelect_MultipleElements() {
		Icon icon1 = new Icon(Material.APPLE);
		Icon icon2 = new Icon(Material.STONE);
		Icon icon3 = new Icon(Material.INK_SAC);

		this.element.addItem(icon1.clone());
		this.element.addItem(icon2);
		this.element.addItem(icon3);
		this.element.addItem(new Icon(Material.DIRT));
		this.element.select(0);

		Assert.assertEquals(SelectModifiers.MAGENTA_GLASS.apply(icon1),
				InterfaceUtils.stripInstructions(this.element.getIcon(Point.of(0, 0))));
		Assert.assertSame(icon2, this.element.getIcon(Point.of(1, 0)));
		Assert.assertSame(icon3, this.element.getIcon(Point.of(2, 0)));
	}

	@Test
	public void testSelect_NegativeOne() {
		this.element.addItems(GameMode.class);
		this.element.select(0);
		this.element.select(-1);

		Assert.assertEquals(InterfaceUtils.convertGameMode(GameMode.CREATIVE),
				InterfaceUtils.stripInstructions(this.element.getIcon(Point.of(0, 0))));
		Assert.assertEquals(InterfaceUtils.convertGameMode(GameMode.SURVIVAL),
				InterfaceUtils.stripInstructions(this.element.getIcon(Point.of(1, 0))));
		Assert.assertEquals(InterfaceUtils.convertGameMode(GameMode.ADVENTURE),
				InterfaceUtils.stripInstructions(this.element.getIcon(Point.of(2, 0))));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSelect_OutOfRange() {
		this.element.addItems(GameMode.class);
		this.element.select(5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSelect_Nagative() {
		this.element.addItems(GameMode.class);
		this.element.select(-2);
	}

	@Test
	public void testDeselect() {
		this.element.addItems(GameMode.class);
		this.element.select(0);
		this.element.deselect();

		Assert.assertEquals(InterfaceUtils.convertGameMode(GameMode.CREATIVE),
				InterfaceUtils.stripInstructions(this.element.getIcon(Point.of(0, 0))));
		Assert.assertEquals(InterfaceUtils.convertGameMode(GameMode.SURVIVAL),
				InterfaceUtils.stripInstructions(this.element.getIcon(Point.of(1, 0))));
		Assert.assertEquals(InterfaceUtils.convertGameMode(GameMode.ADVENTURE),
				InterfaceUtils.stripInstructions(this.element.getIcon(Point.of(2, 0))));
	}

	@Test
	public void testSelect_MultipleSelects() {
		Icon icon1 = new Icon(Material.APPLE);
		Icon icon2 = new Icon(Material.STONE);
		Icon icon3 = new Icon(Material.INK_SAC);

		this.element.addItem(icon1.clone());
		this.element.addItem(icon2.clone());
		this.element.addItem(icon3.clone());
		this.element.addItem(new Icon(Material.DIRT));
		this.element.select(0);
		this.element.select(2);

		Assert.assertEquals(icon1, InterfaceUtils.stripInstructions(this.element.getIcon(Point.of(0, 0))));
		Assert.assertEquals(icon2, InterfaceUtils.stripInstructions(this.element.getIcon(Point.of(1, 0))));
		Assert.assertEquals(SelectModifiers.MAGENTA_GLASS.apply(icon3),
				InterfaceUtils.stripInstructions(this.element.getIcon(Point.of(2, 0))));

		this.element.deselect();

		Assert.assertEquals(icon1, InterfaceUtils.stripInstructions(this.element.getIcon(Point.of(0, 0))));
		Assert.assertEquals(icon2, InterfaceUtils.stripInstructions(this.element.getIcon(Point.of(1, 0))));
		Assert.assertEquals(icon3, InterfaceUtils.stripInstructions(this.element.getIcon(Point.of(2, 0))));

		this.element.select(1);

		Assert.assertEquals(icon1, InterfaceUtils.stripInstructions(this.element.getIcon(Point.of(0, 0))));
		Assert.assertEquals(SelectModifiers.MAGENTA_GLASS.apply(icon2),
				InterfaceUtils.stripInstructions(this.element.getIcon(Point.of(1, 0))));
		Assert.assertEquals(icon3, InterfaceUtils.stripInstructions(this.element.getIcon(Point.of(2, 0))));
	}

	@Test
	public void testSetType_Vertical() {
		Icon icon = new Icon(Material.APPLE);

		this.element.addItem(icon.clone());
		this.element.setType(Direction.VERTICAL);

		Assert.assertNotSame(icon, this.element.getIcon(Point.of(0, 0))); // Icon was cloned
		Assert.assertEquals(icon, InterfaceUtils.stripInstructions(this.element.getIcon(Point.of(0, 0))));
		Assert.assertEquals(icon, InterfaceUtils.stripInstructions(this.element.getIcon(Point.of(1, 0))));
		Assert.assertEquals(icon, InterfaceUtils.stripInstructions(this.element.getIcon(Point.of(2, 0))));

		this.element.select(0);

		Assert.assertEquals(SelectModifiers.MAGENTA_GLASS.apply(icon),
				InterfaceUtils.stripInstructions(this.element.getIcon(Point.of(0, 0))));
		Assert.assertEquals(SelectModifiers.MAGENTA_GLASS.apply(icon),
				InterfaceUtils.stripInstructions(this.element.getIcon(Point.of(1, 0))));
		Assert.assertEquals(SelectModifiers.MAGENTA_GLASS.apply(icon),
				InterfaceUtils.stripInstructions(this.element.getIcon(Point.of(2, 0))));

		this.element.deselect();

		Assert.assertEquals(icon, InterfaceUtils.stripInstructions(this.element.getIcon(Point.of(0, 0))));
		Assert.assertEquals(icon, InterfaceUtils.stripInstructions(this.element.getIcon(Point.of(1, 0))));
		Assert.assertEquals(icon, InterfaceUtils.stripInstructions(this.element.getIcon(Point.of(2, 0))));
	}

	@Test(expected = Success.class)
	public void testActionItems_Interact() {
		Icon icon1 = new Icon(Material.APPLE);
		Icon icon2 = new Icon(Material.STICK);
		TestActionDispalyable icon3 = new TestActionDispalyable();
		Icon icon4 = new Icon(Material.WHITE_WOOL);

		this.element.addItem(icon1);
		this.element.addItem(icon2);
		this.element.addItem(icon3);
		this.element.addItem(icon4);

		this.element.getEventManager().callEvent(ElementInteractEvent.class,
				new ElementInteractEvent(element, new TestPlayer("test"), Point.of(2, 0), ClickType.SHIFT_LEFT, null));
	}

	@Test(expected = Success.class)
	public void testActionItems_Interact_SelectedSame() {
		Icon icon1 = new Icon(Material.APPLE);
		Icon icon2 = new Icon(Material.STICK);
		TestActionDispalyable icon3 = new TestActionDispalyable();
		Icon icon4 = new Icon(Material.WHITE_WOOL);

		this.element.addItem(icon1);
		this.element.addItem(icon2);
		this.element.addItem(icon3);
		this.element.addItem(icon4);
		this.element.select(2);

		this.element.getEventManager().callEvent(ElementInteractEvent.class,
				new ElementInteractEvent(element, new TestPlayer("test"), Point.of(2, 0), ClickType.SHIFT_LEFT, null));
	}

	@Test(expected = Success.class)
	public void testActionItems_Interact_SelectedOther() {
		Icon icon1 = new Icon(Material.APPLE);
		Icon icon2 = new Icon(Material.STICK);
		TestActionDispalyable icon3 = new TestActionDispalyable();
		Icon icon4 = new Icon(Material.WHITE_WOOL);

		this.element.addItem(icon1);
		this.element.addItem(icon2);
		this.element.addItem(icon3);
		this.element.addItem(icon4);
		this.element.select(0);

		this.element.getEventManager().callEvent(ElementInteractEvent.class,
				new ElementInteractEvent(element, new TestPlayer("test"), Point.of(2, 0), ClickType.SHIFT_LEFT, null));
	}

	@Test(expected = Success.class)
	public void testActionItems_Interact_Scrolled() {
		Icon icon1 = new Icon(Material.APPLE);
		Icon icon2 = new Icon(Material.STICK);
		TestActionDispalyable icon3 = new TestActionDispalyable();
		Icon icon4 = new Icon(Material.WHITE_WOOL);

		this.element.addItem(icon1);
		this.element.addItem(icon2);
		this.element.addItem(icon3);
		this.element.addItem(icon4);
		this.element.setStartIndex(2);

		this.element.getEventManager().callEvent(ElementInteractEvent.class,
				new ElementInteractEvent(element, new TestPlayer("test"), Point.of(0, 0), ClickType.SHIFT_LEFT, null));
	}

	@Test
	public void testRemoveItem_One() {
		Icon icon = new Icon(Material.APPLE);

		this.element.addItem(icon);
		this.element.removeItem(icon);

		Assert.assertEquals(0, this.element.getSize());
		Assert.assertEquals(-1, this.element.getSelectedIndex());
		Assert.assertNull(this.element.getSelected());

		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(0, 0)));
		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(1, 0)));
		Assert.assertEquals(Color.TRANSLUCENT.asIcon(), this.element.getIcon(Point.of(2, 0)));
	}

	@Test
	public void testRemoveItem_Multiple() {
		Icon icon1 = new Icon(Material.APPLE);
		Icon icon2 = new Icon(Material.STONE);
		Icon icon3 = new Icon(Material.INK_SAC);
		Icon icon4 = new Icon(Material.DIRT);

		this.element.addItem(icon1);
		this.element.addItem(icon2);
		this.element.addItem(icon3);
		this.element.addItem(icon4);
		
		this.element.removeItem(icon2);

		Assert.assertEquals(3, this.element.getSize());
		Assert.assertSame(icon1, this.element.getIcon(Point.of(0, 0)));
		Assert.assertSame(icon3, this.element.getIcon(Point.of(1, 0)));
		Assert.assertSame(icon4, this.element.getIcon(Point.of(2, 0)));
	}

	@Test
	public void testRemoveItem_BeforeSelected() {
		Icon icon1 = new Icon(Material.APPLE);
		Icon icon2 = new Icon(Material.YELLOW_BED);
		Icon icon3 = new Icon(Material.INK_SAC);
		Icon icon4 = new Icon(Material.DIRT);
		Icon icon5 = new Icon(Material.STONE);
		Icon icon6 = new Icon(Material.RED_SAND);
		Icon icon7 = new Icon(Material.GREEN_BED);

		this.element.addItem(icon1);
		this.element.addItem(icon2);
		this.element.addItem(icon3);
		this.element.addItem(icon4);
		this.element.addItem(icon5);
		this.element.addItem(icon6);
		this.element.addItem(icon7);
		this.element.select(3);

		this.element.removeItem(icon2);

		Assert.assertEquals(2, this.element.getSelectedIndex());
		Assert.assertSame(icon4, this.element.getSelectedItem());
	}

	@Test
	public void testRemoveItem_DirectBeforeSelected() {
		Icon icon1 = new Icon(Material.APPLE);
		Icon icon2 = new Icon(Material.YELLOW_BED);
		Icon icon3 = new Icon(Material.INK_SAC);
		Icon icon4 = new Icon(Material.DIRT);
		Icon icon5 = new Icon(Material.STONE);
		Icon icon6 = new Icon(Material.RED_SAND);
		Icon icon7 = new Icon(Material.GREEN_BED);

		this.element.addItem(icon1);
		this.element.addItem(icon2);
		this.element.addItem(icon3);
		this.element.addItem(icon4);
		this.element.addItem(icon5);
		this.element.addItem(icon6);
		this.element.addItem(icon7);
		this.element.select(3);

		this.element.removeItem(icon3);

		Assert.assertEquals(2, this.element.getSelectedIndex());
		Assert.assertEquals(icon4, this.element.getSelectedItem());
	}

	@Test
	public void testRemoveItem_AtSelected() {
		Icon icon1 = new Icon(Material.APPLE);
		Icon icon2 = new Icon(Material.YELLOW_BED);
		Icon icon3 = new Icon(Material.INK_SAC);
		Icon icon4 = new Icon(Material.DIRT);
		Icon icon5 = new Icon(Material.STONE);
		Icon icon6 = new Icon(Material.RED_SAND);
		Icon icon7 = new Icon(Material.GREEN_BED);

		this.element.addItem(icon1);
		this.element.addItem(icon2);
		this.element.addItem(icon3);
		this.element.addItem(icon4);
		this.element.addItem(icon5);
		this.element.addItem(icon6);
		this.element.addItem(icon7);
		this.element.select(4);

		this.element.removeItem(icon5);
		
		Assert.assertFalse(this.element.isSelected());
	}

	@Test
	public void testRemoveItem_AfterSelected() {
		Icon icon1 = new Icon(Material.APPLE);
		Icon icon2 = new Icon(Material.YELLOW_BED);
		Icon icon3 = new Icon(Material.INK_SAC);
		Icon icon4 = new Icon(Material.DIRT);
		Icon icon5 = new Icon(Material.STONE);
		Icon icon6 = new Icon(Material.RED_SAND);

		this.element.addItem(icon1);
		this.element.addItem(icon2);
		this.element.addItem(icon3);
		this.element.addItem(icon4);
		this.element.addItem(icon5);
		this.element.addItem(icon6);
		this.element.select(3);

		this.element.removeItem(icon5);

		Assert.assertEquals(3, this.element.getSelectedIndex());
		Assert.assertEquals(icon4, this.element.getSelectedItem());
	}

}
