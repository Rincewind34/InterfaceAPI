package de.rincewind.interfaceapi.gui.elements.util.lore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class EmptyLore implements Lore {
	
	public static final EmptyLore INSTANCE = new EmptyLore();
	
	private List<String> emptyList;
	
	private EmptyLore() {
		this.emptyList = Collections.unmodifiableList(new ArrayList<>());
	}
	
	@Override
	public Iterator<String> iterator() {
		return this.asList().iterator();
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public String getPrefix() {
		return null;
	}

	@Override
	public String getEnd() {
		return null;
	}

	@Override
	public String getLine(int index) {
		return null;
	}

	@Override
	public Lore clear() {
		return this;
	}

	@Override
	public Lore expand() {
		return this;
	}

	@Override
	public Lore expand(String line) {
		return this;
	}

	@Override
	public Lore insert(int index, String line) {
		return this;
	}

	@Override
	public Lore delete(int index) {
		return this;
	}

	@Override
	public Lore setPrefix(String prefix) {
		return this;
	}

	@Override
	public Lore setEnd(String end) {
		return this;
	}

	@Override
	public List<String> asList() {
		return this.emptyList;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null || !(obj instanceof SimpleLore)) {
			return false;
		}
		
		SimpleLore other = (SimpleLore) obj;
		return other.getEnd() == null && other.getPrefix() == null && other.size() == 0;
	}

	@Override
	public int hashCode() {
		// Take a look at SimpleLore#hasCode()
		return 29791;
	}
	
	@Override
	public Lore clone() {
		return this;
	}
	
}
