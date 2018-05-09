package de.rincewind.interfaceplugin.gui.elements.abstracts;

import org.bukkit.inventory.ItemStack;

import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.abstracts.ElementSlot;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowEditor;
import de.rincewind.interfaceapi.handling.element.ElementSlotChangeEvent;
import de.rincewind.interfaceapi.handling.element.ElementStackChangeEvent;
import de.rincewind.interfaceapi.util.InterfaceUtils;

public abstract class CraftElementSlot extends CraftElement implements ElementSlot {

	private Icon disabledIcon;

	private ItemStack content;

	public CraftElementSlot(WindowEditor handle) {
		super(handle);

		this.content = null;
		this.disabledIcon = Icon.AIR;

		this.getComponent(Element.ENABLED).setEnabled(true);

		this.getEventManager().registerListener(ElementStackChangeEvent.class, (event) -> {
			if (this.content == null && event.getSlotItem() == null || (this.content != null && this.content.isSimilar(event.getSlotItem()))) {
				event.consume();
				return;
			}
			
			ItemStack previous = this.content;
			this.setContent(event.getSlotItem());
			
			this.getEventManager().callEvent(ElementSlotChangeEvent.class, new ElementSlotChangeEvent(this, previous, this.content));
		}).addAfter();
	}

	@Override
	public void setDisabledIcon(Displayable icon) {
		this.disabledIcon = Displayable.validate(icon);
	}

	@Override
	public boolean isEmpty() {
		return this.content == null;
	}

	@Override
	public ItemStack getCurrentContent() {
		return this.getContent().clone();
	}

	@Override
	public Icon getDisabledIcon() {
		return this.disabledIcon;
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
			return this.disabledIcon;
		}
	}

	public ItemStack getContent() {
		return this.content;
	}

	public void setContent(ItemStack item) {
		this.content = InterfaceUtils.normalize(item);
		this.update();
	}

}