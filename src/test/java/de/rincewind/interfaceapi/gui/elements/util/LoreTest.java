package de.rincewind.interfaceapi.gui.elements.util;

import org.junit.Assert;
import org.junit.Test;

import de.rincewind.interfaceapi.gui.elements.util.lore.Lore;
public class LoreTest {
	
	@Test
	public void testExpandEmpty() {
		Lore lore = Lore.create();
		lore.expand();
		
		Assert.assertEquals("§7", lore.getLine(0));
	}
	
	@Test
	public void testExpandNull() {
		Lore lore = Lore.create();
		lore.expand(null);
		
		Assert.assertEquals("§7null", lore.getLine(0));
	}
	
	@Test
	public void testExpandText() {
		Lore lore = Lore.create();
		lore.expand("Test");
		
		Assert.assertEquals("§7Test", lore.getLine(0));
	}
	
	@Test
	public void testExpandMultilineText() {
		Lore lore = Lore.create();
		lore.expand("Test\\nTest2\\n\\nTest4\\nn\\YEAH");
		
		Assert.assertEquals(5, lore.size());
		Assert.assertEquals("§7Test", lore.getLine(0));
		Assert.assertEquals("§7Test2", lore.getLine(1));
		Assert.assertEquals("§7", lore.getLine(2));
		Assert.assertEquals("§7Test4", lore.getLine(3));
		Assert.assertEquals("§7n\\YEAH", lore.getLine(4));
	}
	
	@Test
	public void testEndEmpty() {
		Lore lore = Lore.create();
		lore.setEnd("Test\\nTest2");
		
		Assert.assertEquals(0, lore.size());
		Assert.assertEquals(2, lore.asList().size());
		Assert.assertEquals("Test", lore.asList().get(0));
		Assert.assertEquals("Test2", lore.asList().get(1));
	}
	
	@Test
	public void testEndFull() {
		Lore lore = Lore.create();
		lore.expand("Line");
		lore.setEnd("Test\\nTest2");
		
		Assert.assertEquals(1, lore.size());
		Assert.assertEquals(4, lore.asList().size());
		Assert.assertEquals("§7Line", lore.asList().get(0));
		Assert.assertEquals("§7", lore.asList().get(1));
		Assert.assertEquals("Test", lore.asList().get(2));
		Assert.assertEquals("Test2", lore.asList().get(3));
	}
	
}
