package de.rincewind.interfaceplugin.gui.util;

import de.rincewind.interfaceapi.handling.Event;

public class TestEvent1 extends Event<Object> {

	private Object payload;

	public TestEvent1() {
		this(null);
	}

	public TestEvent1(Object payload) {
		this.payload = payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	public Object getPayload() {
		return this.payload;
	}

}
