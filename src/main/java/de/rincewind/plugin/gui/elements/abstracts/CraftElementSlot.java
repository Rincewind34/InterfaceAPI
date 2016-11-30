package de.rincewind.plugin.gui.elements.abstracts;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.abstracts.Element;
import de.rincewind.api.gui.elements.abstracts.ElementSlot;
import de.rincewind.api.gui.elements.util.ClickAction;
import de.rincewind.api.gui.elements.util.Icon;
import de.rincewind.api.gui.elements.util.Point;
import de.rincewind.api.handling.element.ElementInteractEvent;

public abstract class CraftElementSlot extends CraftElement implements ElementSlot {

	private ItemStack content;

	public CraftElementSlot(Modifyable handle) {
		super(handle);

		this.content = null;

		this.getComponent(Element.ENABLED).setEnabled(true);

		this.getEventManager().registerListener(ElementInteractEvent.class, (event) -> {
			if (this.isEnabled()) {
				if (event.getPlayer().getItemOnCursor() != null && event.getPlayer().getItemOnCursor().getType() != Material.AIR
						&& this.getBlocker().allows(ClickAction.PLACE)) {
					this.setContent(event.getPlayer().getItemOnCursor());
				} else if (this.getBlocker().allows(ClickAction.PICKUP)) {
					this.setContent(null);
				}
			}
		}).addAfter();
	}

	@Override
	public boolean isEmpty() {
		return this.content == null || this.content.equals(Modifyable.EMPTY_USED_SLOT);
	}

	@Override
	public ItemStack getCurrentContent() {
		return this.getContent().clone();
	}

	@Override
	public Icon getIcon(Point point) {
		super.getIcon(point);

		if (this.isEnabled()) {
			if (this.content == null) {
				return Icon.AIR;
			} else {
				return new Icon(this.content).showInfo(true);
			}
		} else {
			return new Icon(Material.BARRIER, 0, "§7");
		}
	}

	public ItemStack getContent() {
		return this.content;
	}

	public void setContent(ItemStack item) {
		this.content = item;
		this.update();
	}

}