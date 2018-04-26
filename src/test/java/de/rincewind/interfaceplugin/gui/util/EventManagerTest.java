package de.rincewind.interfaceplugin.gui.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.rincewind.interfaceapi.handling.EventManager;
import de.rincewind.interfaceapi.handling.InterfaceListener;
import de.rincewind.interfaceapi.handling.element.ElementInteractEvent;

public class EventManagerTest {

	private EventManager manager;

	@Before
	public void initManager() {
		this.manager = new CraftEventManager();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCallNullParameter() {
		this.manager.callEvent(ElementInteractEvent.class, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCallNullType() {
		this.manager.callEvent(null, new ElementInteractEvent(null, null, null, null, null));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRegisterNullParameter() {
		this.manager.registerListener(ElementInteractEvent.class, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRegisterNullType() {
		this.manager.registerListener(null, (event) -> {

		});
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetNullType() {
		this.manager.getRegisteredListeners(null);
	}

	@Test
	public void testSimpleCall() {
		TestEvent1[] calledEvent = new TestEvent1[2];
		calledEvent[0] = new TestEvent1();

		InterfaceListener<TestEvent1> listener = (event) -> {
			calledEvent[1] = event;
		};

		this.manager.registerListener(TestEvent1.class, listener).addAfter();

		Assert.assertEquals(1, this.manager.getRegisteredListeners(TestEvent1.class).size());
		Assert.assertSame(listener, manager.getRegisteredListeners(TestEvent1.class).get(0));

		this.manager.callEvent(TestEvent1.class, calledEvent[0]);
		Assert.assertSame(calledEvent[0], calledEvent[1]);
	}

	@Test(expected = RuntimeException.class)
	public void testPipelineException() {
		this.manager.registerListener(TestEvent1.class, (event) -> {
			throw new RuntimeException();
		}).addAfter();

		this.manager.callEvent(TestEvent1.class, new TestEvent1());

		Assert.fail();
	}

	@Test
	public void testMultipleCalls() {
		int[] counter = new int[1];

		this.manager.registerListener(TestEvent1.class, (event) -> {
			counter[0]++;
		}).addAfter();

		this.manager.registerListener(TestEvent1.class, (event) -> {
			counter[0]++;
		}).addBefore();

		this.manager.registerListener(TestEvent2.class, (event) -> {
			counter[0]++;
		}).addAfter();

		this.manager.callEvent(TestEvent1.class, new TestEvent1());

		Assert.assertEquals(2, counter[0]);

		counter[0] = 0;
		this.manager.callEvent(TestEvent2.class, new TestEvent2());

		Assert.assertEquals(1, counter[0]);
	}

	@Test
	public void testPayload() {
		Object object1 = new Object();
		Object object2 = new Object();

		this.manager.registerListener(TestEvent1.class, (event) -> {
			Assert.assertSame(object1, event.getPayload());
			event.setPayload(object2);
		}).addAfter();

		this.manager.registerListener(TestEvent1.class, (event) -> {
			Assert.assertSame(object2, event.getPayload());
		}).addAfter();

		this.manager.callEvent(TestEvent1.class, new TestEvent1(object1));
	}

	@Test
	public void testPipelineOrder() {
		InterfaceListener<TestEvent1> listener1 = (event) -> {
		};
		InterfaceListener<TestEvent1> listener2 = (event) -> {
		};
		InterfaceListener<TestEvent1> listener3 = (event) -> {
		};
		InterfaceListener<TestEvent1> listener4 = (event) -> {
		};
		InterfaceListener<TestEvent1> listener5 = (event) -> {
		};

		this.manager.registerListener(TestEvent1.class, listener1).addBefore();
		this.manager.registerListener(TestEvent1.class, listener2).addBefore();
		this.manager.registerListener(TestEvent1.class, listener3).addAfter();
		this.manager.registerListener(TestEvent1.class, listener4).addAfter();
		this.manager.registerListener(TestEvent1.class, listener5).addBefore();

		Assert.assertArrayEquals(new Object[] { listener5, listener2, listener1, listener3, listener4 },
				this.manager.getRegisteredListeners(TestEvent1.class).toArray());
	}

}
