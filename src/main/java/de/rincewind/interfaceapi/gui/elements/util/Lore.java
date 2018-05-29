package de.rincewind.interfaceapi.gui.elements.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Lists;

public final class Lore implements Iterable<String>, Cloneable {

	private boolean dirty;

	private String prefix;
	private String end;

	private final List<String> lore;

	public Lore() {
		this(new ArrayList<>());
	}

	public Lore(String... array) {
		this(Lists.newArrayList(array));
	}

	public Lore(List<String> lore) {
		this.lore = lore;
		this.prefix = "§7";
	}

	@Override
	public Iterator<String> iterator() {
		return this.toList().iterator();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		
		Lore other = (Lore) obj;
		
		if (this.end == null) {
			if (other.end != null) {
				return false;
			}
		} else if (!this.end.equals(other.end)) {
			return false;
		}
		
		if (this.lore == null) {
			if (other.lore != null) {
				return false;
			}
		} else if (!this.lore.equals(other.lore)) {
			return false;
		}
		
		if (this.prefix == null) {
			if (other.prefix != null) {
				return false;
			}
		} else if (!this.prefix.equals(other.prefix)) {
			return false;
		}
		
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.end == null) ? 0 : this.end.hashCode());
		result = prime * result + ((this.lore == null) ? 0 : this.lore.hashCode());
		result = prime * result + ((this.prefix == null) ? 0 : this.prefix.hashCode());
		return result;
	}

	@Override
	public Lore clone() {
		try {
			Lore lore = (Lore) super.clone();
			Collections.copy(lore.lore, this.lore);
			return lore;
		} catch (CloneNotSupportedException exception) {
			assert false : "Unsupported cloning operation";
			return null;
		}
	}

	public boolean isEmpty() {
		return this.lore.isEmpty();
	}

	public boolean isDirty() {
		return this.dirty;
	}

	public int size() {
		return this.lore.size();
	}

	public String getPrefix() {
		return this.prefix;
	}

	public String getEnd() {
		return this.end;
	}

	public String getLine(int index) {
		return this.lore.get(index);
	}

	public Lore clear() {
		this.lore.clear();
		this.dirty = true;
		return this;
	}

	public Lore expand() {
		return this.expand("");
	}

	public Lore expand(String line) {
		if (this.lore.addAll(Stream.of(line.split(Pattern.quote("\\n"))).map((lineElement) -> {
			return this.prefix + line;
		}).collect(Collectors.toList()))) {
			this.dirty = true;
		}

		return this;
	}

	public Lore insert(int index, String line) {
		if (this.lore.addAll(index, Stream.of(line.split(Pattern.quote("\\n"))).map((lineElement) -> {
			return this.prefix + line;
		}).collect(Collectors.toList()))) {
			this.dirty = true;
		}
		return this;
	}

	public Lore delete(int index) {
		this.lore.remove(index);
		this.dirty = true;
		return this;
	}

	public Lore setPrefix(String prefix) {
		if (!Objects.equals(this.prefix, prefix)) {
			this.prefix = prefix == null ? "" : prefix;
		}

		return this;
	}

	public Lore setEnd(String end) {
		if (!Objects.equals(this.end, end)) {
			this.end = end;
		}

		return this;
	}
	
	public List<String> asList() {
		List<String> dummy = new ArrayList<>();
		dummy.addAll(this.lore);

		if (this.end != null) {
			if (this.size() > 0) {
				dummy.add(this.prefix);
			}
			
			dummy.addAll(Arrays.asList(this.end.split(Pattern.quote("\\n"))));
		}
		
		return dummy;
	}
	
	/*
	 * Only called by Icon
	 */
	protected List<String> toList() {
		this.dirty = false;
		return this.asList();
	}

}
