package de.rincewind.interfaceplugin.gui.util;

import de.rincewind.interfaceapi.handling.Event;

public class TestEvent2 extends Event<Object> {

	private Object payload;

	public TestEvent2() {
		this(null);
	}

	public TestEvent2(Object payload) {
		this.payload = payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	public Object getPayload() {
		return this.payload;
	}

}
