package de.rincewind.interfaceapi.gui.windows.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;

import de.rincewind.interfaceapi.gui.elements.abstracts.Element;

public class Toolbar {
	
	private Map<Element, Set<String>> elements;
	
	private String toolset;
	
	public Toolbar() {
		this.elements = new HashMap<>();
	}
	
	public void addElement(Element element, String... toolsets) {
		this.elements.put(element, Sets.newHashSet(toolsets));
		element.setVisible(this.elements.get(element).contains(this.toolset));
	}
	
	public void addElements(Iterable<Element> elements, String... toolsets) {
		for (Element element : elements) {
			this.addElement(element, toolsets);
		}
	}
	
	public void removeElement(Element element) {
		element.setVisible(true);
		this.elements.remove(element);
	}
	
	public void removeElements(Iterable<Element> elements) {
		for (Element element : elements) {
			this.removeElement(element);
		}
	}
	
	public void activateToolSet(String toolset) {
		this.toolset = toolset;
		
		for (Element element : this.elements.keySet()) {
			if (this.toolset == null) {
				element.setVisible(false);
			} else {
				element.setVisible(this.elements.get(element).contains(this.toolset));
			}
		}
	}
	
	public String getToolset() {
		return this.toolset;
	}
	
	public Runnable actiovationListener(String toolset) {
		return () -> {
			this.activateToolSet(toolset);
		};
	}
	
	public Set<Element> getElements() {
		return Collections.unmodifiableSet(this.elements.keySet());
	}
	
	public Set<Element> getElements(String toolset) {
		return Collections.unmodifiableSet(this.elements.entrySet().stream().filter((entry) -> {
			return entry.getValue().contains(toolset);
		}).map(Map.Entry::getKey).collect(Collectors.toSet()));
	}
	
}
