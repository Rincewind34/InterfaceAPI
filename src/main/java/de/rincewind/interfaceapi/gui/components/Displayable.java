package de.rincewind.interfaceapi.gui.components;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.bukkit.Material;

import de.rincewind.interfaceapi.gui.elements.util.Icon;

public interface Displayable {

	public static final Map<Class<?>, Function<Object, Icon>> converters = new HashMap<>();

	@SuppressWarnings("unchecked")
	public static <T> void put(Class<T> cls, Function<T, Icon> converter) {
		Displayable.converters.put(cls, (Function<Object, Icon>) converter);
	}
	
	public static boolean isConvertable(Class<?> cls) {
		return Displayable.converters.containsKey(cls);
	}
	
	public static Displayable of(Object payload) {
		if (payload instanceof Displayable) {
			return (Displayable) payload;
		} else if (payload != null && Displayable.converters.containsKey(payload.getClass())) {
			return Displayable.converters.get(payload.getClass()).apply(payload);
		} else {
			return Displayable.of(new Icon(Material.BEDROCK, 0, payload != null ? payload.toString() : "null"), payload);
		}
	}
	
	public static Displayable of(Icon icon, Object payload) {
		if (icon == null) {
			throw new IllegalArgumentException();
		}
		
		return new SimpleDisplay(icon, payload);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T readPayload(Displayable input) {
		if (input == null) {
			throw new IllegalArgumentException();
		}
		
		if (input instanceof UserMemory) {
			return ((UserMemory) input).getUserObject();
		} else {
			return (T) input;
		}
	}
	
	public static Icon validate(Displayable input) {
		return input != null ? input.getIcon() : Icon.AIR;
	}

	public abstract Icon getIcon();

	public default void setIcon(Icon icon) {
		throw new UnsupportedOperationException();
	}

}
