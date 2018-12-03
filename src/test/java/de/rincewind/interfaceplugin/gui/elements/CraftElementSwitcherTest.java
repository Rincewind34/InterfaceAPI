package de.rincewind.interfaceplugin.gui.elements;

import java.util.stream.Stream;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.components.DisplayableDisabled;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.util.Point;
import de.rincewind.test.TestWindowSizeable;
public class CraftElementSwitcherTest {

	private CraftElementSwitcher element;

	@Before
	public void initElement() {
		this.element = (CraftElementSwitcher) new TestWindowSizeable().elementCreator().newSwitcher();
	}
	
	@Test
	public void testVarient() {
		Assert.assertEquals(0, this.element.size());
		Assert.assertEquals(-1, this.element.getSwitchIndex());
		Assert.assertNull(this.element.getCurrent());
		Assert.assertNull(this.element.getCurrentSwitch());
		Assert.assertEquals(DisplayableDisabled.default_icon, this.element.getDisabledIcon());
	}
	
	@Test
	public void testAddSwitch_One() {
		Object payload = new Object();
		Icon icon = new Icon(Material.APPLE);
		Displayable displayable = Displayable.of(icon, payload);
		
		this.element.addSwitch(displayable);
		
		Assert.assertEquals(1, this.element.size());
		Assert.assertEquals(0, this.element.getSwitchIndex());
		Assert.assertSame(payload, this.element.getCurrent());
		Assert.assertSame(displayable, this.element.getCurrentSwitch());
		
		Assert.assertSame(icon, this.element.getIcon(Point.NULL));
	}
	
	@Test
	public void testAddSwitch_Multiple() {
		Icon icon = new Icon(Material.APPLE);
		
		this.element.addSwitch(icon);
		this.element.addSwitch(Displayable.of(new Object()));
		this.element.addSwitch(Displayable.of(new Icon(Material.STONE)));
		
		Assert.assertEquals(3, this.element.size());
		Assert.assertEquals(0, this.element.getSwitchIndex());
		Assert.assertSame(icon, this.element.getCurrent());
		Assert.assertSame(icon, this.element.getCurrentSwitch());
		
		Assert.assertSame(icon, this.element.getIcon(Point.NULL));
	}
	
	@Test
	public void testAddSwitches() {
		Icon icon = new Icon(Material.APPLE);
		
		this.element.addSwitches(icon, Displayable.of(new Object()), Displayable.of(new Icon(Material.STONE)));
		
		Assert.assertEquals(3, this.element.size());
		Assert.assertEquals(0, this.element.getSwitchIndex());
		Assert.assertSame(icon, this.element.getCurrent());
		Assert.assertSame(icon, this.element.getCurrentSwitch());
		
		Assert.assertSame(icon, this.element.getIcon(Point.NULL));
	}
	
	@Test
	public void testAddSwitches_Enum() {
		Stream.of(GameMode.values()).map(Displayable::of).forEach(this.element::addSwitch);
		
		Assert.assertEquals(4, this.element.size());
		Assert.assertEquals(0, this.element.getSwitchIndex());
		Assert.assertSame(GameMode.CREATIVE, this.element.getCurrent());
	}
	
}
