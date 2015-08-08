package de.rincewind.plugin.gui.elements.abstracts;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.ClickBlocker;
import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.abstracts.Element;

public abstract class CraftElement implements Element {
	
	private int id;
	private Point point;
	
	private Modifyable handle;
	
	private boolean visible;
	private boolean enable;
	
	protected ItemStack[][] items;
	
	protected ClickBlocker blocker;
	
	public CraftElement(Modifyable handle) {
		this.handle = handle;
		
		this.id = -1;
		this.point = new Point(0, 0);
		
		this.visible = true;
		this.enable = true;
		
		this.items = new ItemStack[][] {{ null }};
		
		this.blocker = new ClickBlocker();
		this.blocker.lock();
	}
	
	@Override
	public Point getPoint() {
		return this.point;
	}
	
	@Override
	public int getWidth() {
		return 1;
	}
	
	@Override
	public int getHeight() {
		return 1;
	}
	
	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public boolean isEnabled() {
		return this.enable;
	}
	
	@Override
	public ClickBlocker getBlocker() {
		return this.blocker;
	}
	
	@Override
	public void enable() {
		this.setEnabled(true);
	}

	@Override
	public void disable() {
		this.setEnabled(false);
	}
	
	@Override
	public void setEnabled(boolean enable) {
		this.enable = enable;
		this.handle.updateItemMap(this);
	}
	
	@Override
	public void setPoint(Point point) {
		this.point = point;
		this.handle.updateItemMap(this);
	}

	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
		this.handle.updateItemMap(this);
	}
	
	@Override
	public void setBlocker(ClickBlocker blocker) {
		this.blocker = blocker;
	}
	
	public void setItemAt(Point point, ItemStack item) {
		if(this.hasItemAt(point)) {
			this.items[point.getX()][point.getY()] = item;
		}
 	}
	
	public void updateItemMap() {
		this.setItemAt(new Point(0, 0), Modifyable.EMPTY_USED_SLOT);
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public boolean hasItemAt(Point point) {
		if(0 <= point.getX() && point.getX() < this.items.length) {
			if(0 <= point.getY() && point.getY() < this.items[point.getX()].length) {
				return true;
			}
		}
		
		return false;
	}
	
	public ItemStack getItemAt(Point point) {
		if (this.hasItemAt(point)) {
			return this.items[point.getX()][point.getY()];
		} else {
			return null;
		}
	}
	
	public Modifyable getHandle() {
		return this.handle;
	}
	
	public abstract Runnable getRunnable(InventoryClickEvent event);
	
	public abstract void onAdd();
	
	@Override
	@Deprecated
	public int getX() {
		return this.point.getX();
	}

	@Override
	@Deprecated
	public int getY() {
		return this.point.getY();
	}
	
	@Override
	@Deprecated
	public void setPosition(int x, int y) {
		this.setPoint(new Point(x, y));
	}
	
}
