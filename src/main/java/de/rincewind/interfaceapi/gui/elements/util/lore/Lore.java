package de.rincewind.interfaceapi.gui.elements.util.lore;

import java.util.List;

public interface Lore extends Iterable<String>, Cloneable {
	
	public static Lore create() {
		return new SimpleLore();
	}
	
	public static Lore create(String... array) {
		return new SimpleLore(array);
	}

	public static Lore create(List<String> list) {
		return new SimpleLore(list);
	}
	
	public abstract Lore clone();
	
	public abstract boolean isEmpty();

	public abstract int size();

	public abstract String getPrefix();

	public abstract String getEnd();

	public abstract String getLine(int index);

	public abstract Lore clear();

	public abstract Lore expand();

	public abstract Lore expand(String line);

	public abstract Lore insert(int index, String line);

	public abstract Lore delete(int index);

	public abstract Lore setPrefix(String prefix);

	public abstract Lore setEnd(String end);

	public abstract List<String> asList();

}