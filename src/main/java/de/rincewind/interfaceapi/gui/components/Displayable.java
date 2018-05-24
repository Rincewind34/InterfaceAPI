package de.rincewind.interfaceapi.gui.components;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.bukkit.Material;

import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceplugin.Validate;

public interface Displayable {

	public static final Map<Class<?>, Function<Object, Icon>> converters = new HashMap<>();
	
	public static void copy(Class<?> cls, Class<?> other) {
		Validate.notNull(cls, "The class cannot be null");
		Validate.notNull(other, "The other class cannot be null");
		
		if (Displayable.isConvertable(cls)) {
			throw new IllegalArgumentException("The class " + cls + " is not convertable");
		}
		
		Displayable.converters.put(other, Displayable.converters.get(cls));
	}

	@SuppressWarnings("unchecked")
	public static <T> void put(Class<T> cls, Function<T, Icon> converter) {
		Validate.notNull(cls, "The class cannot be null");
		Validate.notNull(converter, "The converter cannot be null");
		
		Displayable.converters.put(cls, (Function<Object, Icon>) converter);
	}

	public static boolean isConvertable(Class<?> cls) {
		Validate.notNull(cls, "The class cannot be null");
		
		return Displayable.converters.containsKey(cls);
	}

	public static Icon validate(Displayable icon) {
		return icon == null ? Icon.AIR : icon.getIcon();
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

	public static Displayable of(Object payload, String name) {
		Displayable result = Displayable.of(payload);

		if (!result.hasStaticIcon()) {
			throw new UnsupportedOperationException();
		}

		result.getIcon().rename(name);
		return result;
	}

	public static Displayable of(Icon icon, Object payload) {
		if (icon == null) {
			throw new IllegalArgumentException();
		}

		return new SimpleDisplay(icon, payload);
	}

	@SuppressWarnings("unchecked")
	public static <T> T readPayload(Displayable input) {
		Validate.notNull(input, "The input cannot be null");

		if (input instanceof UserMemory) {
			return ((UserMemory) input).getUserObject();
		} else {
			return (T) input;
		}
	}

	public abstract Icon getIcon();

	/**
	 * Returns <code>true</code> if {{@link #getIcon()}} does always return the
	 * same reference.<br>
	 * The result of this method has to be final too independent
	 * to {@link #getIcon()}.<br>
	 * If this method returns <code>false</code> there is no need that the
	 * reference resulting from {@link #getIcon()} changes with every call.
	 * 
	 * @return if the icon instance of {@link #getIcon()} is finalized.
	 */
	public default boolean hasStaticIcon() {
		return false;
	}

}
