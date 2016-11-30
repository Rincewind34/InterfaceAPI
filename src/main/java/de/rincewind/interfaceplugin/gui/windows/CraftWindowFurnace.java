package de.rincewind.interfaceplugin.gui.windows;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import de.rincewind.interfaceapi.InterfaceAPI;
import de.rincewind.interfaceapi.exceptions.InvalidSlotException;
import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceapi.gui.windows.WindowFurnace;
import de.rincewind.interfaceplugin.APIReflection;
import de.rincewind.interfaceplugin.ReflectionUtil;
import de.rincewind.interfaceplugin.gui.windows.abstracts.CraftWindowActivatable;

public class CraftWindowFurnace extends CraftWindowActivatable implements WindowFurnace {

	public CraftWindowFurnace(Plugin plugin) {
		super(plugin);
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
	public List<Point> getPoints() {
		return Arrays.asList(new Point(0, 0), new Point(0, 2), new Point(1, 1));
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
