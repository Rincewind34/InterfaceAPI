package de.rincewind.plugin.gui.windows;

import java.util.function.Consumer;

import lib.securebit.ReflectionUtil;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import de.rincewind.api.gui.components.Locatable.Point;
import de.rincewind.api.gui.windows.WindowBrewing;
import de.rincewind.defaults.exceptions.InvalidSlotException;
import de.rincewind.plugin.APIReflection;
import de.rincewind.plugin.InterfacePlugin.InterfaceAPI;
import de.rincewind.plugin.gui.windows.abstracts.CraftWindowActivatable;

public class CraftWindowBrewing extends CraftWindowActivatable implements WindowBrewing {
	
	public CraftWindowBrewing(Plugin plugin) {
		super(plugin);
		super.setProgress(6);
	}
	
	@Override
	public int getSlot(Point point) {
		if (point.getY() == 0) {
			if (point.getX() == 1) {
				return 3;
			}
		} else if (point.getY() == 1) {
			if (0 <= point.getX() && point.getX() <= 2) {
				return point.getX();
			}
		}
		
		return -1;
	}
	
	@Override
	public Point getPoint(int bukkitSlot) {
		if (0 <= bukkitSlot && bukkitSlot <= 2) {
			return new Point(bukkitSlot, 1);
		} else if (bukkitSlot == 3) {
			return new Point(1, 0);
		} else {
			throw new InvalidSlotException(bukkitSlot, WindowBrewing.class);
		}
	}
	
	@Override
	public void iterate(Consumer<Point> action) {
		for (int slot = 0; slot < 4; slot++) {
			action.accept(this.getPoint(slot));
		}
	}
	
	@Override
	public Inventory newInventory() {
		return Bukkit.createInventory(null, InventoryType.BREWING, this.getName());
	}
	
	@Override
	public Runnable getRunnable() {
		return () -> {
			if (!this.isRunning()) {
				return;
			} else {
				this.sendUpdatePacket(this.getProgress());
				super.setProgress(this.getProgress() - 1);
				
				if (0 > super.getProgress() || this.getProgress() > 6) {
					super.setProgress(6);
				}
			}
		};
	}
	
	public void sendUpdatePacket(int progress) {
		if (super.getUser() == null) {
			return;
		}
		
		Object packet = ReflectionUtil.createObject(APIReflection.CONSTRUCTOR_PACKET_WINDOWDATA, 
				new Object[] { InterfaceAPI.getActiveWindowId(super.getUser()), 0, progress * 2 + 400 });
		APIReflection.sendPacket(super.getUser(), packet);
	}

}
