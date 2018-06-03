package de.rincewind.interfaceplugin.gui.elements.abstracts;

import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.abstracts.ElementDisplayable;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowEditor;

public abstract class CraftElementDisplayable extends CraftElement implements ElementDisplayable {
	
	private Displayable icon;
	private Displayable disabledIcon;
	
	public CraftElementDisplayable(WindowEditor handle) {
		super(handle);
		
		this.icon = Icon.AIR;
		this.disabledIcon = Icon.AIR;
		
		this.getComponent(Element.ENABLED).setEnabled(true);
	}
	
	@Override
	public void setIcon(Displayable icon) {
		this.icon = icon;
		this.update();
	}
	
	@Override
	public void setDisabledIcon(Displayable icon) {
		this.disabledIcon = icon;
		this.update();
	}
	
	@Override
	public Icon getIcon() {
		return Displayable.validate(this.icon);
	}

	@Override
	public Icon getDisabledIcon() {
		return Displayable.validate(this.disabledIcon);
	}
	
	@Override
	protected Icon getIcon0(Point point) {
		if (this.isEnabled()) {
			return this.getIcon();
		} else {
			return this.getDisabledIcon();
		}
	}
	
	protected Displayable getDisplayableEnabled() {
		return this.icon;
	}
	
	protected Displayable getDisplayableDisabled() {
		return this.disabledIcon;
	}
	
}
