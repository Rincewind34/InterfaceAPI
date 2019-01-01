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
	public void testAddSwitche_Enum() {
		Stream.of(GameMode.values()).map(Displayable::of).forEach(this.element::addSwitch);
		
		Assert.assertEquals(4, this.element.size());
		Assert.assertEquals(0, this.element.getSwitchIndex());
		Assert.assertSame(GameMode.CREATIVE, this.element.getCurrent());
	}
	
	@Test
	public void testAddSwitches_Empty() {
		this.element.addSwitches();
		
		Assert.assertEquals(0, this.element.size());
		Assert.assertSame(null, this.element.getCurrent());
		Assert.assertSame(null, this.element.getCurrentSwitch());
		Assert.assertSame(Icon.AIR, this.element.getIcon(Point.NULL));
	}
	
	@Test
	public void testNext_Empty() {
		this.element.next();
		
		Assert.assertSame(null, this.element.getCurrent());
		Assert.assertSame(null, this.element.getCurrentSwitch());
		Assert.assertSame(-1, this.element.getSwitchIndex());
		Assert.assertSame(Icon.AIR, this.element.getIcon(Point.NULL));
	}
	
	@Test
	public void testNext_OneItem_OneCall() {
		Icon icon1 = new Icon(Material.APPLE);
		
		this.element.addSwitch(icon1);
		this.element.next();
		
		Assert.assertSame(icon1, this.element.getCurrent());
		Assert.assertSame(icon1, this.element.getCurrentSwitch());
		Assert.assertSame(0, this.element.getSwitchIndex());
		Assert.assertSame(icon1, this.element.getIcon(Point.NULL));
	}
	
	@Test
	public void testNext_OneItem_MultipleCalls() {
		Icon icon1 = new Icon(Material.APPLE);
		
		this.element.addSwitch(icon1);
		this.element.next();
		this.element.next();
		this.element.next();
		
		Assert.assertSame(icon1, this.element.getCurrent());
		Assert.assertSame(icon1, this.element.getCurrentSwitch());
		Assert.assertSame(0, this.element.getSwitchIndex());
		Assert.assertSame(icon1, this.element.getIcon(Point.NULL));
	}
	
	@Test
	public void testNext_TwoItems_OneCall() {
		Icon icon1 = new Icon(Material.APPLE);
		Icon icon2 = new Icon(Material.BEDROCK);
		
		this.element.addSwitches(icon1, icon2);
		this.element.next();
		
		Assert.assertSame(icon2, this.element.getCurrent());
		Assert.assertSame(icon2, this.element.getCurrentSwitch());
		Assert.assertSame(1, this.element.getSwitchIndex());
		Assert.assertSame(icon2, this.element.getIcon(Point.NULL));
	}
	
	@Test
	public void testNext_TwoItems_TwoCalls() {
		Icon icon1 = new Icon(Material.APPLE);
		Icon icon2 = new Icon(Material.BEDROCK);
		
		this.element.addSwitches(icon1, icon2);
		this.element.next();
		this.element.next();
		
		Assert.assertSame(icon1, this.element.getCurrent());
		Assert.assertSame(icon1, this.element.getCurrentSwitch());
		Assert.assertSame(0, this.element.getSwitchIndex());
		Assert.assertSame(icon1, this.element.getIcon(Point.NULL));
	}
	
	@Test
	public void testNext_TwoItems_MultipleCalls() {
		Icon icon1 = new Icon(Material.APPLE);
		Icon icon2 = new Icon(Material.BEDROCK);
		
		this.element.addSwitches(icon1, icon2);
		this.element.next();
		this.element.next();
		this.element.next();
		this.element.next();
		
		Assert.assertSame(icon1, this.element.getCurrent());
		Assert.assertSame(icon1, this.element.getCurrentSwitch());
		Assert.assertSame(0, this.element.getSwitchIndex());
		Assert.assertSame(icon1, this.element.getIcon(Point.NULL));
	}
	
	@Test
	public void testNext_MultipleItems_OneCall() {
		Icon icon1 = new Icon(Material.APPLE);
		Icon icon2 = new Icon(Material.BEDROCK);
		Icon icon3 = new Icon(Material.RED_WOOL);
		Icon icon4 = new Icon(Material.GREEN_WOOL);
		
		this.element.addSwitches(icon1, icon2, icon3, icon4);
		this.element.next();
		
		Assert.assertSame(icon2, this.element.getCurrent());
		Assert.assertSame(icon2, this.element.getCurrentSwitch());
		Assert.assertSame(1, this.element.getSwitchIndex());
		Assert.assertSame(icon2, this.element.getIcon(Point.NULL));
	}
	
	@Test
	public void testNext_MultipleItems_MultipleCalls() {
		Icon icon1 = new Icon(Material.APPLE);
		Icon icon2 = new Icon(Material.BEDROCK);
		Icon icon3 = new Icon(Material.RED_WOOL);
		Icon icon4 = new Icon(Material.GREEN_WOOL);
		
		this.element.addSwitches(icon1, icon2, icon3, icon4);
		this.element.next();
		this.element.next();
		
		Assert.assertSame(icon3, this.element.getCurrent());
		Assert.assertSame(icon3, this.element.getCurrentSwitch());
		Assert.assertSame(2, this.element.getSwitchIndex());
		Assert.assertSame(icon3, this.element.getIcon(Point.NULL));
	}
	
	@Test
	public void testNext_MultipleItems_SkipThrough() {
		Icon icon1 = new Icon(Material.APPLE);
		Icon icon2 = new Icon(Material.BEDROCK);
		Icon icon3 = new Icon(Material.RED_WOOL);
		Icon icon4 = new Icon(Material.GREEN_WOOL);
		
		this.element.addSwitches(icon1, icon2, icon3, icon4);
		this.element.next();
		this.element.next();
		this.element.next();
		
		Assert.assertSame(icon4, this.element.getCurrent());
		Assert.assertSame(icon4, this.element.getCurrentSwitch());
		Assert.assertSame(3, this.element.getSwitchIndex());
		Assert.assertSame(icon4, this.element.getIcon(Point.NULL));
	}
	
	@Test
	public void testNext_MultipleItems_SkipOver() {
		Icon icon1 = new Icon(Material.APPLE);
		Icon icon2 = new Icon(Material.BEDROCK);
		Icon icon3 = new Icon(Material.RED_WOOL);
		Icon icon4 = new Icon(Material.GREEN_WOOL);
		
		this.element.addSwitches(icon1, icon2, icon3, icon4);
		this.element.next();
		this.element.next();
		this.element.next();
		this.element.next();
		this.element.next();
		
		Assert.assertSame(icon2, this.element.getCurrent());
		Assert.assertSame(icon2, this.element.getCurrentSwitch());
		Assert.assertSame(1, this.element.getSwitchIndex());
		Assert.assertSame(icon2, this.element.getIcon(Point.NULL));
	}
	
}
