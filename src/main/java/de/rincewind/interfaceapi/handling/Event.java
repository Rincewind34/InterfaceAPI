package de.rincewind.interfaceapi.handling;

public class Event {

	private boolean consumed;

	public void consume() {
		this.consumed = true;
	}

	public boolean isConsumed() {
		return this.consumed;
	}

}
