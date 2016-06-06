package de.rincewind.plugin.gui.windows;

import java.util.function.Consumer;

import lib.securebit.ReflectionUtil;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import de.rincewind.api.InterfaceAPI;
import de.rincewind.api.exceptions.InvalidSlotException;
import de.rincewind.api.gui.elements.util.Point;
import de.rincewind.api.gui.windows.WindowFurnace;
import de.rincewind.plugin.APIReflection;
import de.rincewind.plugin.gui.windows.abstracts.CraftWindowActivatable;

public class CraftWindowFurnace extends CraftWindowActivatable implements WindowFurnace {

	public CraftWindowFurnace(Plugin plugin) {
		super(plugin);
	}
	
	@Override
	public Point getPoint(int bukkitSlot) {
		if (bukkitSlot == 0) {
			return new Point(0, 0);
		} else if (bukkitSlot == 1) {
			return new Point(0, 2);
		} else if (bukkitSlot == 2) {
			return new Point(1, 1);
		} else {
			throw new InvalidSlotException(bukkitSlot, WindowFurnace.class);
		}
	}
	
	@Override
	public int getSlot(Point point) {
		if (point.getX() == 0) {
			if (point.getY() == 0) {
				return 0;
			} else if (point.getY() == 2) {
				return 1;
			}
		} else if (point.getX() == 1) {
			if (point.getY() == 1) {
				return 2;
			}
		}
		
		return -1;
	}
	
	@Override
	public void iterate(Consumer<Point> action) {
		action.accept(new Point(0, 0));
		action.accept(new Point(0, 2));
		action.accept(new Point(1, 1));
	}
	
	@Override
	public Runnable getRunnable() {
		return () -> {
			if (!this.isRunning()) {
				return;
			} else {
				this.sendUpdatePacket(this.getProgress());
				this.setProgress(this.getProgress() + 1);
				
				if (0 > this.getProgress() || this.getProgress() > 13) {
					this.setProgress(0);
				}
			}
		};
	}
	
	@Override
	public Inventory newInventory() {
		return Bukkit.createInventory(null, InventoryType.FURNACE, this.getName());
	}
	
	public void sendUpdatePacket(int progress) {
		if (super.getUser() == null) {
			return;
		}
		
		Object packet = ReflectionUtil.createObject(APIReflection.CONSTRUCTOR_PACKET_WINDOWDATA, 
				new Object[] { InterfaceAPI.getActiveWindowId(super.getUser()), 0, progress * 15 });
		APIReflection.sendPacket(super.getUser(), packet);
	}

}
