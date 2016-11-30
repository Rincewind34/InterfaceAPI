package de.rincewind.interfaceapi.gui.elements.util;

import de.rincewind.interfaceapi.gui.components.Modifyable;
import de.rincewind.interfaceplugin.gui.elements.CraftElementBrick;
import de.rincewind.interfaceplugin.gui.elements.CraftElementButton;
import de.rincewind.interfaceplugin.gui.elements.CraftElementCounter;
import de.rincewind.interfaceplugin.gui.elements.CraftElementInput;
import de.rincewind.interfaceplugin.gui.elements.CraftElementItem;
import de.rincewind.interfaceplugin.gui.elements.CraftElementList;
import de.rincewind.interfaceplugin.gui.elements.CraftElementOutput;
import de.rincewind.interfaceplugin.gui.elements.CraftElementScanner;
import de.rincewind.interfaceplugin.gui.elements.CraftElementSwitcher;
import de.rincewind.interfaceplugin.gui.elements.abstracts.CraftElement;
import de.rincewind.interfaceplugin.gui.elements.abstracts.CraftElementDisplayable;
import de.rincewind.interfaceplugin.gui.elements.abstracts.CraftElementSlot;

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
	
	public abstract static class ElementListExtendable extends CraftElementList {

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
	
	public abstract static class ElementSwitcherExtendable extends CraftElementSwitcher {

		public ElementSwitcherExtendable(Modifyable handle) {
			super(handle);
		}
		
	}
	
}