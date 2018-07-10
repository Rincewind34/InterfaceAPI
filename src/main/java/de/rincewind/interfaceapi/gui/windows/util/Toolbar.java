package de.rincewind.interfaceapi.gui.windows.util;

import java.util.Set;

import de.rincewind.interfaceapi.gui.elements.abstracts.Element;

public interface Toolbar {

	public abstract void addElement(Element element, String... toolsets);

	public abstract void addElements(Iterable<Element> elements, String... toolsets);

	public abstract void removeElement(Element element);

	public abstract void removeElements(Iterable<Element> elements);

	public abstract void activateToolSet(String toolset);
	
	public abstract void deactivateToolset();
	
	public abstract boolean isToolsetActive();

	public abstract String getActiveToolset();

	public abstract Runnable actiovationListener(String toolset);

	public abstract Set<Element> getElements();

	public abstract Set<Element> getElements(String toolset);

}