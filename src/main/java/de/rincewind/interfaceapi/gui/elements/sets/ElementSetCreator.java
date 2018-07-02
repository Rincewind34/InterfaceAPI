package de.rincewind.interfaceapi.gui.elements.sets;

import java.util.Collection;

import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceapi.gui.util.creators.ElementCreator;
import de.rincewind.interfaceapi.gui.util.creators.ElementCreatorCloseable;
import de.rincewind.interfaceapi.gui.util.creators.ElementCreatorLogging;

public abstract class ElementSetCreator {
	
	public ElementSet createElements(ElementCreator creator, Point point, int width, int height) {
		ElementCreatorLogging logger = new ElementCreatorLogging(creator);
		ElementCreatorCloseable closeableCreator = new ElementCreatorCloseable(logger);
		
		this.create(closeableCreator, width, height);
		closeableCreator.close();
		
		for (Element element : logger.getLog()) {
			element.setPoint(element.getPoint().add(point));
		}
		
		return this.newElementSet(logger.getLog());
	}
	
	protected abstract void create(ElementCreator creator, int width, int height);
	
	protected ElementSet newElementSet(Collection<Element> collection) {
		return new ElementSet(collection);
	}
	
}
