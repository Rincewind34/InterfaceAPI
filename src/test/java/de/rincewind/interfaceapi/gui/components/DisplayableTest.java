package de.rincewind.interfaceapi.gui.components;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.junit.BeforeClass;
import org.junit.Test;

import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.util.InterfaceUtils;
import junit.framework.Assert;

public class DisplayableTest {
	
	@BeforeClass
	public static void initRegistry() {
		Displayable.put(GameMode.class, InterfaceUtils::convertGameMode);
	}
	
	@Test
	public void testOfDisplayable() {
		Icon icon = new Icon(Material.APPLE);
		Assert.assertSame(icon, Displayable.of(icon));
	}
	
	@Test
	public void testOfConvertable() {
		Displayable displayable = Displayable.of(GameMode.SURVIVAL);
		Assert.assertEquals(InterfaceUtils.convertGameMode(GameMode.SURVIVAL), Displayable.readPayload(displayable));
	}
	
	@Test
	public void testOfCustom() {
		CustomDisplayable custom = new CustomDisplayable();
		Assert.assertSame(custom, Displayable.readPayload(custom));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testOfIconNull() {
		Displayable.of(null, new Object());
	}
	
	private static class CustomDisplayable implements Displayable {
		
		@Override
		public Icon getIcon() {
			return new Icon(Material.APPLE);
		}
		
	}
	
}
