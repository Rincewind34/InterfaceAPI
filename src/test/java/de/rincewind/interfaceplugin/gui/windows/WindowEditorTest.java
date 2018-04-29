package de.rincewind.interfaceplugin.gui.windows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.junit.Before;
import org.junit.Test;

import de.rincewind.interfaceapi.exceptions.ElementEditorException;
import de.rincewind.interfaceapi.gui.elements.ElementItem;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceplugin.gui.elements.CraftElementItem;
import de.rincewind.interfaceplugin.gui.windows.abstracts.CraftWindowEditor;
import de.rincewind.test.TestInventory;
import junit.framework.Assert;

public class WindowEditorTest {
	
	private CraftWindowEditor window;
	
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
		this.assertIcons(Point.NULL);
		
		item.setPoint(new Point(1, 1));
		
		System.out.println(this.window.getOccupiedPoints(item));
		
		Assert.assertEquals(new Icon(Material.APPLE), this.window.getIcon(new Point(1, 1)));
		this.assertIcons(new Point(1, 1));
	}
	
	@Test(expected = ElementEditorException.class)
	public void testAddElementTwice() {
		CraftElementItem item = new CraftElementItem(this.window);
		this.window.addElement(item);
		this.window.addElement(item);
	}
	
	private void assertIcons(Point... occupiedSlots) {
		List<Point> list = Arrays.asList(occupiedSlots);
		
		for (int i = 0; i < this.window.getPoints().size(); i++) {
			if (list.contains(this.window.getPoint(i))) {
				continue;
			}
			
			Assert.assertEquals(Icon.AIR, this.window.getIcon(this.window.getPoint(i)));
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
