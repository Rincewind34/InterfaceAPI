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
import de.rincewind.interfaceapi.gui.windows.WindowBrewing;
import de.rincewind.interfaceplugin.gui.windows.abstracts.CraftWindowActivatable;
import net.minecraft.server.v1_13_R1.PacketPlayOutWindowData;

public class CraftWindowBrewing extends CraftWindowActivatable implements WindowBrewing {

	public static final Set<Point> final_points = Collections
			.unmodifiableSet(Sets.newHashSet(Point.of(0, 1), Point.of(1, 1), Point.of(2, 1), Point.of(1, 0)));

	public CraftWindowBrewing(Plugin plugin) {
		super(plugin);

		this.setProgress(6);
	}

	@Override
	public int getSlot(Point point) {
		if (point.getY() == 0 && point.getX() == 1) {
			return 3;
		} else if (point.getY() == 1 && 0 <= point.getX() && point.getX() <= 2) {
			return point.getX();
		}
		
		assert false : "The point " + point + " is undifined for window brewing";
		return 0;
	}

	@Override
	public Point getPoint(int bukkitSlot) {
		if (0 <= bukkitSlot && bukkitSlot <= 2) {
			return Point.of(bukkitSlot, 1);
		} else if (bukkitSlot == 3) {
			return Point.of(1, 0);
		} else {
			assert false : "The bukkit slot " + bukkitSlot + " is undifined for window brewing";
			return null;
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
				this.checkProgress();
				this.sendUpdatePacket(this.getProgress());
				this.setProgress(this.getProgress() - 1);

				if (this.getProgress() < 0) {
					this.setProgress(6);
				}
			}
		};
	}

	@Override
	public Set<Point> getPoints() {
		return CraftWindowBrewing.final_points;
	}

	public void sendUpdatePacket(int progress) {
		if (this.getUser() == null) {
			return;
		}

		((CraftPlayer) this.getUser()).getHandle().playerConnection
				.sendPacket(new PacketPlayOutWindowData(InterfaceAPI.getActiveWindowId(this.getUser()), 0, progress * 2 + 400));
	}

	private void checkProgress() {
		if (0 > this.getProgress()) {
			this.setProgress(0);
		} else if (6 < this.getProgress()) {
			this.setProgress(6);
		}
	}
	
}
