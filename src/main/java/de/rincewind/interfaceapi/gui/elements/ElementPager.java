package de.rincewind.interfaceapi.gui.elements;

import java.util.List;

import de.rincewind.interfaceapi.gui.components.DisplayableDisabled;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.ElementSet;
import de.rincewind.interfaceapi.gui.util.Color;
import de.rincewind.interfaceapi.handling.InterfaceListener;
import de.rincewind.interfaceapi.handling.element.ElementInteractEvent;

public interface ElementPager extends Element, DisplayableDisabled {
	
	public abstract void addPageElement(ElementSet elements, int height);
	
	public abstract void removePageElement(ElementSet elements);
	
	public abstract void nextPage();
	
	public abstract void previousPage();
	
	public abstract void setPage(int page);

	public abstract void setColor(Color color);
	
	public abstract void clear();
	
	public abstract void registerNextPageFliper(Element element);
	
	public abstract void registerPreviousPageFliper(Element element);
	
	public abstract void unregisterFliper(Element element);
	
	public abstract boolean isFirstPage();
	
	public abstract boolean isLastPage();
	
	public abstract Color getColor();
	
	public abstract int getPage();
	
	public abstract int getMaxPage();
	
	public abstract int size();
	
	public abstract InterfaceListener<ElementInteractEvent> newFlipListener(int offset);
	
	public abstract List<ElementSet> getCurrentPageElements();
	
	public abstract List<ElementSet> getPageElements();
	
	public abstract List<ElementSet> getPageElements(int page);
	
}
