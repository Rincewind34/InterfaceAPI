package de.rincewind.interfaceplugin.gui.elements;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.rincewind.interfaceapi.handling.element.ElementStackChangeEvent;
import de.rincewind.interfaceplugin.InterfacePlugin;
import de.rincewind.test.TestPlayer;
import de.rincewind.test.TestServer;
import de.rincewind.test.TestWindowSizeable;

public class CraftElementInputTest {

	private CraftElementInput element;
	@SuppressWarnings("unused")
	private TestWindowSizeable window;

	@BeforeClass
	public static void initInterfaceAPI() {
		InterfacePlugin.debugOutput = false;
		InterfacePlugin.registerConverter();
		TestServer.setup();
	}

	@Before
	public void initElement() {
		this.element = (CraftElementInput) (this.window = new TestWindowSizeable()).elementCreator().newInput();
	}

	@Test
	public void testVarient() {
		Assert.assertFalse(this.element.isEmpty());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testElementEvent_ItemInput() {
		this.element.getEventManager().callEvent(ElementStackChangeEvent.class,
				new ElementStackChangeEvent(this.element, new TestPlayer("Test"), null, null, null, null));
	}

}
