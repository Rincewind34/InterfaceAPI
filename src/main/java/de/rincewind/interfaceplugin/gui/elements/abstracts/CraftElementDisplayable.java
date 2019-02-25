package de.rincewind.interfaceplugin.gui.elements.abstracts;

import java.util.function.UnaryOperator;

import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.components.DisplayableDisabled;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.abstracts.ElementDisplayable;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.util.Point;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowEditor;

public abstract class CraftElementDisplayable extends CraftElement implements ElementDisplayable {

	private boolean workInstructions;

	private Displayable display;
	private Displayable disabledDisplay;

	public CraftElementDisplayable(WindowEditor handle) {
		super(handle);

		this.display = Icon.AIR;
		this.disabledDisplay = DisplayableDisabled.default_icon;
		this.workInstructions = true;

		this.getComponent(Element.ENABLED).setEnabled(true);
	}

	@Override
	public void setIcon(Displayable icon) {
		if (this.workInstructions) {
			CraftElement.clearInstructions(this.display);
		}

		this.display = Displayable.checkNull(icon);

		if (this.isEnabled()) {
			this.update();
		}
	}

	@Override
	public void setDisabledIcon(Displayable icon) {
		this.disabledDisplay = Displayable.checkNull(icon);

		if (!this.isEnabled()) {
			this.update();
		}
	}

	@Override
	public void setDisabledIconModified(UnaryOperator<Icon> modifier) {
		this.setDisabledIcon(modifier.apply(this.display.hasStaticIcon() ? this.display.getIcon().clone() : this.display.getIcon()));
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
			Icon icon = this.getIcon();

			if (this.workInstructions) {
				return this.updateInstructions(icon);
			} else {
				return icon;
			}
		} else {
			return this.getDisabledIcon();
		}
	}

	protected void setWorkInstructions(boolean workInstructions) {
		this.workInstructions = workInstructions;
	}

	protected boolean isWorkInstructions() {
		return this.workInstructions;
	}

	protected String currentInstructions() {
		return null;
	}

	protected final Icon updateInstructions(Icon icon) {
		return this.updateInstructions(icon, this.currentInstructions());
	}

}
