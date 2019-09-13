package de.rincewind.interfaceplugin.gui.elements.abstracts;

import org.bukkit.inventory.ItemStack;

import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.components.DisplayableDisabled;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.abstracts.ElementSlot;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.util.Point;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowEditor;
import de.rincewind.interfaceapi.handling.element.ElementSlotChangeEvent;
import de.rincewind.interfaceapi.handling.element.ElementStackChangeEvent;
import de.rincewind.interfaceapi.util.InterfaceUtils;

public abstract class CraftElementSlot extends CraftElement implements ElementSlot {

	private Displayable disabledIcon;

	private ItemStack content;

	public CraftElementSlot(WindowEditor handle) {
		super(handle);

		this.content = null;
		this.disabledIcon = DisplayableDisabled.default_icon;

		this.getComponent(Element.ENABLED).setEnabled(true);

		this.getEventManager().registerListener(ElementStackChangeEvent.class, (event) -> {
//			if (this.content == null && event.getSlotItem() == null
//					|| (this.content != null && this.content.isSimilar(event.getSlotItem()))) {
//				System.out.println("CONSUME SLOT");
//				event.consume();
//			}
			
			this.setContent(event.getSlotItem());
		}).addAfter();

//		this.getEventManager().registerListener(ElementStackChangeEvent.class, (event) -> {
//			System.out.println("SET CONTENT: " + event.isConsumed());
//			if (!event.isConsumed()) {
//				this.setContent(event.getSlotItem());
//			}
//		}).monitor();
	}

	@Override
	public void setDisabledIcon(Displayable icon) {
		this.disabledIcon = Displayable.checkNull(icon);
	}

	@Override
	public boolean isEmpty() {
		return this.content == null;
	}

	@Override
	public ItemStack getCurrentContent() {
		return this.isEmpty() ? null : this.content.clone();
	}

	@Override
	public Icon getDisabledIcon() {
		return this.disabledIcon.getIcon();
	}

	@Override
	protected Icon getIcon0(Point point) {
		if (this.isEnabled()) {
			if (this.content == null) {
				return Icon.AIR;
			} else {
				return new Icon(this.content);
			}
		} else {
			return this.getDisabledIcon();
		}
	}

	public void setContent(ItemStack item) {
		assert item == null || item.getAmount() <= this.getMaxStackSize() : "The amount is bigger than maxstacksize";

		ItemStack previous = this.content;

		this.content = InterfaceUtils.normalize(item);
		this.update();

		this.getEventManager().callEvent(ElementSlotChangeEvent.class, new ElementSlotChangeEvent(this, previous, this.content));
	}

}