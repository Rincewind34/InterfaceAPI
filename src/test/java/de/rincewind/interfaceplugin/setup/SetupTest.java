package de.rincewind.interfaceplugin.setup;

import org.bukkit.event.player.PlayerQuitEvent;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import de.rincewind.interfaceapi.InterfaceAPI;
import de.rincewind.interfaceapi.exceptions.SetupException;
import de.rincewind.interfaceapi.gui.windows.util.WindowState;
import de.rincewind.interfaceapi.setup.Setup;
import de.rincewind.interfaceplugin.listener.InventoryCloseListener;
import de.rincewind.interfaceplugin.listener.PlayerQuitListener;
import de.rincewind.test.TestPlayer;
import de.rincewind.test.TestServer;
import de.rincewind.test.TestWindowSizeable;

public class SetupTest {

	private static InventoryCloseListener closeListener;
	private static PlayerQuitListener quitListener;

	@BeforeClass
	public static void initInterfaceAPI() {
		TestServer.setup();

		InterfaceAPI.enable();
		CraftSetup.delay_inventory_change = false;
		
		SetupTest.closeListener = new InventoryCloseListener();
		SetupTest.quitListener = new PlayerQuitListener();
	}

	@After
	public void cleanSetup() {
		InterfaceAPI.resetSetups();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetupNotNull() {
		InterfaceAPI.getSetup(null);
	}

	@Test
	public void testSetupReference() {
		TestPlayer player1 = new TestPlayer("Player1", SetupTest.closeListener);

		Assert.assertSame(InterfaceAPI.getSetup(player1), InterfaceAPI.getSetup(player1));
		Assert.assertSame(player1, InterfaceAPI.getSetup(player1).getOwner());
		Assert.assertEquals(1, InterfaceAPI.getSetupAmount());

		TestPlayer player2 = new TestPlayer("Player2", SetupTest.closeListener);

		Assert.assertNotSame(InterfaceAPI.getSetup(player1), InterfaceAPI.getSetup(player2));
		Assert.assertEquals(2, InterfaceAPI.getSetupAmount());

		SetupTest.quitListener.onQuit(new PlayerQuitEvent(player1, null));

		Assert.assertEquals(1, InterfaceAPI.getSetupAmount());
	}

	@Test
	public void testSingleWindow() {
		TestPlayer player = new TestPlayer("Player", SetupTest.closeListener);
		TestWindowSizeable window = new TestWindowSizeable();

		Assert.assertFalse(InterfaceAPI.getSetup(player).hasMaximizedWindow());
		Assert.assertFalse(InterfaceAPI.getSetup(player).hasOpenWindow(window));
		Assert.assertEquals(WindowState.CLOSED, window.getState());
		Assert.assertEquals(0, InterfaceAPI.getSetup(player).getOpenWindows().size());

		InterfaceAPI.getSetup(player).open(window);
		
		Assert.assertSame(window.getReference(), player.getSynthInventory());
		Assert.assertSame(player, window.getUser());
		Assert.assertEquals(WindowState.MAXIMIZED, window.getState());
		Assert.assertSame(window, InterfaceAPI.getSetup(player).getMaximizedWindow());
		Assert.assertEquals(1, InterfaceAPI.getSetup(player).getOpenWindows().size());
		Assert.assertEquals(window, InterfaceAPI.getSetup(player).getOpenWindows().get(0));
		Assert.assertTrue(InterfaceAPI.getSetup(player).hasMaximizedWindow());
		Assert.assertTrue(InterfaceAPI.getSetup(player).hasOpenWindow(window));
		Assert.assertEquals(1, InterfaceAPI.getSetup(player).getOpenWindows().size());

		InterfaceAPI.getSetup(player).minimize();

		Assert.assertEquals(WindowState.MINIMIZED, window.getState());
		Assert.assertNull(InterfaceAPI.getSetup(player).getMaximizedWindow());
		Assert.assertEquals(1, InterfaceAPI.getSetup(player).getOpenWindows().size());
		Assert.assertNull(player.getSynthInventory());

		InterfaceAPI.getSetup(player).close(window);

		Assert.assertNull(player.getSynthInventory());
		Assert.assertEquals(0, InterfaceAPI.getSetup(player).getOpenWindows().size());
		Assert.assertEquals(WindowState.CLOSED, window.getState());
	}

	@Test(expected = SetupException.class)
	public void testDoubleOpenWindow() {
		TestPlayer player = new TestPlayer("Player", SetupTest.closeListener);
		TestWindowSizeable window = new TestWindowSizeable();

		InterfaceAPI.getSetup(player).open(window);
		InterfaceAPI.getSetup(player).open(window);
	}

	@Test(expected = SetupException.class)
	public void testCloseWindowClosed() {
		TestPlayer player = new TestPlayer("Player", SetupTest.closeListener);
		TestWindowSizeable window = new TestWindowSizeable();

		InterfaceAPI.getSetup(player).close(window);
	}

	@Test
	public void testWindowActionOther() {
		TestPlayer player1 = new TestPlayer("Player1", SetupTest.closeListener);
		TestPlayer player2 = new TestPlayer("Player2", SetupTest.closeListener);
		TestWindowSizeable window = new TestWindowSizeable();

		InterfaceAPI.getSetup(player1).open(window);

		try {
			InterfaceAPI.getSetup(player2).maximize(window);
			Assert.fail();
		} catch (SetupException exception) {

		}

		try {
			InterfaceAPI.getSetup(player2).close(window);
			Assert.fail();
		} catch (SetupException exception) {

		}
		
		try {
			InterfaceAPI.getSetup(player2).open(window);
			Assert.fail();
		} catch (SetupException exception) {

		}
	}
	
	@Test
	public void testCloseAll() {
		
	}

	@Test
	public void testMultipleWindows() {
		TestPlayer player = new TestPlayer("Player", SetupTest.closeListener);
		TestWindowSizeable window1 = new TestWindowSizeable();
		TestWindowSizeable window2 = new TestWindowSizeable();
		TestWindowSizeable window3 = new TestWindowSizeable();

		Setup setup = InterfaceAPI.getSetup(player);
		setup.open(window1);
		setup.open(window2);

		Assert.assertTrue(setup.hasMaximizedWindow());
		Assert.assertSame(window2, setup.getMaximizedWindow());
		Assert.assertSame(window2.getReference(), player.getSynthInventory());
		Assert.assertEquals(WindowState.BACKGROUND, window1.getState());
		Assert.assertEquals(WindowState.MAXIMIZED, window2.getState());
		Assert.assertEquals(2, InterfaceAPI.getSetup(player).getOpenWindows().size());
		
		setup.minimize();

		Assert.assertTrue(setup.hasMaximizedWindow());
		Assert.assertSame(window1, setup.getMaximizedWindow());
		Assert.assertSame(window1.getReference(), player.getSynthInventory());
		Assert.assertEquals(WindowState.MAXIMIZED, window1.getState());
		Assert.assertEquals(WindowState.MINIMIZED, window2.getState());
		
		setup.open(window3);

		Assert.assertTrue(setup.hasMaximizedWindow());
		Assert.assertSame(window3, setup.getMaximizedWindow());
		Assert.assertSame(window3.getReference(), player.getSynthInventory());
		Assert.assertEquals(WindowState.BACKGROUND, window1.getState());
		Assert.assertEquals(WindowState.MINIMIZED, window2.getState());
		Assert.assertEquals(WindowState.MAXIMIZED, window3.getState());
		Assert.assertEquals(3, InterfaceAPI.getSetup(player).getOpenWindows().size());
		
		setup.close(window1);

		Assert.assertTrue(setup.hasMaximizedWindow());
		Assert.assertSame(window3, setup.getMaximizedWindow());
		Assert.assertSame(window3.getReference(), player.getSynthInventory());
		Assert.assertEquals(WindowState.CLOSED, window1.getState());
		Assert.assertEquals(WindowState.MINIMIZED, window2.getState());
		Assert.assertEquals(WindowState.MAXIMIZED, window3.getState());
		Assert.assertEquals(2, InterfaceAPI.getSetup(player).getOpenWindows().size());
		
		setup.open(window1);

		Assert.assertTrue(setup.hasMaximizedWindow());
		Assert.assertSame(window1, setup.getMaximizedWindow());
		Assert.assertSame(window1.getReference(), player.getSynthInventory());
		Assert.assertEquals(WindowState.MAXIMIZED, window1.getState());
		Assert.assertEquals(WindowState.MINIMIZED, window2.getState());
		Assert.assertEquals(WindowState.BACKGROUND, window3.getState());
		Assert.assertEquals(3, InterfaceAPI.getSetup(player).getOpenWindows().size());
		
		setup.maximize(window2);

		Assert.assertTrue(setup.hasMaximizedWindow());
		Assert.assertSame(window2, setup.getMaximizedWindow());
		Assert.assertSame(window2.getReference(), player.getSynthInventory());
		Assert.assertEquals(WindowState.BACKGROUND, window1.getState());
		Assert.assertEquals(WindowState.MAXIMIZED, window2.getState());
		Assert.assertEquals(WindowState.BACKGROUND, window3.getState());
		
		setup.close(window2);

		Assert.assertTrue(setup.hasMaximizedWindow());
		Assert.assertSame(window1, setup.getMaximizedWindow());
		Assert.assertSame(window1.getReference(), player.getSynthInventory());
		Assert.assertEquals(WindowState.MAXIMIZED, window1.getState());
		Assert.assertEquals(WindowState.CLOSED, window2.getState());
		Assert.assertEquals(WindowState.BACKGROUND, window3.getState());
		Assert.assertEquals(2, InterfaceAPI.getSetup(player).getOpenWindows().size());
		
		setup.close(window3);

		Assert.assertTrue(setup.hasMaximizedWindow());
		Assert.assertSame(window1, setup.getMaximizedWindow());
		Assert.assertSame(window1.getReference(), player.getSynthInventory());
		Assert.assertEquals(WindowState.MAXIMIZED, window1.getState());
		Assert.assertEquals(WindowState.CLOSED, window2.getState());
		Assert.assertEquals(WindowState.CLOSED, window3.getState());
		Assert.assertEquals(1, InterfaceAPI.getSetup(player).getOpenWindows().size());
		
		setup.close(window1);

		Assert.assertFalse(setup.hasMaximizedWindow());
		Assert.assertNull(setup.getMaximizedWindow());
		Assert.assertNull(player.getSynthInventory());
		Assert.assertEquals(WindowState.CLOSED, window1.getState());
		Assert.assertEquals(WindowState.CLOSED, window2.getState());
		Assert.assertEquals(WindowState.CLOSED, window3.getState());
		Assert.assertEquals(0, InterfaceAPI.getSetup(player).getOpenWindows().size());
	}

	@Test
	public void testMinimizeAll_AllInBackground() {
		TestPlayer player = new TestPlayer("Player", SetupTest.closeListener);
		TestWindowSizeable window1 = new TestWindowSizeable();
		TestWindowSizeable window2 = new TestWindowSizeable();
		TestWindowSizeable window3 = new TestWindowSizeable();

		Setup setup = InterfaceAPI.getSetup(player);
		setup.open(window1);
		setup.open(window2);
		setup.open(window3);
		
		setup.minimizeAll();
		
		Assert.assertFalse(setup.hasMaximizedWindow());
		Assert.assertEquals(WindowState.MINIMIZED, window1.getState());
		Assert.assertEquals(WindowState.MINIMIZED, window2.getState());
		Assert.assertEquals(WindowState.MINIMIZED, window3.getState());
		Assert.assertEquals(3, InterfaceAPI.getSetup(player).getOpenWindows().size());
	}

	@Test
	public void testMinimizeAll_SomeMinimized() {
		TestPlayer player = new TestPlayer("Player", SetupTest.closeListener);
		TestWindowSizeable window1 = new TestWindowSizeable();
		TestWindowSizeable window2 = new TestWindowSizeable();
		TestWindowSizeable window3 = new TestWindowSizeable();

		Setup setup = InterfaceAPI.getSetup(player);
		setup.open(window1);
		setup.open(window2);
		setup.open(window3);
		
		setup.minimize();
		setup.minimizeAll();
		
		Assert.assertFalse(setup.hasMaximizedWindow());
		Assert.assertEquals(WindowState.MINIMIZED, window1.getState());
		Assert.assertEquals(WindowState.MINIMIZED, window2.getState());
		Assert.assertEquals(WindowState.MINIMIZED, window3.getState());
		Assert.assertEquals(3, InterfaceAPI.getSetup(player).getOpenWindows().size());
	}

	@Test
	public void testMinimizeAll_AllMinimized() {
		TestPlayer player = new TestPlayer("Player", SetupTest.closeListener);
		TestWindowSizeable window1 = new TestWindowSizeable();
		TestWindowSizeable window2 = new TestWindowSizeable();
		TestWindowSizeable window3 = new TestWindowSizeable();

		Setup setup = InterfaceAPI.getSetup(player);
		setup.open(window1);
		setup.open(window2);
		setup.open(window3);
		
		setup.minimize();
		setup.minimize();
		setup.minimize();
		
		setup.minimizeAll();
		
		Assert.assertFalse(setup.hasMaximizedWindow());
		Assert.assertEquals(WindowState.MINIMIZED, window1.getState());
		Assert.assertEquals(WindowState.MINIMIZED, window2.getState());
		Assert.assertEquals(WindowState.MINIMIZED, window3.getState());
		Assert.assertEquals(3, InterfaceAPI.getSetup(player).getOpenWindows().size());
	}

	@Test
	public void testMinimizeAll_NoWindows() {
		TestPlayer player = new TestPlayer("Player", SetupTest.closeListener);
		Setup setup = InterfaceAPI.getSetup(player);
		setup.minimizeAll();
	}

	@Test
	public void testMaximizeAll_AllMinimized() {
		TestPlayer player = new TestPlayer("Player", SetupTest.closeListener);
		TestWindowSizeable window1 = new TestWindowSizeable();
		TestWindowSizeable window2 = new TestWindowSizeable();
		TestWindowSizeable window3 = new TestWindowSizeable();

		Setup setup = InterfaceAPI.getSetup(player);
		setup.open(window1);
		setup.open(window2);
		setup.open(window3);
		setup.minimizeAll();
		setup.maximizeAll();
		
		Assert.assertTrue(setup.hasMaximizedWindow());
		Assert.assertSame(window3.getReference(), player.getSynthInventory());
		Assert.assertEquals(WindowState.BACKGROUND, window1.getState());
		Assert.assertEquals(WindowState.BACKGROUND, window2.getState());
		Assert.assertEquals(WindowState.MAXIMIZED, window3.getState());
		Assert.assertEquals(3, InterfaceAPI.getSetup(player).getOpenWindows().size());
	}

}
