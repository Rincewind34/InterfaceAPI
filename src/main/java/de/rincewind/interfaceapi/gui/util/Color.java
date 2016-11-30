package de.rincewind.interfaceapi.gui.util;

import org.bukkit.Material;

import de.rincewind.interfaceapi.gui.elements.util.Icon;

public enum Color {
	
	WHITE(0),
	ORANGE(1),
	MAGENTA(2),
	LIGHT_BLUE(3),
	YELLOW(4),
	LIME(5),
	PINK(6),
	GRAY(7),
	LIGHT_GRAY(8),
	CYAN(9),
	PURPLE(10),
	BLUE(11),
	BROWN(12),
	GREEN(13),
	RED(14),
	BLACK(15),
	
	TRANSLUCENT(-1);
	
	private int data;
	
	private Color(int data) {
		this.data = data;
	}
	
	public Icon asIcon() {
		if (this.data == -1) {
			return new Icon(Material.AIR);
		} else {
			return new Icon(Material.STAINED_GLASS_PANE, this.data).rename("§7");
		}
	}
	
}
