package de.rincewind.plugin.gui.elements.abstracts;

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
		if (this.icon == null) {
			return null;
		} else {
			return new Icon(this.icon);
		}
	}

	@Override
	public Icon getDisabledIcon() {
		if (this.disabledIcon == null) {
			return null;
		} else {
			return new Icon(this.disabledIcon);
		}
	}
	
	@Override
	public void setIcon(Icon icon) {
		if (icon != null) {
			this.icon = icon.toItem();
		} else {
			this.icon = null;
		}
		
		this.getHandle().readItemsFrom(this);
	}
	
	@Override
	public void setDisabledIcon(Icon icon) {
		if (icon != null) {
			this.disabledIcon = icon.toItem();
		} else {
			this.icon = null;
		}
		
		this.getHandle().readItemsFrom(this);
	}
	
	@Override
	public void updateItemMap() {
		this.updateItemMap(Point.atNull(), true);
	}
	
	protected final void updateItemMap(Point point, boolean withIcon) {
		if (!this.isVisible()) {
			this.setItemAt(point, Modifyable.INVISIBLE_ELEMENT);
			return;
		}
		
		if (!this.isEnabled()) {
			if (this.disabledIcon == null) {
				this.setItemAt(point, Modifyable.EMPTY_USED_SLOT);
			} else {
				this.setItemAt(point, this.disabledIcon);
			}
		} else if (withIcon) {
			if (this.icon == null) {
				this.setItemAt(point, Modifyable.EMPTY_USED_SLOT);
			} else {
				this.setItemAt(point, this.icon);
			}
		}
	}

}
