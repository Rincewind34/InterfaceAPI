package de.rincewind.interfaceapi.gui.elements.util;

import org.junit.Test;

import de.rincewind.interfaceapi.gui.elements.util.lore.Lore;
import junit.framework.Assert;

public class LoreTest {
	
	@Test
	public void testExpand() {
		Lore lore = Lore.create();
		lore.expand("Test");
		
		Assert.assertEquals("§7Test", lore.getLine(0));
	}
	
}
