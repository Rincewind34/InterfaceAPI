package de.rincewind.interfaceplugin.gui.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.rincewind.interfaceapi.gui.elements.util.ClickAction;
import de.rincewind.interfaceapi.gui.elements.util.ClickBlocker;

public class ClickBlockerTest {
	
	private ClickBlocker clickBlocker;
	
	@Before
	public void initBlocker() {
		this.clickBlocker = new CraftClickBlocker();
	}
	
	@Test
	public void testEmptyUnlocked() {
		for (ClickAction action : ClickAction.values()) {
			Assert.assertTrue(this.clickBlocker.allows(action));
		}
		
		Assert.assertFalse(this.clickBlocker.isLocked());
	}
	
	@Test
	public void testLocked() {
		this.clickBlocker.lock();
		
		for (ClickAction action : ClickAction.values()) {
			Assert.assertFalse(this.clickBlocker.allows(action));
		}
		
		Assert.assertTrue(this.clickBlocker.isLocked());
	}
	
	@Test
	public void testBlocked() {
		this.clickBlocker.addAction(ClickAction.PICKUP);
		
		Assert.assertFalse(this.clickBlocker.allows(ClickAction.PICKUP));
		Assert.assertTrue(this.clickBlocker.allows(ClickAction.PLACE));
		Assert.assertFalse(this.clickBlocker.isLocked());
		Assert.assertEquals(1, this.clickBlocker.getBlocked().size());
		
		this.clickBlocker.addAction(ClickAction.PICKUP);

		Assert.assertEquals(1, this.clickBlocker.getBlocked().size());
	}
	
	@Test
	public void testRelock() {
		this.clickBlocker.lock();
		
		Assert.assertTrue(this.clickBlocker.isLocked());
		
		this.clickBlocker.unlock();
		
		Assert.assertFalse(this.clickBlocker.isLocked());
	}
	
}
