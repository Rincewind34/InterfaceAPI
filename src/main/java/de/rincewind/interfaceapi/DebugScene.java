package de.rincewind.interfaceapi;

import java.util.List;

public class DebugScene {
	
	private List<String> debug;
	private boolean positiv;
	
	public DebugScene(List<String> debug, boolean positiv) {
		this.debug = debug;
		this.positiv = positiv;
	}
	
	public List<String> getDebug() {
		return this.debug;
	}
	
	public boolean isPositiv() {
		return this.positiv;
	}

}