package de.rincewind.interfaceapi.gui.elements.util.lore;

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

public final class SimpleLore implements Iterable<String>, Cloneable, Lore {

	private boolean dirty;
	
	private String prefix;
	private String end;

	private final List<String> lore;

	SimpleLore() {
		this(new ArrayList<>());
	}

	SimpleLore(String... array) {
		this(Lists.newArrayList(array));
	}

	SimpleLore(List<String> lore) {
		this.prefix = "§7";
		this.lore = lore.stream().map((line) -> {
			return this.prefix + line;
		}).collect(Collectors.toList());
	}

	@Override
	public Iterator<String> iterator() {
		return this.toList().iterator();
	}

	@Override
	public boolean isEmpty() {
		return this.lore.isEmpty();
	}

	@Override
	public int size() {
		return this.lore.size();
	}

	@Override
	public String getPrefix() {
		return this.prefix;
	}

	@Override
	public String getEnd() {
		return this.end;
	}

	@Override
	public String getLine(int index) {
		return this.lore.get(index);
	}

	@Override
	public Lore clear() {
		this.lore.clear();
		this.dirty = true;
		return this;
	}

	@Override
	public Lore expand() {
		this.lore.add(this.prefix);
		return this;
	}

	@Override
	public Lore expand(String line) {
		if (line != null) {
			if (this.lore.addAll(Stream.of(line.split(Pattern.quote("\\n"))).map((lineElement) -> {
				return this.prefix + lineElement;
			}).collect(Collectors.toList()))) {
				this.dirty = true;
			}
			
			return this;
		} else {
			this.lore.add(this.prefix + "null");
			return this;
		}
	}

	@Override
	public Lore insert(int index, String line) {
		if (this.lore.addAll(index, Stream.of(line.split(Pattern.quote("\\n"))).map((lineElement) -> {
			return this.prefix + line;
		}).collect(Collectors.toList()))) {
			this.dirty = true;
		}
		return this;
	}

	@Override
	public Lore delete(int index) {
		this.lore.remove(index);
		this.dirty = true;
		return this;
	}

	@Override
	public Lore setPrefix(String prefix) {
		if (!Objects.equals(this.prefix, prefix)) {
			this.prefix = prefix == null ? "" : prefix;
			this.dirty = true;
		}

		return this;
	}

	@Override
	public Lore setEnd(String end) {
		if (!Objects.equals(this.end, end)) {
			this.end = end;
			this.dirty = true;
		}

		return this;
	}

	@Override
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		
		if (obj == EmptyLore.INSTANCE) {
			return this.lore.size() == 0 && this.prefix == null && this.end == null;
		}
		
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		
		SimpleLore other = (SimpleLore) obj;
		
		if (this.end == null) {
			if (other.end != null) {
				return false;
			}
		} else if (!this.end.equals(other.end)) {
			return false;
		}
		
		if (!this.lore.equals(other.lore)) {
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
		result = prime * result + this.lore.hashCode();
		result = prime * result + ((this.prefix == null) ? 0 : this.prefix.hashCode());
		return result;
	}

	@Override
	public SimpleLore clone() {
		try {
			SimpleLore lore = (SimpleLore) super.clone();
			Collections.copy(lore.lore, this.lore);
			lore.dirty = this.dirty;
			lore.end = this.end;
			lore.prefix = this.prefix;
			return lore;
		} catch (CloneNotSupportedException exception) {
			assert false : "Unsupported cloning operation";
			return null;
		}
	}
	
	public boolean isDirty() {
		return this.dirty;
	}
	
	/*
	 * Only called by Icon
	 */
	public List<String> toList() {
		this.dirty = false;
		return this.asList();
	}

}
