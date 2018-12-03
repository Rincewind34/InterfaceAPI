package de.rincewind.interfaceapi.gui.elements.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import de.rincewind.test.TestServer;

public class IconTest {
	
	@BeforeClass
	public static void initServer() {
		TestServer.setup();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNullItemStack() {
		new Icon((ItemStack) null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAirItemStack() {
		new Icon(new ItemStack(Material.AIR));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAirMaterial() {
		new Icon(Material.AIR);
	}

	@Test
	public void testEqualsAir() {
		Assert.assertTrue(Icon.AIR.equals(Icon.AIR));
	}
	
	@Test
	public void testEqualsComplex() {
		Icon icon1 = new Icon(Material.STONE, "Mein Stein");
		Icon icon2 = new Icon(Material.STONE, "Mein Stein");
		
		Assert.assertTrue(icon1.equals(icon2));
	}
	
	@Test
	public void testImmutableAir() {
		Icon icon = Icon.AIR.count(2).rename("My new name").enchant();
		Assert.assertSame(Icon.AIR, icon);
		Assert.assertEquals(new ItemStack(Material.AIR), icon.toItem());
	}
	
	@Test
	public void testCloneAir() {
		Icon icon = Icon.AIR.clone();
		Assert.assertSame(Icon.AIR, icon);
	}
	
	@Test
	public void testCloneComplex( ) {
		Icon icon1 = new Icon(Material.STONE, "Mein Stein");
		Icon icon2 = icon1.clone();

		Assert.assertEquals(icon1.toItem(), icon2.toItem());
		Assert.assertEquals(icon1, icon2);
	}
	
}
