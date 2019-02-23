package de.rincewind.interfaceapi.gui.components;

import org.bukkit.Material;

import de.rincewind.interfaceapi.gui.elements.util.Icon;

public interface DisplayableDisabled {

	public static final Icon default_icon = new Icon(Material.FIREWORK_STAR);

	public abstract void setDisabledIcon(Displayable icon);

	public abstract Icon getDisabledIcon();

}
