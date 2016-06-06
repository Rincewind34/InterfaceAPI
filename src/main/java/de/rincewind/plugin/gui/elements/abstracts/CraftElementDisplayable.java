package de.rincewind.plugin.gui.elements.abstracts;

import org.apache.commons.lang.Validate;
import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.abstracts.ElementDisplayable;
import de.rincewind.api.gui.elements.util.Icon;
import de.rincewind.api.gui.elements.util.Point;

public abstract class CraftElementDisplayable extends CraftElement implements ElementDisplayable {
	
	private ItemStack icon;
	private ItemStack disabledIcon;
	
	public CraftElementDisplayable(Modifyable handle) {
		super(handle);
		
		this.icon = Modifyable.INVISIBLE_ELEMENT;
		this.disabledIcon = this.getIcon().toItem();
	}
	
	@Override
	public Icon getIcon() {
		return new Icon(this.icon);
	}

	@Override
	public Icon getDisabledIcon() {
		return new Icon(this.disabledIcon);
	}
	
	@Override
	public void setIcon(Icon icon) {
		Validate.notNull(icon, "The icon cannot be null!");
		
		this.icon = icon.toItem();
		this.getHandle().readItemsFrom(this);
	}
	
	@Override
	public void setDisabledIcon(Icon icon) {
		Validate.notNull(icon, "The icon cannot be null!");
		
		this.disabledIcon = icon.toItem();
		this.getHandle().readItemsFrom(this);
	}
	
	@Override
	public void updateItemMap() {
		this.updateItemMap(Point.atNull(), true);
	}
	
	@Override
	@Deprecated
	public void setIcon(ItemStack item) {
		Validate.notNull(item, "The item cannot be null!");
		
		this.icon = item;
		this.getHandle().readItemsFrom(this);
	}
	
	@Override
	@Deprecated
	public void setDisabledIcon(ItemStack item) {
		Validate.notNull(item, "The item cannot be null!");
		
		this.disabledIcon = item;
		this.getHandle().readItemsFrom(this);
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
