package de.rincewind.interfaceplugin.gui.elements.abstracts;

import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.abstracts.ElementDisplayable;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowEditor;

public abstract class CraftElementDisplayable extends CraftElement implements ElementDisplayable {
	
	private Displayable display;
	private Displayable disabledDisplay;
	
	public CraftElementDisplayable(WindowEditor handle) {
		super(handle);
		
		this.display = Icon.AIR;
		this.disabledDisplay = Icon.AIR;
		
		this.getComponent(Element.ENABLED).setEnabled(true);
	}
	
	@Override
	public void setIcon(Displayable icon) {
		CraftElement.clearInstructions(this.display);
		this.display = Displayable.checkNull(icon);
		this.update();
	}
	
	@Override
	public void setDisabledIcon(Displayable icon) {
		this.disabledDisplay = Displayable.checkNull(icon);
		this.update();
	}
	
	@Override
	public Icon getIcon() {
		return this.display.getIcon();
	}

	@Override
	public Icon getDisabledIcon() {
		return this.disabledDisplay.getIcon();
	}
	
	@Override
	protected Icon getIcon0(Point point) {
		if (this.isEnabled()) {
			return this.updateInstructions(this.getIcon());
		} else {
			return this.getDisabledIcon();
		}
	}
	
	protected final Icon updateInstructions(Icon icon) {
		return this.updateInstructions(icon, this.currentInstructions());
	}
	
	protected String currentInstructions() {
		return null;
	}
	
}
