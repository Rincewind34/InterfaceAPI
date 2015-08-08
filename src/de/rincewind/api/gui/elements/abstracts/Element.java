package de.rincewind.api.gui.elements.abstracts;

import de.rincewind.api.gui.ClickBlocker;
import de.rincewind.api.gui.components.Activatable;
import de.rincewind.api.gui.components.Locatable;
import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.plugin.gui.elements.abstracts.CraftElement;

public abstract interface Element extends Locatable, Activatable {
	
	public abstract int getWidth();
	
	public abstract int getHeight();
	
	public abstract void setBlocker(ClickBlocker blocker);
	
	public abstract void setVisible(boolean visible);
	
	public abstract boolean isVisible();
	
	public abstract ClickBlocker getBlocker();
	
	
	public abstract static class ElementExtendable extends CraftElement {

		public ElementExtendable(Modifyable handle) {
			super(handle);
		}

	}
}
