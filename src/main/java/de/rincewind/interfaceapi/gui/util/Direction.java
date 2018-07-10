package de.rincewind.interfaceapi.gui.util;

public enum Direction {
	
	VERTICAL,
	HORIZONTAL;
	
	public Direction inverse() {
		return this == Direction.VERTICAL ? Direction.HORIZONTAL : Direction.VERTICAL;
	}
	
}
