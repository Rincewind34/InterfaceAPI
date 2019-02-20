package de.rincewind.interfaceplugin.gui.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.rincewind.interfaceapi.exceptions.EventPipelineException;
import de.rincewind.interfaceapi.handling.Event;
import de.rincewind.interfaceapi.handling.InterfaceListener;
import de.rincewind.interfaceapi.handling.element.ElementInteractEvent;

public class EventManagerTest {

	private CraftEventManager manager;

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
	public void testRegisterNullParameter1() {
		this.manager.registerListener(ElementInteractEvent.class, (InterfaceListener<ElementInteractEvent>) null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRegisterNullParameter2() {
		this.manager.registerListener(ElementInteractEvent.class, (Runnable) null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRegisterNullType() {
		this.manager.registerListener(null, (event) -> {

		});
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCountNullType1() {
		this.manager.countRegisteredListeners(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCountNullType2() {
		this.manager.countRegisteredPipelineListeners(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCountNullType3() {
		this.manager.countRegisteredMonitorListeners(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCountNullType4() {
		this.manager.countActiveListeners(null);
	}

	@Test
	public void testSimpleCall() {
		TestEvent1[] calledEvent = new TestEvent1[2];
		calledEvent[0] = new TestEvent1();

		InterfaceListener<TestEvent1> listener = (event) -> {
			calledEvent[1] = event;
		};

		this.manager.registerListener(TestEvent1.class, listener).addAfter();

		Assert.assertEquals(1, this.manager.countRegisteredListeners(TestEvent1.class));
		Assert.assertEquals(1, this.manager.countRegisteredPipelineListeners(TestEvent1.class));
		Assert.assertEquals(1, this.manager.countActiveListeners(TestEvent1.class));
		Assert.assertEquals(0, this.manager.countRegisteredMonitorListeners(TestEvent1.class));

		this.manager.callEvent(TestEvent1.class, calledEvent[0]);
		Assert.assertSame(calledEvent[0], calledEvent[1]);
	}

	@Test
	public void testMonitor() {
		TestEvent1[] calledEvent = new TestEvent1[2];
		calledEvent[0] = new TestEvent1();

		InterfaceListener<TestEvent1> listener = (event) -> {
			if (!event.isInMonitor()) {
				Assert.fail();
			}

			calledEvent[1] = event;
		};

		this.manager.registerListener(TestEvent1.class, listener).monitor();

		Assert.assertEquals(1, this.manager.countRegisteredListeners(TestEvent1.class));
		Assert.assertEquals(0, this.manager.countRegisteredPipelineListeners(TestEvent1.class));
		Assert.assertEquals(1, this.manager.countActiveListeners(TestEvent1.class));
		Assert.assertEquals(1, this.manager.countRegisteredMonitorListeners(TestEvent1.class));

		this.manager.callEvent(TestEvent1.class, calledEvent[0]);
		Assert.assertSame(calledEvent[0], calledEvent[1]);
	}

	@Test
	public void testException() {
		this.manager.registerListener(TestEvent1.class, (event) -> {
			RuntimeException exception = new RuntimeException();
			exception.setStackTrace(new StackTraceElement[0]);
			throw exception;
		}).addAfter();

		this.manager.registerListener(TestEvent1.class, (event) -> {
			throw new Error();
		}).monitor();

		TestEvent1 event = new TestEvent1();

		try {
			this.manager.callEvent(TestEvent1.class, event);
			Assert.fail();
		} catch (Error error) {

		}

		Assert.assertTrue(event.isConsumed());
		Assert.assertTrue(event.isInMonitor());
	}

	@Test
	public void testPipelineException() {
		this.manager.registerListener(TestEvent1.class, (event) -> {
			EventPipelineException exception = new EventPipelineException();
			exception.setStackTrace(new StackTraceElement[0]);
			throw exception;
		}).addAfter();

		this.manager.registerListener(TestEvent1.class, (event) -> {
			throw new Error();
		}).monitor();

		TestEvent1 event = new TestEvent1();

		try {
			this.manager.callEvent(TestEvent1.class, event);
			Assert.fail();
		} catch (Error error) {

		}

		Assert.assertFalse(event.isConsumed());
		Assert.assertTrue(event.isInMonitor());
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
	public void testExtendingEvents() {
		int[] counter = new int[] { 0 };

		this.manager.registerListener(Event.class, (event) -> {
			counter[0]++;
		}).addAfter();

		this.manager.registerListener(TestEvent1.class, (event) -> {
			counter[0]++;
		}).addAfter();

		this.manager.callEvent(TestEvent1.class, new TestEvent1());
		Assert.assertEquals(2, counter[0]);
		counter[0] = 0;

		this.manager.registerListener(TestEvent1.class, (event) -> {
			counter[0]++;
		}).addAfter();

		this.manager.registerListener(TestEvent2.class, (event) -> {
			counter[0]++;
		}).addAfter();

		this.manager.callEvent(TestEvent2.class, new TestEvent2());
		Assert.assertEquals(2, counter[0]);
		counter[0] = 0;

		this.manager.callEvent(TestEvent3.class, new TestEvent3());
		Assert.assertEquals(3, counter[0]);
		counter[0] = 0;

		this.manager.registerListener(TestEvent3.class, (event) -> {
			counter[0]++;
		}).addAfter();

		this.manager.callEvent(TestEvent3.class, new TestEvent3());
		Assert.assertEquals(4, counter[0]);
		counter[0] = 0;

		this.manager.callEvent(Event.class, new Event());
		Assert.assertEquals(1, counter[0]);
	}

	@Test
	public void testConsume() {
		int[] counter = new int[] { 0 };

		this.manager.registerListener(TestEvent1.class, (event) -> {
			counter[0]++;
			event.consume();
		}).addAfter();

		this.manager.registerListener(TestEvent1.class, (event) -> {
			counter[0]++;
		}).addAfter();

		this.manager.callEvent(TestEvent1.class, new TestEvent1());
		Assert.assertEquals(1, counter[0]);
		counter[0] = 0;

		this.manager.registerListener(Event.class, (event) -> {
			counter[0]++;
		}).addAfter();

		this.manager.callEvent(TestEvent1.class, new TestEvent1());
		Assert.assertEquals(1, counter[0]);
		counter[0] = 0;

		this.manager.registerListener(TestEvent1.class, (event) -> {
			counter[0]++;
		}).addBefore();

		this.manager.callEvent(TestEvent1.class, new TestEvent1());
		Assert.assertEquals(2, counter[0]);
		counter[0] = 0;

		this.manager.callEvent(Event.class, new Event());
		Assert.assertEquals(1, counter[0]);
		counter[0] = 0;

		this.manager.registerListener(TestEvent3.class, (event) -> {
			counter[0]++;
		}).addAfter();

		this.manager.callEvent(TestEvent3.class, new TestEvent3());
		Assert.assertEquals(3, counter[0]);
		counter[0] = 0;

		this.manager.registerListener(TestEvent3.class, (event) -> {
			counter[0]++;
			event.consume();
		}).addBefore();

		this.manager.callEvent(TestEvent3.class, new TestEvent3());
		Assert.assertEquals(1, counter[0]);
		counter[0] = 0;

		this.manager.callEvent(TestEvent1.class, new TestEvent1());
		Assert.assertEquals(2, counter[0]);
		counter[0] = 0;

		this.manager.registerListener(TestEvent2.class, (event) -> {
			counter[0]++;
		}).addBefore();

		this.manager.callEvent(Event.class, new Event());
		Assert.assertEquals(1, counter[0]);
		counter[0] = 0;

		this.manager.callEvent(TestEvent1.class, new TestEvent3());
		Assert.assertEquals(2, counter[0]);
		counter[0] = 0;

		this.manager.callEvent(Event.class, new TestEvent3());
		Assert.assertEquals(1, counter[0]);
		counter[0] = 0;

		this.manager.callEvent(TestEvent2.class, new TestEvent2());
		Assert.assertEquals(2, counter[0]);
		counter[0] = 0;
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
		InterfaceListener<TestEvent1> listener1 = (event) -> {};
		InterfaceListener<TestEvent1> listener2 = (event) -> {};
		InterfaceListener<TestEvent1> listener3 = (event) -> {};
		InterfaceListener<TestEvent1> listener4 = (event) -> {};
		InterfaceListener<TestEvent1> listener5 = (event) -> {};

		this.manager.registerListener(TestEvent1.class, listener1).addBefore();
		this.manager.registerListener(TestEvent1.class, listener2).addBefore();
		this.manager.registerListener(TestEvent1.class, listener3).addAfter();
		this.manager.registerListener(TestEvent1.class, listener4).addAfter();
		this.manager.registerListener(TestEvent1.class, listener5).addBefore();

		Assert.assertArrayEquals(new Object[] { listener5, listener2, listener1, listener3, listener4 },
				this.manager.touchEntry(TestEvent1.class).listeners.toArray());
	}

}
