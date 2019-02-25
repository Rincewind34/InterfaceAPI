package de.rincewind.interfaceapi.gui.components;

import java.util.function.UnaryOperator;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import de.rincewind.interfaceapi.gui.elements.util.Icon;

public interface DisplayableDisabled {

	public static final Icon default_icon = new Icon(Material.FIREWORK_STAR);

	public static final UnaryOperator<Icon> default_modifier = (icon) -> {
		return icon.rename("§8" + ChatColor.stripColor(icon.getName())).typecast(Material.FIREWORK_STAR);
	};

	public abstract void setDisabledIcon(Displayable icon);

	public abstract Icon getDisabledIcon();

}
