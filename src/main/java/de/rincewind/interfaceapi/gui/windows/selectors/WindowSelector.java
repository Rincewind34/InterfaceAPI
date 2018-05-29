package de.rincewind.interfaceapi.gui.windows.selectors;

import java.util.function.Consumer;

import org.bukkit.plugin.Plugin;

import de.rincewind.interfaceplugin.gui.windows.CraftWindowSizeable;

public abstract class WindowSelector<T> extends CraftWindowSizeable {

	private final Class<T> typeClass;
	private final Iterable<T> typeElements;
	private final Consumer<T> onSelect;

	public WindowSelector(Plugin plugin, Consumer<T> onSelect, Iterable<T> typeElements, Class<T> typeClass) {
		super(plugin);

		this.typeClass = typeClass;
		this.typeElements = typeElements;
		this.onSelect = onSelect;
	}

	public final Class<T> getTypeClass() {
		return this.typeClass;
	}
	
	public final Iterable<T> getTypeElements() {
		return this.typeElements;
	}

	protected final Consumer<T> getOnSelect() {
		return this.onSelect;
	}

}