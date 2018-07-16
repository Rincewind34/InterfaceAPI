package de.rincewind.interfaceapi.gui.elements.util;

import java.util.function.UnaryOperator;

import org.bukkit.Material;

public class SelectModifiers {
	
	public static final UnaryOperator<Icon> DISABLED = UnaryOperator.identity();
	
	public static final UnaryOperator<Icon> ENCHANT = Icon::enchant;
	
	public static final UnaryOperator<Icon> MAGENTA_GLASS = (icon) -> {
		return icon.typecast(Material.MAGENTA_STAINED_GLASS);
	};
	
	public static final UnaryOperator<Icon> CYAN_GLASS = (icon) -> {
		return icon.typecast(Material.CYAN_STAINED_GLASS).damage(9);
	};
	
}
