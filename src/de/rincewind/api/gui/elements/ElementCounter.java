package de.rincewind.api.gui.elements;

public abstract interface ElementCounter extends ElementButton {

	public abstract int getCount();
	
	public abstract int getMaxCount();
	
	public abstract int getMinCount();
	
	public abstract void setMaxCount(int maxCount);
	
	public abstract void setMinCount(int minCount);
	
	public abstract void setCount(int count);
	
	public abstract void increment();
	
	public abstract void countdown();
	
	public abstract void addIncrementer(ElementButton btn, int value);
	
}
