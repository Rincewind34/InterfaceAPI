package de.rincewind.interfaceplugin.gui.windows;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import de.rincewind.interfaceapi.InterfaceAPI;
import de.rincewind.interfaceapi.exceptions.APIException;
import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceapi.gui.windows.WindowBrewing;
import de.rincewind.interfaceplugin.gui.windows.abstracts.CraftWindowActivatable;
import net.minecraft.server.v1_12_R1.PacketPlayOutWindowData;

public class CraftWindowBrewing extends CraftWindowActivatable implements WindowBrewing {

	public CraftWindowBrewing(Plugin plugin) {
		super(plugin);

		this.setProgress(6);
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

	@Override
	public List<Point> getPoints() {
		return Arrays.asList(new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 0));
	}

	@Override
	public int getSlot(Point point) {
		if (point.getY() == 0 && point.getX() == 1) {
			return 3;
		} else if (point.getY() == 1 && 0 <= point.getX() && point.getX() <= 2) {
			return point.getX();
		}

		throw new APIException("Invalid point!");
	}

	@Override
	public Point getPoint(int bukkitSlot) {
		if (0 <= bukkitSlot && bukkitSlot <= 2) {
			return new Point(bukkitSlot, 1);
		} else if (bukkitSlot == 3) {
			return new Point(1, 0);
		} else {
			throw new APIException("Invalid slot!");
		}
	}

	@Override
	public Inventory newInventory() {
		return Bukkit.createInventory(null, InventoryType.BREWING, this.getName());
	}

}
