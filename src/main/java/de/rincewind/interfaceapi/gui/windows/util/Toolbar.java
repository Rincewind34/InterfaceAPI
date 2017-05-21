package de.rincewind.interfaceapi.gui.windows.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import de.rincewind.interfaceapi.gui.elements.abstracts.Element;

public class Toolbar {
	
	private Map<Element, List<String>> elements;
	
	private String toolset;
	
	public Toolbar() {
		this.elements = new HashMap<>();
	}
	
	public void addElement(Element element, String... toolsets) {
		this.elements.put(element, Arrays.asList(toolsets));
		element.setVisible(this.elements.get(element).contains(this.toolset));
	}
	
	public void removeElement(Element element) {
		element.setVisible(true);
		this.elements.remove(element);
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
	
	public List<Element> getElements() {
		return Collections.unmodifiableList(this.elements.keySet().stream().collect(Collectors.toList()));
	}
	
	public List<Element> getElements(String toolset) {
		return Collections.unmodifiableList(this.elements.keySet().stream().filter((element) -> {
			return this.elements.get(element).contains(toolset);
		}).collect(Collectors.toList()));
	}
	
}
