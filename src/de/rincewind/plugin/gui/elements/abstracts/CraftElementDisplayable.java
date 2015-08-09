package de.rincewind.plugin.gui.elements.abstracts;

import org.apache.commons.lang.Validate;
import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.abstracts.ElementDisplayable;

public abstract class CraftElementDisplayable extends CraftElement implements ElementDisplayable {
	
	private ItemStack icon;
	private ItemStack disabledIcon;
	
	public CraftElementDisplayable(Modifyable handle) {
		super(handle);
		
		this.icon = Modifyable.INVISIBLE_ELEMENT;
		this.disabledIcon = this.getIcon();
	}
	
	@Override
	public ItemStack getIcon() {
		return this.icon;
	}

	@Override
	public ItemStack getDisabledIcon() {
		return this.disabledIcon;
	}
	
	@Override
	public void setIcon(ItemStack icon) {
		Validate.notNull(icon, "The icon cannot be null!");
		
		this.icon = icon;
		this.getHandle().readItemsFrom(this);
	}
	
	@Override
	public void setDisabledIcon(ItemStack icon) {
		this.disabledIcon = icon;
		this.getHandle().readItemsFrom(this);
	}
	
	@Override
	public void updateItemMap() {
		this.updateItemMap(new Point(0, 0), true);
	}
	
	protected final void updateItemMap(Point point, boolean withIcon) {
		if (!this.isVisible()) {
			this.setItemAt(point, Modifyable.INVISIBLE_ELEMENT);
			return;
		}
		
		if (!this.isEnabled() && this.disabledIcon != null) {
			this.setItemAt(point, this.disabledIcon);
		} else if (withIcon) {
			this.setItemAt(point, this.icon);
		}
	}

}
