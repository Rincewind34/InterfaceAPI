package de.rincewind.interfaceplugin.gui.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;

import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.windows.util.Toolbar;

public class CraftToolbar implements Toolbar {

	private Map<Element, Set<String>> elements;

	private String toolset;

	public CraftToolbar() {
		this.elements = new HashMap<>();
	}

	@Override
	public void addElement(Element element, String... toolsets) {
		this.elements.put(element, Sets.newHashSet(toolsets));
		element.setVisible(this.elements.get(element).contains(this.toolset));
	}

	@Override
	public void addElements(Iterable<Element> elements, String... toolsets) {
		for (Element element : elements) {
			this.addElement(element, toolsets);
		}
	}

	@Override
	public void removeElement(Element element) {
		element.setVisible(true);
		this.elements.remove(element);
	}

	@Override
	public void removeElements(Iterable<Element> elements) {
		for (Element element : elements) {
			this.removeElement(element);
		}
	}

	@Override
	public void activateToolSet(String toolset) {
		if (Objects.equals(toolset, this.toolset)) {
			return;
		}

		this.toolset = toolset;

		for (Element element : this.elements.keySet()) {
			if (this.toolset == null) {
				element.setVisible(false);
			} else {
				element.setVisible(this.elements.get(element).contains(this.toolset));
			}
		}
	}

	@Override
	public void deactivateToolset() {
		if (this.toolset != null) {
			this.toolset = null;

			for (Element element : this.elements.keySet()) {
				element.setVisible(false);
			}
		}
	}

	@Override
	public boolean isToolsetActive() {
		return this.toolset != null;
	}

	@Override
	public String getActiveToolset() {
		return this.toolset;
	}

	@Override
	public Runnable actiovationListener(String toolset) {
		return () -> {
			this.activateToolSet(toolset);
		};
	}

	@Override
	public Set<Element> getElements() {
		return Collections.unmodifiableSet(this.elements.keySet());
	}

	@Override
	public Set<Element> getElements(String toolset) {
		return Collections.unmodifiableSet(this.elements.entrySet().stream().filter((entry) -> {
			return entry.getValue().contains(toolset);
		}).map(Map.Entry::getKey).collect(Collectors.toSet()));
	}

}
