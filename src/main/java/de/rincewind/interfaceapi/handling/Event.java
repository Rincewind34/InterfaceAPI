package de.rincewind.interfaceapi.handling;

public class Event {

	private boolean consumed;
	private boolean monitor;

	public void consume() {
		this.validateMonitor();
		
		this.consumed = true;
	}
	
	public void monitor() {
		this.validateMonitor();
		
		this.monitor = true;
	}
	
	public boolean isInMonitor() {
		return this.monitor;
	}

	public boolean isConsumed() {
		return this.consumed;
	}
	
	protected void validateMonitor() {
		if (this.monitor) {
			throw new IllegalStateException("The event is already in monitor");
		}
	}

}
