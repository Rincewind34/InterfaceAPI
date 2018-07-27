package de.rincewind.interfaceapi.handling.element;

import java.util.function.Consumer;

import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.handling.InterfaceListener;

public class ElementValueChangeEvent<T extends Element> extends ElementEvent<T> {
	
	public static <T> InterfaceListener<ElementValueChangeEvent<?>> consume(Class<T> cls, Consumer<T> action) {
		return (event) -> {
			action.accept(cls.cast(event.newValue));
		};
	}
	
	@SuppressWarnings("unchecked")
	public static <T> InterfaceListener<ElementValueChangeEvent<?>> consume(Consumer<T> action) {
		return (event) -> {
			action.accept((T) event.newValue);
		};
	}
	
	private final Object newValue;
	
	public ElementValueChangeEvent(T element, Object newValue) {
		super(element);
		
		this.newValue = newValue;
	}
	
}
