package de.rincewind.interfaceplugin.gui.windows.abstracts;

import org.bukkit.plugin.Plugin;

import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.util.Color;
import de.rincewind.interfaceapi.gui.util.Point;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowColorable;
import de.rincewind.interfaceplugin.Validate;

public abstract class CraftWindowColorable extends CraftWindowEditor implements WindowColorable {
	
	private Color color;
	
	public CraftWindowColorable(Plugin plugin) {
		super(plugin);
		
		this.color = Color.NONE;
	}
	
	@Override
	public Color getColor() {
		return this.color;
	}
	
	@Override
	public void setColor(Color color) {
		Validate.notNull(color, "The color cannot be null!");
		
		this.color = color;
		this.renderFrame();
	}
	
	@Override
	public Icon getIcon(Point point) {
		Icon icon = super.getIcon(point);
		
		if (icon == null) {
			return this.color.asIcon();
		} else {
			return icon;
		}
	}
	
}
