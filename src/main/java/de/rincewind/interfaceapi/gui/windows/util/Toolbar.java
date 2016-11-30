package de.rincewind.interfaceapi.gui.windows.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.rincewind.interfaceapi.gui.elements.abstracts.Element;

public class Toolbar {
	
	private Map<Element, List<String>> elements;
	
	private String toolset;
	
	public Toolbar() {
		this.elements = new HashMap<>();
	}
	
	public List<Element> getElements() {
		return Collections.unmodifiableList(Arrays.asList(this.elements.keySet().toArray(new Element[this.elements.size()])));
	}
	
	public void addElement(Element element, String... toolsets) {
		this.elements.put(element, Arrays.asList(toolsets));
		element.setVisible(this.elements.get(element).contains(this.toolset));
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
	
}
