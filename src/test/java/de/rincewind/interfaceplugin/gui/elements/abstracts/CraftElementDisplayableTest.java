package de.rincewind.interfaceplugin.gui.elements.abstracts;

import org.bukkit.Material;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.components.DisplayableDisabled;
import de.rincewind.interfaceapi.gui.elements.ElementItem;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.util.Point;
import de.rincewind.interfaceapi.gui.util.creators.ElementCreator;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowEditor;
import de.rincewind.test.TestServer;
import de.rincewind.test.TestWindowSizeable;

public class CraftElementDisplayableTest {

	private TestElement element;

	@BeforeClass
	public static void initInterfaceAPI() {
		TestServer.setup();
	}

	@Before
	public void initElement() {
		this.element = new TestWindowSizeable().elementCreator().newElement(TestElement.class);
	}

	@Test
	public void testDefaultIcon() {
		Assert.assertSame(Icon.AIR, this.element.getIcon());
		Assert.assertSame(Icon.AIR, this.element.getIcon(Point.NULL));
	}

	@Test
	public void testIcon() {
		Icon icon = new Icon(Material.APPLE);
		icon.getLore().setEnd("End");

		this.element.setIcon(icon);

		Assert.assertSame(icon, this.element.getIcon());
		Assert.assertSame(icon, this.element.getIcon(Point.NULL));
		Assert.assertEquals("End", this.element.getIcon(Point.NULL).getLore().getEnd());
	}

	@Test
	public void testPayloadIcon() {
		Object payload = new Object();
		Icon icon = new Icon(Material.APPLE);
		icon.getLore().setEnd("End");

		this.element.setIcon(Displayable.of(icon, payload));

		Assert.assertSame(icon, this.element.getIcon());
		Assert.assertSame(icon, this.element.getIcon(Point.NULL));
		Assert.assertEquals("End", this.element.getIcon(Point.NULL).getLore().getEnd());
	}

	@Test
	public void testDefaultDisabledIcon() {
		Icon icon = new Icon(Material.APPLE);
		icon.getLore().setEnd("End");

		this.element.setIcon(icon);
		this.element.setComponentValue(Element.ENABLED, false);

		Assert.assertSame(icon, this.element.getIcon());
		Assert.assertSame(DisplayableDisabled.default_icon, this.element.getDisabledIcon());
		Assert.assertSame(DisplayableDisabled.default_icon, this.element.getIcon(Point.NULL));
		Assert.assertEquals(DisplayableDisabled.default_icon.getLore().getEnd(), this.element.getIcon(Point.NULL).getLore().getEnd());
	}

	@Test
	public void testDisabledIcon() {
		Icon icon = new Icon(Material.APPLE);
		icon.getLore().setEnd("End");
		Icon disabledIcon = new Icon(Material.BARRIER);
		disabledIcon.getLore().setEnd("EndDisabled");

		this.element.setIcon(icon);
		this.element.setDisabledIcon(disabledIcon);
		this.element.setComponentValue(Element.ENABLED, false);

		Assert.assertSame(icon, this.element.getIcon());
		Assert.assertSame(disabledIcon, this.element.getDisabledIcon());
		Assert.assertSame(disabledIcon, this.element.getIcon(Point.NULL));
		Assert.assertEquals("EndDisabled", this.element.getIcon(Point.NULL).getLore().getEnd());

		this.element.setComponentValue(Element.ENABLED, true);

		Assert.assertSame(icon, this.element.getIcon());
		Assert.assertSame(disabledIcon, this.element.getDisabledIcon());
		Assert.assertSame(icon, this.element.getIcon(Point.NULL));
		Assert.assertEquals("End", this.element.getIcon(Point.NULL).getLore().getEnd());
	}

	@Test
	public void testDisabledIcon_Modifier() {
		Icon icon = new Icon(Material.APPLE);

		this.element.setIcon(icon);
		this.element.setDisabledIconModified(DisplayableDisabled.default_modifier);
		this.element.setComponentValue(Element.ENABLED, false);

		Assert.assertSame(icon, this.element.getIcon());
		Assert.assertNotSame(icon, this.element.getDisabledIcon());
		Assert.assertNotSame(icon, this.element.getIcon(Point.NULL));
		Assert.assertEquals(DisplayableDisabled.default_modifier.apply(icon.clone()), this.element.getIcon(Point.NULL));

		this.element.setComponentValue(Element.ENABLED, true);

		Assert.assertSame(icon, this.element.getIcon());
		Assert.assertSame(icon, this.element.getIcon(Point.NULL));
	}

	@Test
	public void testNotWorkingInstructions() {
		Icon icon = new Icon(Material.APPLE);
		icon.getLore().setEnd("End");

		this.element.setIcon(icon);
		this.element.setInstructions("Instructions");

		Assert.assertEquals("End", this.element.getIcon(Point.NULL).getLore().getEnd());
	}

	@Test
	public void testWorkingInstructions() {
		Icon icon = new Icon(Material.APPLE);
		icon.getLore().setEnd("End");

		this.element.setIcon(icon);
		this.element.setInstructions("Instructions");
		this.element.setWorkInstructions(true);
		this.element.getComponent(Element.INSTRUCTIONS).setEnabled(true);

		Assert.assertEquals("Instructions", this.element.getIcon(Point.NULL).getLore().getEnd());
	}

	@Test
	public void testOverlappingInvisibleElements() {
		TestWindowSizeable window = new TestWindowSizeable();
		ElementCreator creator = window.elementCreator();
		ElementItem element1 = creator.newItem();
		
		element1.setIcon(new Icon(Material.APPLE));
		element1.setVisible(false);
		
		ElementItem element2 = creator.newItem();
		element2.setIcon(new Icon(Material.STICK));
		element2.setVisible(false);

		Assert.assertNull(window.getVisibleElementAt(Point.NULL));
		Assert.assertEquals(window.getColor().asIcon(), window.getIcon(Point.NULL));
	}

	private static class TestElement extends CraftElementDisplayable {

		private String instructions;

		public TestElement(WindowEditor handle) {
			super(handle);

			this.instructions = null;
			this.setWorkInstructions(false);
		}

		@Override
		protected String currentInstructions() {
			return this.instructions;
		}

		public void setInstructions(String instructions) {
			this.instructions = instructions;
		}

	}

}
