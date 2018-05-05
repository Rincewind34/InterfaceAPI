package de.rincewind.interfaceapi.gui.elements.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Lists;

public class Lore implements List<String> {

	private String prefix;

	private List<String> lore;

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
	public boolean add(String line) {
		return this.lore.add(this.prefix + line);
	}

	@Override
	public void add(int index, String line) {
		this.addAll(index, Stream.of(line.split(Pattern.quote("\\n"))).map((lineElement) -> {
			return this.prefix + line;
		}).collect(Collectors.toList()));
	}

	@Override
	public boolean addAll(Collection<? extends String> lines) {
		return this.lore.addAll(lines.stream().flatMap((line) -> {
			return Stream.of(line.split(Pattern.quote("\\n")));
		}).map((line) -> {
			return this.prefix + line;
		}).collect(Collectors.toList()));
	}

	@Override
	public boolean addAll(int index, Collection<? extends String> lines) {
		return this.lore.addAll(index, lines.stream().flatMap((line) -> {
			return Stream.of(line.split(Pattern.quote("\\n")));
		}).map((line) -> {
			return this.prefix + line;
		}).collect(Collectors.toList()));
	}

	@Override
	public void clear() {
		this.lore.clear();
	}

	@Override
	public boolean contains(Object object) {
		return this.lore.contains(object);
	}

	@Override
	public boolean containsAll(Collection<?> objects) {
		return this.lore.containsAll(objects);
	}

	@Override
	public String get(int index) {
		return this.lore.get(index);
	}

	@Override
	public int indexOf(Object object) {
		return this.lore.lastIndexOf(object);
	}

	@Override
	public boolean isEmpty() {
		return this.lore.isEmpty();
	}

	@Override
	public Iterator<String> iterator() {
		return this.lore.iterator();
	}

	@Override
	public int lastIndexOf(Object object) {
		return this.lore.lastIndexOf(object);
	}

	@Override
	public ListIterator<String> listIterator() {
		return this.lore.listIterator();
	}

	@Override
	public ListIterator<String> listIterator(int index) {
		return this.lore.listIterator(index);
	}

	@Override
	public boolean remove(Object object) {
		return this.lore.remove(object);
	}

	@Override
	public String remove(int index) {
		return this.lore.remove(index);
	}

	@Override
	public boolean removeAll(Collection<?> lines) {
		return this.lore.removeAll(lines);
	}

	@Override
	public boolean retainAll(Collection<?> lines) {
		return this.lore.retainAll(lines);
	}

	@Override
	public String set(int index, String line) {
		return this.lore.set(index, this.prefix + line); // TODO support split at \\n
	}

	@Override
	public int size() {
		return this.lore.size();
	}

	@Override
	public List<String> subList(int fromIndex, int toIndex) {
		return this.lore.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		return this.lore.toArray();
	}

	@Override
	public <T> T[] toArray(T[] array) {
		return this.lore.toArray(array);
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public Lore expand() {
		return this.expand("");
	}

	public Lore expand(String line) {
		this.add(line);
		return this;
	}
	
}
