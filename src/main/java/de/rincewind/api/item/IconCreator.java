package de.rincewind.api.item;

import java.util.function.Supplier;

import de.rincewind.api.gui.elements.util.Icon;

@Deprecated
public interface IconCreator extends Supplier<Icon> {
	
	public abstract Icon createIcon();
	
	@Override
	public default Icon get() {
		return this.createIcon();
	}
	
}
