package de.rincewind.interfaceapi.gui.elements;

import java.util.Set;
import java.util.function.UnaryOperator;

import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.components.DisplayableDisabled;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.util.Direction;
import de.rincewind.interfaceapi.gui.windows.util.Toolbar;
import de.rincewind.interfaceplugin.Validate;
import de.rincewind.interfaceplugin.gui.elements.CraftElementToolbar;

public interface ElementToolbar extends Element, DisplayableDisabled {
	
	public static void setSelectInstructions(String instructions) {
		Validate.notNull(instructions, "The instructions cannot be null");
		
		CraftElementToolbar.INSTRUCTIONS_SELECT = instructions;
	}
	
	public static void setUnselectInstructions(String instructions) {
		Validate.notNull(instructions, "The instructions cannot be null");
		
		CraftElementToolbar.INSTRUCTIONS_UNSELECT = instructions;
	}
	
	public static void setSelectedInstructions(String instructions) {
		Validate.notNull(instructions, "The instructions cannot be null");
		
		CraftElementToolbar.INSTRUCTIONS_SELECTED = instructions;
	}
	
	public static String getSelectInstructions() {
		return CraftElementToolbar.INSTRUCTIONS_SELECT;
	}
	
	public static String getUnselectInstructions() {
		return CraftElementToolbar.INSTRUCTIONS_UNSELECT;
	}
	
	public static String getSelectedInstructions() {
		return CraftElementToolbar.INSTRUCTIONS_SELECTED;
	}
	
	public abstract void addButton(String toolset, Displayable icon);
	
	public abstract void removeButton(String toolset);
	
	public abstract void setCanUnselect(boolean value);
	
	public abstract void setDirection(Direction direction);
	
	public abstract void setSelectModifyer(UnaryOperator<Icon> modifier);
	
	public abstract boolean canUnselect();
	
	public abstract Direction getDirection();
	
	public abstract Toolbar getToolbar();
	
	public abstract UnaryOperator<Icon> getSelectModifier();
	
	public abstract Set<String> getSelectableToolsets();
	
}
