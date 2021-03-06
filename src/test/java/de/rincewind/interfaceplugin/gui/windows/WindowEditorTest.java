package de.rincewind.interfaceplugin.gui.windows;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.Sets;

import de.rincewind.interfaceapi.exceptions.ElementEditorException;
import de.rincewind.interfaceapi.gui.elements.ElementItem;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.util.Point;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowEditor;
import de.rincewind.interfaceplugin.gui.elements.CraftElementItem;
import de.rincewind.interfaceplugin.gui.elements.abstracts.CraftElement;
import de.rincewind.interfaceplugin.gui.windows.abstracts.CraftWindowEditor;
import de.rincewind.test.Success;
import de.rincewind.test.TestInventory;
import de.rincewind.test.TestServer;

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

		Assert.assertEquals(new Icon(Material.APPLE), this.window.getIcon(Point.of(0, 0)));
		this.assertIcons(null, Point.NULL);
		
		item.setPoint(Point.of(1, 1));
		
		Assert.assertEquals(new Icon(Material.APPLE), this.window.getIcon(Point.of(1, 1)));
		this.assertIcons(null, Point.of(1, 1));
	}

	@Test(expected = ElementEditorException.class)
	public void testAddElementTwice() {
		CraftElementItem item = new CraftElementItem(this.window);
		this.window.addElement(item);
		this.window.addElement(item);
	}

	@Test(expected = ElementEditorException.class)
	public void testAddElementDifferent() {
		CraftElementItem item = new CraftElementItem(this.window);
		this.window.addElement(item);
		
		new TestWindow().addElement(item);
	}
	
	@Test
	public void testRemoveElement() {
		ElementItem item = this.window.elementCreator().newItem();
		item.setIcon(new Icon(Material.APPLE));
		
		Assert.assertEquals(new Icon(Material.APPLE), this.window.getIcon(Point.of(0, 0)));
		this.assertIcons(null, Point.NULL);

		this.window.removeElement(item);
		this.assertIcons(null);
	}
	
	@Test(expected = Success.class)
	public void testOnElementAdded() {
		this.window.elementCreator().newElement(TestElement1.class);
	}
	
	
	@Test(expected = Success.class)
	public void testOnElementRemoved() {
		this.window.removeElement(this.window.elementCreator().newElement(TestElement2.class));
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
		item1.setPoint(Point.of(2, 1));

		ElementItem item2 = this.window.elementCreator().newItem();
		item2.setIcon(new Icon(Material.ARROW));
		item2.setPoint(Point.of(6, 1));
		item2.setComponentValue(Element.HEIGHT, 4);

		ElementItem item3 = this.window.elementCreator().newItem();
		item3.setIcon(new Icon(Material.BEACON));
		item3.setPoint(Point.of(3, 2));
		item3.setComponentValue(Element.WIDTH, 5);

		Set<Point> occupiedPoints = new HashSet<>();

		for (Point point : Arrays.asList(Point.of(2, 1), Point.of(3, 1), Point.of(2, 2), Point.of(2, 3), Point.of(3, 3))) {
			Assert.assertEquals(new Icon(Material.APPLE), this.window.getIcon(point));
			occupiedPoints.add(point);
		}

		for (Point point : Arrays.asList(Point.of(6, 1), Point.of(6, 3), Point.of(6, 4))) {
			Assert.assertEquals(new Icon(Material.ARROW), this.window.getIcon(point));
			occupiedPoints.add(point);
		}

		for (Point point : Arrays.asList(Point.of(3, 2), Point.of(4, 2), Point.of(5, 2), Point.of(6, 2), Point.of(7, 2))) {
			Assert.assertEquals(new Icon(Material.BEACON), this.window.getIcon(point));
			occupiedPoints.add(point);
		}

		this.assertIcons(null, occupiedPoints.toArray(new Point[0]));

		item2.priorize();

		occupiedPoints = new HashSet<>();

		for (Point point : Arrays.asList(Point.of(2, 1), Point.of(3, 1), Point.of(2, 2), Point.of(2, 3), Point.of(3, 3))) {
			Assert.assertEquals(new Icon(Material.APPLE), this.window.getIcon(point));
			occupiedPoints.add(point);
		}

		for (Point point : Arrays.asList(Point.of(6, 1), Point.of(6, 2), Point.of(6, 3), Point.of(6, 4))) {
			Assert.assertEquals(new Icon(Material.ARROW), this.window.getIcon(point));
			occupiedPoints.add(point);
		}

		for (Point point : Arrays.asList(Point.of(3, 2), Point.of(4, 2), Point.of(5, 2), Point.of(7, 2))) {
			Assert.assertEquals(new Icon(Material.BEACON), this.window.getIcon(point));
			occupiedPoints.add(point);
		}

		this.assertIcons(null, occupiedPoints.toArray(new Point[0]));

		item1.priorize();

		occupiedPoints = new HashSet<>();

		for (Point point : Arrays.asList(Point.of(2, 1), Point.of(3, 1), Point.of(2, 2), Point.of(3, 2), Point.of(2, 3), Point.of(3, 3))) {
			Assert.assertEquals(new Icon(Material.APPLE), this.window.getIcon(point));
			occupiedPoints.add(point);
		}

		for (Point point : Arrays.asList(Point.of(6, 1), Point.of(6, 2), Point.of(6, 3), Point.of(6, 4))) {
			Assert.assertEquals(new Icon(Material.ARROW), this.window.getIcon(point));
			occupiedPoints.add(point);
		}

		for (Point point : Arrays.asList(Point.of(4, 2), Point.of(5, 2), Point.of(7, 2))) {
			Assert.assertEquals(new Icon(Material.BEACON), this.window.getIcon(point));
			occupiedPoints.add(point);
		}

		this.assertIcons(null, occupiedPoints.toArray(new Point[0]));
	}

	@Test
	public void testGetOccupiedPoints() {
		ElementItem item1 = this.window.elementCreator().newItem();
		item1.setIcon(new Icon(Material.APPLE));
		item1.setComponentValue(Element.WIDTH, 2);
		item1.setComponentValue(Element.HEIGHT, 3);
		item1.setPoint(Point.of(2, 1));

		ElementItem item2 = this.window.elementCreator().newItem();
		item2.setIcon(new Icon(Material.ARROW));
		item2.setPoint(Point.of(6, 1));
		item2.setComponentValue(Element.HEIGHT, 4);

		ElementItem item3 = this.window.elementCreator().newItem();
		item3.setIcon(new Icon(Material.BEACON));
		item3.setPoint(Point.of(3, 2));
		item3.setComponentValue(Element.WIDTH, 5);

		Assert.assertEquals(Sets.newHashSet(Point.of(2, 1), Point.of(3, 1), Point.of(2, 2), Point.of(2, 3), Point.of(3, 3)),
				this.window.getOccupiedPoints(item1));
		Assert.assertEquals(Sets.newHashSet(Point.of(6, 1), Point.of(6, 3), Point.of(6, 4)), this.window.getOccupiedPoints(item2));
		Assert.assertEquals(Sets.newHashSet(Point.of(3, 2), Point.of(4, 2), Point.of(5, 2), Point.of(6, 2), Point.of(7, 2)),
				this.window.getOccupiedPoints(item3));

		item2.priorize();

		Assert.assertEquals(Sets.newHashSet(Point.of(2, 1), Point.of(3, 1), Point.of(2, 2), Point.of(2, 3), Point.of(3, 3)),
				this.window.getOccupiedPoints(item1));
		Assert.assertEquals(Sets.newHashSet(Point.of(6, 1), Point.of(6, 2), Point.of(6, 3), Point.of(6, 4)), this.window.getOccupiedPoints(item2));
		Assert.assertEquals(Sets.newHashSet(Point.of(3, 2), Point.of(4, 2), Point.of(5, 2), Point.of(7, 2)), this.window.getOccupiedPoints(item3));
		
		item1.priorize();

		Assert.assertEquals(Sets.newHashSet(Point.of(2, 1), Point.of(3, 1), Point.of(2, 2), Point.of(3, 2), Point.of(2, 3), Point.of(3, 3)),
				this.window.getOccupiedPoints(item1));
		Assert.assertEquals(Sets.newHashSet(Point.of(6, 1), Point.of(6, 2), Point.of(6, 3), Point.of(6, 4)), this.window.getOccupiedPoints(item2));
		Assert.assertEquals(Sets.newHashSet(Point.of(4, 2), Point.of(5, 2), Point.of(7, 2)), this.window.getOccupiedPoints(item3));
	}

	@Test
	public void testRemoveElements() {
		ElementItem item1 = this.window.elementCreator().newItem();
		item1.setIcon(new Icon(Material.APPLE));
		item1.setComponentValue(Element.WIDTH, 2);
		item1.setComponentValue(Element.HEIGHT, 3);
		item1.setPoint(Point.of(2, 1));

		ElementItem item2 = this.window.elementCreator().newItem();
		item2.setIcon(new Icon(Material.ARROW));
		item2.setPoint(Point.of(6, 1));
		item2.setComponentValue(Element.HEIGHT, 4);

		ElementItem item3 = this.window.elementCreator().newItem();
		item3.setIcon(new Icon(Material.BEACON));
		item3.setPoint(Point.of(3, 2));
		item3.setComponentValue(Element.WIDTH, 5);

		Assert.assertEquals(Sets.newHashSet(Point.of(2, 1), Point.of(3, 1), Point.of(2, 2), Point.of(2, 3), Point.of(3, 3)),
				this.window.getOccupiedPoints(item1));
		Assert.assertEquals(Sets.newHashSet(Point.of(6, 1), Point.of(6, 3), Point.of(6, 4)), this.window.getOccupiedPoints(item2));
		Assert.assertEquals(Sets.newHashSet(Point.of(3, 2), Point.of(4, 2), Point.of(5, 2), Point.of(6, 2), Point.of(7, 2)),
				this.window.getOccupiedPoints(item3));

		item2.priorize();

		Assert.assertEquals(Sets.newHashSet(Point.of(2, 1), Point.of(3, 1), Point.of(2, 2), Point.of(2, 3), Point.of(3, 3)),
				this.window.getOccupiedPoints(item1));
		Assert.assertEquals(Sets.newHashSet(Point.of(6, 1), Point.of(6, 2), Point.of(6, 3), Point.of(6, 4)), this.window.getOccupiedPoints(item2));
		Assert.assertEquals(Sets.newHashSet(Point.of(3, 2), Point.of(4, 2), Point.of(5, 2), Point.of(7, 2)), this.window.getOccupiedPoints(item3));
		
		item1.priorize();

		Assert.assertEquals(Sets.newHashSet(Point.of(2, 1), Point.of(3, 1), Point.of(2, 2), Point.of(3, 2), Point.of(2, 3), Point.of(3, 3)),
				this.window.getOccupiedPoints(item1));
		Assert.assertEquals(Sets.newHashSet(Point.of(6, 1), Point.of(6, 2), Point.of(6, 3), Point.of(6, 4)), this.window.getOccupiedPoints(item2));
		Assert.assertEquals(Sets.newHashSet(Point.of(4, 2), Point.of(5, 2), Point.of(7, 2)), this.window.getOccupiedPoints(item3));
	}

	@Test
	public void testDisabledElement() {
		ElementItem item = this.window.elementCreator().newItem();
		item.setIcon(new Icon(Material.APPLE));
		item.setDisabledIcon(new Icon(Material.STICK));
		item.setComponentValue(Element.ENABLED, false);

		Assert.assertEquals(new Icon(Material.STICK), this.window.getIcon(Point.of(0, 0)));
		this.assertIcons(null, Point.of(0, 0));

		item.setComponentValue(Element.ENABLED, true);
		
		Assert.assertEquals(new Icon(Material.APPLE), this.window.getIcon(Point.of(0, 0)));
		this.assertIcons(null, Point.of(0, 0));
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
			super(null);
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
			int y = (int) (bukkitSlot / 9.0D);
			int x = bukkitSlot - (y * 9);

			return Point.of(x, y);
		}

		@Override
		public Inventory newInventory() {
			return new TestInventory("GUI", 9 * 3);
		}

	}
	
	private static class TestElement1 extends CraftElement {

		public TestElement1(WindowEditor handle) {
			super(handle);
		}
		
		@Override
		public void onElementAdded() {
			throw new Success();
		}
		
		@Override
		protected Icon getIcon0(Point point) {
			return new Icon(Material.APPLE);
		}
		
	}
	
	private static class TestElement2 extends CraftElement {

		public TestElement2(WindowEditor handle) {
			super(handle);
		}
		
		@Override
		public void onElementRemoved() {
			throw new Success();
		}
		
		@Override
		protected Icon getIcon0(Point point) {
			return new Icon(Material.APPLE);
		}
		
	}

}
