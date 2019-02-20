package de.rincewind.interfaceapi.gui.elements;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

import org.bukkit.block.data.Directional;

public interface ElementLineTree {
	
	public abstract void setDirection();

	public abstract void setValue(int value);

	public abstract void setCanChangeValue(boolean value);

	public abstract void setSelectionPredicate(Predicate<Integer> predicate);

	public abstract void setIconModifer(BiConsumer<Integer, TreeElementState> modifier);

	public abstract boolean canChangeValue();

	public abstract Directional getDirection();

	public abstract Predicate<Integer> getSelectionPredicate();

	public static enum TreeElementState {

		ON_VALUE, BELOW_VALUE, BEYOND_VALUE;

	}

}
