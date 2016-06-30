package de.rincewind.plugin.gui.elements.abstracts;

import java.util.function.Consumer;

import lib.securebit.Validate;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.abstracts.Element;
import de.rincewind.api.gui.elements.util.ClickBlocker;
import de.rincewind.api.gui.elements.util.ElementDefaults;
import de.rincewind.api.gui.elements.util.Point;
import de.rincewind.api.gui.util.EventManager;
import de.rincewind.plugin.gui.util.CraftClickBlocker;
import de.rincewind.plugin.gui.util.CraftEventManager;

public abstract class CraftElement implements Element {
	
	private int id;
	private Point point;
	
	private Modifyable handle;
	
	private boolean visible;
	private boolean enable;
	
	private ItemStack[][] items;
	
	private ClickBlocker blocker;
	
	private EventManager eventManager;
	
	public CraftElement(Modifyable handle) {
		this.handle = handle;
		
		this.id = -1;
		this.point = Point.atNull();
		
		this.visible = ElementDefaults.VISIBLE;
		this.enable = ElementDefaults.ENABLED;
		
		this.items = new ItemStack[][] {{ null }};
		
		this.eventManager = new CraftEventManager();
		
		this.blocker = new CraftClickBlocker();
		this.blocker.lock();
		
		this.getHandle().readItemsFrom(this);
	}
	
	@Override
	public void priorize() {
		this.handle.priorize(this);
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
		if (this.enable == enable) {
			return;
		}
		
		this.enable = enable;
		this.handle.readItemsFrom(this);
	}
	
	@Override
	public void setPoint(Point point) {
		Validate.notNull(point, "The point cannot be null!");
		
		if (this.point.equals(point)) {
			return;
		}
		
		this.handle.clearItemsFrom(this);
		this.point = point;
		this.handle.readItemsFrom(this);
	}

	@Override
	public void setVisible(boolean visible) {
		if (this.visible == visible) {
			return;
		}
		
		this.visible = visible;
		this.handle.readItemsFrom(this);
	}
	
	@Override
	public void setBlocker(ClickBlocker blocker) {
		Validate.notNull(blocker, "The blocker cannot be null!");
		
		this.blocker = blocker;
	}
	
	@Override
	public EventManager getEventManager() {
		return this.eventManager;
	}
	
	@Override
	public void iterate(Consumer<Point> action) {
		for (int x = 0; x < this.getWidth(); x++) {
			for (int y = 0; y < this.getHeight(); y++) {
				action.accept(new Point(x, y));
			}
		}
	}
	
	public void setItemAt(Point point, ItemStack item) {
		Validate.notNull(point, "The point cannot be null!");
		Validate.notNull(item, "The item cannot be null!");
		
		if (this.hasItemAt(point)) {
			this.items[point.getX()][point.getY()] = item;
		}
 	}
	
	public void createArray() {
		this.items = this.getNewArray();
	}
	
	public void updateItemMap() {
		this.setItemAt(new Point(0, 0), Modifyable.EMPTY_USED_SLOT);
	}
	
	public int getId() {
		return this.id;
	}
	
	public boolean hasItemAt(Point point) {
		Validate.notNull(point, "The point cannot be null!");
		
		if(0 <= point.getX() && point.getX() < this.items.length) {
			if(0 <= point.getY() && point.getY() < this.items[point.getX()].length) {
				return true;
			}
		}
		
		return false;
	}
	
	public ItemStack getItemAt(Point point) {
		Validate.notNull(point, "The point cannot be null!");
		
		if (this.hasItemAt(point)) {
			return this.items[point.getX()][point.getY()];
		} else {
			return null;
		}
	}
	
	public ItemStack[][] getNewArray() {
		return new ItemStack[][] {{ null }};
	}
	
	public Modifyable getHandle() {
		return this.handle;
	}
	
	public abstract void handleClick(InventoryClickEvent event);
	
	public void onCreate() {
		
	}
	
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
