package de.rincewind.interfaceplugin.gui.windows;

import java.util.Collections;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_13_R1.entity.CraftPlayer;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import com.google.common.collect.Sets;

import de.rincewind.interfaceapi.InterfaceAPI;
import de.rincewind.interfaceapi.gui.util.Point;
import de.rincewind.interfaceapi.gui.windows.WindowFurnace;
import de.rincewind.interfaceplugin.gui.windows.abstracts.CraftWindowActivatable;
import net.minecraft.server.v1_13_R1.PacketPlayOutWindowData;

public class CraftWindowFurnace extends CraftWindowActivatable implements WindowFurnace {

	public static final Set<Point> final_points = Collections.unmodifiableSet(Sets.newHashSet(Point.of(0, 0), Point.of(0, 2), Point.of(1, 1)));

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
	public Set<Point> getPoints() {
		return CraftWindowFurnace.final_points;
	}

	@Override
	public Point getPoint(int bukkitSlot) {
		if (bukkitSlot == 0) {
			return Point.of(0, 0);
		} else if (bukkitSlot == 1) {
			return Point.of(0, 2);
		} else if (bukkitSlot == 2) {
			return Point.of(1, 1);
		} else {
			assert false : "The bukkit slot " + bukkitSlot + " is undefined for window furnace";
			return null;
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

		assert false : "The point " + point + " is undefined for window furnace";
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

		((CraftPlayer) this.getUser()).getHandle().playerConnection
				.sendPacket(new PacketPlayOutWindowData(InterfaceAPI.getActiveWindowId(this.getUser()), 0, progress * 15));

	}

}
