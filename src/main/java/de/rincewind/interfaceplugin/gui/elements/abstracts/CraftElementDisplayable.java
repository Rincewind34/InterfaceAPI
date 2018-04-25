package de.rincewind.interfaceplugin.gui.elements.abstracts;

import de.rincewind.interfaceapi.gui.components.Modifyable;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.abstracts.ElementDisplayable;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.elements.util.Point;

public abstract class CraftElementDisplayable extends CraftElement implements ElementDisplayable {
	
	private Icon icon;
	private Icon disabledIcon;
	
	public CraftElementDisplayable(Modifyable handle) {
		super(handle);
		
		this.icon = Icon.AIR;
		this.disabledIcon = Icon.AIR;
		
		this.getComponent(Element.ENABLED).setEnabled(true);
	}
	
	@Override
	public void setIcon(Icon icon) {
		if (icon != null) {
			this.icon = icon;
		} else {
			this.icon = Icon.AIR;
		}
		
		this.update();
	}
	
	@Override
	public void setDisabledIcon(Icon icon) {
		if (icon != null) {
			this.disabledIcon = icon;
		} else {
			this.disabledIcon = Icon.AIR;
		}
		
		this.update();
	}
	
	@Override
	public Icon getIcon() {
		return this.icon;
	}

	@Override
	public Icon getDisabledIcon() {
		return this.disabledIcon;
	}
	
	@Override
	public Icon getIcon(Point point) {
		super.getIcon(point);
		
		if (this.isEnabled()) {
			return this.getIcon();
		} else {
			return this.getDisabledIcon();
		}
	}
	
}
