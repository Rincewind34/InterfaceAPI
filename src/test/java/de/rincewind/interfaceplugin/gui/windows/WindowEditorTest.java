package de.rincewind.interfaceplugin.gui.windows;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Sets;

import de.rincewind.interfaceapi.exceptions.ElementEditorException;
import de.rincewind.interfaceapi.gui.elements.ElementItem;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceplugin.gui.elements.CraftElementItem;
import de.rincewind.interfaceplugin.gui.windows.abstracts.CraftWindowEditor;
import de.rincewind.test.TestInventory;
import de.rincewind.test.TestServer;
import junit.framework.Assert;

public class WindowEditorTest {

	private CraftWindowEditor window;
	
	@BeforeClass
	public static void initServer() {
		TestServer.setup();
	}
	
	@Before
	public void initWindow() {
		this.window = new TestWindow();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddElementNull() {
		this.window.addElement(null);
	}

	@Test
	public void testAddElement() {
		ElementItem item = this.window.elementCreator().newItem();
		item.setIcon(new Icon(Material.APPLE));

		Assert.assertEquals(1, this.window.getElements().size());
		Assert.assertSame(item, this.window.getElements().get(0));
		Assert.assertSame(item, this.window.getVisibleElementAt(Point.NULL));
		Assert.assertEquals(1, this.window.getElementsAt(Point.NULL).size());
		Assert.assertSame(item, this.window.getElementsAt(Point.NULL).iterator().next());

		Assert.assertEquals(new Icon(Material.APPLE), this.window.getIcon(new Point(0, 0)));
		this.assertIcons(Icon.AIR, Point.NULL);

		item.setPoint(new Point(1, 1));

		Assert.assertEquals(new Icon(Material.APPLE), this.window.getIcon(new Point(1, 1)));
		this.assertIcons(Icon.AIR, new Point(1, 1));
	}

	@Test(expected = ElementEditorException.class)
	public void testAddElementTwice() {
		CraftElementItem item = new CraftElementItem(this.window);
		this.window.addElement(item);
		this.window.addElement(item);
	}
	
	@Test
	public void testRemoveElement() {
		ElementItem item = this.window.elementCreator().newItem();
		item.setIcon(new Icon(Material.APPLE));
		
		Assert.assertEquals(new Icon(Material.APPLE), this.window.getIcon(new Point(0, 0)));
		this.assertIcons(Icon.AIR, Point.NULL);

		this.window.removeElement(item);
		this.assertIcons(Icon.AIR);
	}

	/**
	 * <pre>
	 * X X X X X X X X X
	 * X X 1 1 X X 2 X X
	 * X X 1 3 3 3 3 3 X
	 * X X 1 1 X X 2 X X
	 * X X X X X X 2 X X
	 * X X X X X X X X X
	 * </pre>
	 */
	@Test
	public void testPriorize() {
		ElementItem item1 = this.window.elementCreator().newItem();
		item1.setIcon(new Icon(Material.APPLE));
		item1.setComponentValue(Element.WIDTH, 2);
		item1.setComponentValue(Element.HEIGHT, 3);
		item1.setPoint(new Point(2, 1));

		ElementItem item2 = this.window.elementCreator().newItem();
		item2.setIcon(new Icon(Material.ARROW));
		item2.setPoint(new Point(6, 1));
		item2.setComponentValue(Element.HEIGHT, 4);

		ElementItem item3 = this.window.elementCreator().newItem();
		item3.setIcon(new Icon(Material.BEACON));
		item3.setPoint(new Point(3, 2));
		item3.setComponentValue(Element.WIDTH, 5);

		Set<Point> occupiedPoints = new HashSet<>();

		for (Point point : Arrays.asList(new Point(2, 1), new Point(3, 1), new Point(2, 2), new Point(2, 3), new Point(3, 3))) {
			Assert.assertEquals(new Icon(Material.APPLE), this.window.getIcon(point));
			occupiedPoints.add(point);
		}

		for (Point point : Arrays.asList(new Point(6, 1), new Point(6, 3), new Point(6, 4))) {
			Assert.assertEquals(new Icon(Material.ARROW), this.window.getIcon(point));
			occupiedPoints.add(point);
		}

		for (Point point : Arrays.asList(new Point(3, 2), new Point(4, 2), new Point(5, 2), new Point(6, 2), new Point(7, 2))) {
			Assert.assertEquals(new Icon(Material.BEACON), this.window.getIcon(point));
			occupiedPoints.add(point);
		}

		this.assertIcons(Icon.AIR, occupiedPoints.toArray(new Point[0]));

		item2.priorize();

		occupiedPoints = new HashSet<>();

		for (Point point : Arrays.asList(new Point(2, 1), new Point(3, 1), new Point(2, 2), new Point(2, 3), new Point(3, 3))) {
			Assert.assertEquals(new Icon(Material.APPLE), this.window.getIcon(point));
			occupiedPoints.add(point);
		}

		for (Point point : Arrays.asList(new Point(6, 1), new Point(6, 2), new Point(6, 3), new Point(6, 4))) {
			Assert.assertEquals(new Icon(Material.ARROW), this.window.getIcon(point));
			occupiedPoints.add(point);
		}

		for (Point point : Arrays.asList(new Point(3, 2), new Point(4, 2), new Point(5, 2), new Point(7, 2))) {
			Assert.assertEquals(new Icon(Material.BEACON), this.window.getIcon(point));
			occupiedPoints.add(point);
		}

		this.assertIcons(Icon.AIR, occupiedPoints.toArray(new Point[0]));

		item1.priorize();

		occupiedPoints = new HashSet<>();

		for (Point point : Arrays.asList(new Point(2, 1), new Point(3, 1), new Point(2, 2), new Point(3, 2), new Point(2, 3), new Point(3, 3))) {
			Assert.assertEquals(new Icon(Material.APPLE), this.window.getIcon(point));
			occupiedPoints.add(point);
		}

		for (Point point : Arrays.asList(new Point(6, 1), new Point(6, 2), new Point(6, 3), new Point(6, 4))) {
			Assert.assertEquals(new Icon(Material.ARROW), this.window.getIcon(point));
			occupiedPoints.add(point);
		}

		for (Point point : Arrays.asList(new Point(4, 2), new Point(5, 2), new Point(7, 2))) {
			Assert.assertEquals(new Icon(Material.BEACON), this.window.getIcon(point));
			occupiedPoints.add(point);
		}

		this.assertIcons(Icon.AIR, occupiedPoints.toArray(new Point[0]));
	}

	@Test
	public void testGetOccupiedPoints() {
		ElementItem item1 = this.window.elementCreator().newItem();
		item1.setIcon(new Icon(Material.APPLE));
		item1.setComponentValue(Element.WIDTH, 2);
		item1.setComponentValue(Element.HEIGHT, 3);
		item1.setPoint(new Point(2, 1));

		ElementItem item2 = this.window.elementCreator().newItem();
		item2.setIcon(new Icon(Material.ARROW));
		item2.setPoint(new Point(6, 1));
		item2.setComponentValue(Element.HEIGHT, 4);

		ElementItem item3 = this.window.elementCreator().newItem();
		item3.setIcon(new Icon(Material.BEACON));
		item3.setPoint(new Point(3, 2));
		item3.setComponentValue(Element.WIDTH, 5);

		Assert.assertEquals(Sets.newHashSet(new Point(2, 1), new Point(3, 1), new Point(2, 2), new Point(2, 3), new Point(3, 3)),
				this.window.getOccupiedPoints(item1));
		Assert.assertEquals(Sets.newHashSet(new Point(6, 1), new Point(6, 3), new Point(6, 4)), this.window.getOccupiedPoints(item2));
		Assert.assertEquals(Sets.newHashSet(new Point(3, 2), new Point(4, 2), new Point(5, 2), new Point(6, 2), new Point(7, 2)),
				this.window.getOccupiedPoints(item3));

		item2.priorize();

		Assert.assertEquals(Sets.newHashSet(new Point(2, 1), new Point(3, 1), new Point(2, 2), new Point(2, 3), new Point(3, 3)),
				this.window.getOccupiedPoints(item1));
		Assert.assertEquals(Sets.newHashSet(new Point(6, 1), new Point(6, 2), new Point(6, 3), new Point(6, 4)), this.window.getOccupiedPoints(item2));
		Assert.assertEquals(Sets.newHashSet(new Point(3, 2), new Point(4, 2), new Point(5, 2), new Point(7, 2)), this.window.getOccupiedPoints(item3));
		
		item1.priorize();

		Assert.assertEquals(Sets.newHashSet(new Point(2, 1), new Point(3, 1), new Point(2, 2), new Point(3, 2), new Point(2, 3), new Point(3, 3)),
				this.window.getOccupiedPoints(item1));
		Assert.assertEquals(Sets.newHashSet(new Point(6, 1), new Point(6, 2), new Point(6, 3), new Point(6, 4)), this.window.getOccupiedPoints(item2));
		Assert.assertEquals(Sets.newHashSet(new Point(4, 2), new Point(5, 2), new Point(7, 2)), this.window.getOccupiedPoints(item3));
	}

	@Test
	public void testRemoveElements() {
		ElementItem item1 = this.window.elementCreator().newItem();
		item1.setIcon(new Icon(Material.APPLE));
		item1.setComponentValue(Element.WIDTH, 2);
		item1.setComponentValue(Element.HEIGHT, 3);
		item1.setPoint(new Point(2, 1));

		ElementItem item2 = this.window.elementCreator().newItem();
		item2.setIcon(new Icon(Material.ARROW));
		item2.setPoint(new Point(6, 1));
		item2.setComponentValue(Element.HEIGHT, 4);

		ElementItem item3 = this.window.elementCreator().newItem();
		item3.setIcon(new Icon(Material.BEACON));
		item3.setPoint(new Point(3, 2));
		item3.setComponentValue(Element.WIDTH, 5);

		Assert.assertEquals(Sets.newHashSet(new Point(2, 1), new Point(3, 1), new Point(2, 2), new Point(2, 3), new Point(3, 3)),
				this.window.getOccupiedPoints(item1));
		Assert.assertEquals(Sets.newHashSet(new Point(6, 1), new Point(6, 3), new Point(6, 4)), this.window.getOccupiedPoints(item2));
		Assert.assertEquals(Sets.newHashSet(new Point(3, 2), new Point(4, 2), new Point(5, 2), new Point(6, 2), new Point(7, 2)),
				this.window.getOccupiedPoints(item3));

		item2.priorize();

		Assert.assertEquals(Sets.newHashSet(new Point(2, 1), new Point(3, 1), new Point(2, 2), new Point(2, 3), new Point(3, 3)),
				this.window.getOccupiedPoints(item1));
		Assert.assertEquals(Sets.newHashSet(new Point(6, 1), new Point(6, 2), new Point(6, 3), new Point(6, 4)), this.window.getOccupiedPoints(item2));
		Assert.assertEquals(Sets.newHashSet(new Point(3, 2), new Point(4, 2), new Point(5, 2), new Point(7, 2)), this.window.getOccupiedPoints(item3));
		
		item1.priorize();

		Assert.assertEquals(Sets.newHashSet(new Point(2, 1), new Point(3, 1), new Point(2, 2), new Point(3, 2), new Point(2, 3), new Point(3, 3)),
				this.window.getOccupiedPoints(item1));
		Assert.assertEquals(Sets.newHashSet(new Point(6, 1), new Point(6, 2), new Point(6, 3), new Point(6, 4)), this.window.getOccupiedPoints(item2));
		Assert.assertEquals(Sets.newHashSet(new Point(4, 2), new Point(5, 2), new Point(7, 2)), this.window.getOccupiedPoints(item3));
	}

	private void assertIcons(Icon icon, Point... occupiedSlots) {
		List<Point> list = Arrays.asList(occupiedSlots);

		for (int i = 0; i < this.window.getPoints().size(); i++) {
			if (list.contains(this.window.getPoint(i))) {
				continue;
			}

			Assert.assertEquals(icon, this.window.getIcon(this.window.getPoint(i)));
		}
	}

	private static class TestWindow extends CraftWindowEditor {

		public TestWindow() {
			this.createBukkitInventory();
		}

		@Override
		public Set<Point> getPoints() {
			return Collections.unmodifiableSet(Point.NULL.square(9, 3));
		}

		@Override
		public int getSlot(Point point) {
			return point.getX() + (9 * point.getY());
		}

		@Override
		public Point getPoint(int bukkitSlot) {
			int y = (int) ((double) bukkitSlot / 9.0D);
			int x = bukkitSlot - (y * 9);

			return new Point(x, y);
		}

		@Override
		public Inventory newInventory() {
			return new TestInventory("GUI", 9 * 3);
		}

	}

}
