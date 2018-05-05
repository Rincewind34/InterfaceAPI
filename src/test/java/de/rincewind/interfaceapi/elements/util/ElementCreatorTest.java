package de.rincewind.interfaceapi.elements.util;

import org.bukkit.Material;
import org.junit.Before;
import org.junit.Test;

import de.rincewind.interfaceapi.exceptions.ElementCreationException;
import de.rincewind.interfaceapi.gui.components.Modifyable;
import de.rincewind.interfaceapi.gui.elements.abstracts.ElementDisplayable;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceapi.gui.windows.WindowEnchanter;
import de.rincewind.interfaceplugin.gui.elements.abstracts.CraftElement;
import de.rincewind.test.TestWindowSizeable;
import junit.framework.Assert;

public class ElementCreatorTest {
	
	private TestWindowSizeable window;
	
	@Before
	public void initCreator() {
		this.window = new TestWindowSizeable();
	}
	
	@Test
	public void testSimpleCreation() {
		Assert.assertEquals(0, this.window.getElements().size());
		
		this.window.elementCreator().newItem();
		
		Assert.assertEquals(1, this.window.getElements().size());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNullClass() {
		this.window.elementCreator().newElement(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAbstractClass() {
		this.window.elementCreator().newElement(CraftElement.class);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInterfaceClass() {
		this.window.elementCreator().newElement(ElementDisplayable.class);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidConstructor1() {
		this.window.elementCreator().newElement(TestElement1.class);
	}
	
	@Test(expected = ElementCreationException.class)
	public void testInvalidConstructor2() {
		this.window.elementCreator().newElement(TestElement2.class);
	}
	
	@Test(expected = ElementCreationException.class)
	public void testInvalidConstructor3() {
		this.window.elementCreator().newElement(TestElement4.class);
	}
	
	@Test
	public void testValidConstructor() {
		this.window.elementCreator().newElement(TestElement3.class);
	}
	
	private static class TestElement1 extends CraftElement {

		public TestElement1() {
			super(null);
		}

		public TestElement1(Modifyable handle, Object payload) {
			super(handle);
		}

		@Override
		protected Icon getIcon0(Point point) {
			return new Icon(Material.APPLE);
		}
		
	}
	
	private static class TestElement2 extends CraftElement {

		public TestElement2(WindowEnchanter handle) {
			super(handle);
		}

		@Override
		protected Icon getIcon0(Point point) {
			return new Icon(Material.APPLE);
		}
		
	}
	
	private static class TestElement3 extends CraftElement {

		public TestElement3() {
			super(null);
		}

		public TestElement3(Object payload) {
			super(null);
		}

		public TestElement3(Modifyable handle) {
			super(handle);
		}

		@Override
		protected Icon getIcon0(Point point) {
			return new Icon(Material.APPLE);
		}
		
	}
	
	private static class TestElement4 extends CraftElement {

		public TestElement4(Modifyable handle) {
			super(handle);
			
			throw new RuntimeException();
		}

		@Override
		protected Icon getIcon0(Point point) {
			return new Icon(Material.APPLE);
		}
		
	}
	
}