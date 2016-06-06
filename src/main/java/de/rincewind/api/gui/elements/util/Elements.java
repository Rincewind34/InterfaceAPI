package de.rincewind.api.gui.elements.util;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.plugin.gui.elements.CraftElementBrick;
import de.rincewind.plugin.gui.elements.CraftElementButton;
import de.rincewind.plugin.gui.elements.CraftElementCounter;
import de.rincewind.plugin.gui.elements.CraftElementInput;
import de.rincewind.plugin.gui.elements.CraftElementItem;
import de.rincewind.plugin.gui.elements.CraftElementList;
import de.rincewind.plugin.gui.elements.CraftElementOutput;
import de.rincewind.plugin.gui.elements.CraftElementScanner;
import de.rincewind.plugin.gui.elements.CraftElementSwitcher;
import de.rincewind.plugin.gui.elements.abstracts.CraftElement;
import de.rincewind.plugin.gui.elements.abstracts.CraftElementDisplayable;
import de.rincewind.plugin.gui.elements.abstracts.CraftElementSizeable;
import de.rincewind.plugin.gui.elements.abstracts.CraftElementSlot;

public abstract class Elements {
	
	public abstract static class ElementExtendable extends CraftElement {

		public ElementExtendable(Modifyable handle) {
			super(handle);
		}
		
	}
	
	public abstract static class ElementDisplayExtendable extends CraftElementDisplayable {

		public ElementDisplayExtendable(Modifyable handle) {
			super(handle);
		}
		
	}
	
	public abstract static class ElementSizeableExtendable extends CraftElementSizeable {

		public ElementSizeableExtendable(Modifyable handle) {
			super(handle);
		}
		
	}
	
	public abstract static class ElementSlotExtendable extends CraftElementSlot {

		public ElementSlotExtendable(Modifyable handle) {
			super(handle);
		}
		
	}
	
	public abstract static class ElementBrickExtendable extends CraftElementBrick {

		public ElementBrickExtendable(Modifyable handle) {
			super(handle);
		}
		
	}
	
	public abstract static class ElementButtonExtendable extends CraftElementButton {

		public ElementButtonExtendable(Modifyable handle) {
			super(handle);
		}
		
	}
	
	public abstract static class ElementCounterExtendable extends CraftElementCounter {

		public ElementCounterExtendable(Modifyable handle) {
			super(handle);
		}
		
	}
	
	public abstract static class ElementInputExtendable extends CraftElementInput {

		public ElementInputExtendable(Modifyable handle) {
			super(handle);
		}
		
	}
	
	public abstract static class ElementItemExtendable extends CraftElementItem {

		public ElementItemExtendable(Modifyable handle) {
			super(handle);
		}
		
	}
	
	public abstract static class ElementListExtendable<T> extends CraftElementList<T> {

		public ElementListExtendable(Modifyable handle) {
			super(handle);
		}
		
	}
	
	public abstract static class ElementOutputExtendable extends CraftElementOutput {

		public ElementOutputExtendable(Modifyable handle) {
			super(handle);
		}
		
	}
	
	public abstract static class ElementScannerExtendable extends CraftElementScanner {

		public ElementScannerExtendable(Modifyable handle) {
			super(handle);
		}
		
	}
	
	public abstract static class ElementSwitcherExtendable<T> extends CraftElementSwitcher<T> {

		public ElementSwitcherExtendable(Modifyable handle) {
			super(handle);
		}
		
	}
	
}