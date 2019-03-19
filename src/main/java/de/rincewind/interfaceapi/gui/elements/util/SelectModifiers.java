package de.rincewind.interfaceapi.gui.elements.util;

import java.util.function.UnaryOperator;

import org.bukkit.Material;

public class SelectModifiers {
	
	public static final UnaryOperator<Icon> DISABLED = Icon::clone;
	
	public static final UnaryOperator<Icon> ENCHANT = SelectModifiers.newModifier(Icon::enchant);
	
	public static final UnaryOperator<Icon> MAGENTA_GLASS = SelectModifiers.newModifier((icon) -> {
		return icon.typecast(Material.MAGENTA_STAINED_GLASS);
	});
	
	public static final UnaryOperator<Icon> PINK_GLASS = SelectModifiers.newModifier((icon) -> {
		return icon.typecast(Material.PINK_STAINED_GLASS);
	});
	
	public static final UnaryOperator<Icon> CYAN_GLASS = SelectModifiers.newModifier((icon) -> {
		return icon.typecast(Material.CYAN_STAINED_GLASS);
	});
	
	public static UnaryOperator<Icon> newModifier(UnaryOperator<Icon> operator) {
		return (icon) -> {
			return operator.apply(icon.clone());
		};
	}
	
}
