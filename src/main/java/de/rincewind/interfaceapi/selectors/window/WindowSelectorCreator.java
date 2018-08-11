package de.rincewind.interfaceapi.selectors.window;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.bukkit.plugin.Plugin;

import de.rincewind.interfaceapi.exceptions.SelectorInterfaceException;
import de.rincewind.interfaceplugin.Validate;

public class WindowSelectorCreator<T, U extends WindowSelector<T>> {

	private final Class<T> selectingClass;
	private final Class<U> windowClass;
	private final Constructor<U> constructor;

	private final Supplier<Collection<? extends T>> elementGetter;

	public WindowSelectorCreator(Class<T> selectingClass, Class<U> windowClass) {
		this(selectingClass, windowClass, null);
	}

	public WindowSelectorCreator(Class<T> selectingClass, Class<U> windowClass, Supplier<Collection<? extends T>> elementGetter) {
		Validate.notNull(selectingClass, "The type class cannot be null");
		Validate.notNull(windowClass, "The window class cannot be null");

		this.selectingClass = selectingClass;
		this.windowClass = windowClass;
		this.elementGetter = elementGetter;

		if (this.windowClass.isInterface() || Modifier.isAbstract(this.windowClass.getModifiers())) {
			throw new IllegalArgumentException("The window class " + this.windowClass + " is abstract");
		}

		for (Constructor<?> constructor : this.windowClass.getDeclaredConstructors()) {
			if (Arrays.equals(constructor.getParameterTypes(),
					new Class<?>[] { Plugin.class, Consumer.class, Collection.class, boolean.class, this.selectingClass })) {

				try {
					Constructor<U> target = this.windowClass.getConstructor(constructor.getParameterTypes());
					target.setAccessible(true);

					this.constructor = target;
					return;
				} catch (NoSuchMethodException | SecurityException | IllegalArgumentException exception) {
					assert false : "Should be unreachable: " + exception.getMessage();
					throw new RuntimeException(exception);
				}
			}
		}

		throw new IllegalArgumentException("The selector class " + this.windowClass + " does not provide a valid constructor");

	}

	public final Class<U> getWindowClass() {
		return this.windowClass;
	}

	public final Class<T> getSelectingClass() {
		return this.selectingClass;
	}

	public final Constructor<U> getConstructor() {
		return this.constructor;
	}

	public final U newWindow(Plugin plugin, Consumer<? super T> action) {
		if (this.elementGetter == null) {
			throw new UnsupportedOperationException("The element getter was not set");
		}

		return this.newWindow(plugin, action, this.elementGetter.get());
	}

	public final U newWindow(Plugin plugin, Consumer<? super T> action, T defaultValue) {
		if (this.elementGetter == null) {
			throw new UnsupportedOperationException("The element getter was not set");
		}

		return this.newWindow(plugin, action, this.elementGetter.get(), defaultValue);
	}

	public final U newWindow(Plugin plugin, Consumer<? super T> action, Collection<? extends T> elements) {
		return this.newWindow(plugin, action, elements, false, null);
	}

	public final U newWindow(Plugin plugin, Consumer<? super T> action, Collection<? extends T> elements, T defaultValue) {
		return this.newWindow(plugin, action, elements, true, defaultValue);
	}

	private U newWindow(Plugin plugin, Consumer<? super T> action, Collection<? extends T> elements, boolean defaultSet, T defaultValue) {
		// Let WindowSelector do null validation

		try {
			U selector = this.constructor.newInstance(plugin, action, elements, defaultSet, defaultValue);

			assert selector.getSelectingClass() == this.selectingClass : "Type class of new selector window does not match request " + this.selectingClass;
			return selector;
		} catch (ClassCastException exception) {
			assert false : "Failed with ClassCastException that should have been caught in register method";
			throw exception;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException exception) {
			assert false : "Failed with reflective exception (" + exception.getClass().getSimpleName() + ") that should have been caught in register method: "
					+ exception.getMessage();
			throw new SelectorInterfaceException(exception);
		} catch (InvocationTargetException exception) {
			throw new SelectorInterfaceException(exception);
		}
	}

}
