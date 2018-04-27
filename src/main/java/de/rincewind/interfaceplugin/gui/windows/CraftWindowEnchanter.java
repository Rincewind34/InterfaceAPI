package de.rincewind.interfaceplugin.gui.windows;

import java.util.Collections;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import com.google.common.collect.Sets;

import de.rincewind.interfaceapi.InterfaceAPI;
import de.rincewind.interfaceapi.exceptions.IllegalCoordinatesException;
import de.rincewind.interfaceapi.exceptions.InvalidSlotException;
import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceapi.gui.windows.WindowEnchanter;
import de.rincewind.interfaceapi.gui.windows.util.WindowState;
import de.rincewind.interfaceapi.handling.window.WindowChangeStateEvent;
import de.rincewind.interfaceplugin.gui.windows.abstracts.CraftWindowEditor;
import net.minecraft.server.v1_12_R1.PacketPlayOutWindowData;

public class CraftWindowEnchanter extends CraftWindowEditor implements WindowEnchanter {

	private static final Set<Point> final_points = Collections.unmodifiableSet(Sets.newHashSet(new Point(0, 0), new Point(1, 0)));

	private int[] lvls;

	public CraftWindowEnchanter() {
		this.lvls = new int[] { 0, 0, 0 };

		this.getEventManager().registerListener(WindowChangeStateEvent.class, (event) -> {
			if (event.getNewState() == WindowState.MAXIMIZED) {
				this.renderFrame();
			}
		}).addAfter();

		this.createBukkitInventory();
	}

	@Override
	public void setOffer(int slot, int lvl) {
		if (0 > slot || slot > 2) {
			throw new InvalidSlotException(slot, WindowEnchanter.class);
		} else {
			this.lvls[slot] = lvl;
		}
	}

	@Override
	public void updateLevels() {
		for (int i = 0; i < 3; i++) {
			this.sendUpdatePacket(i, this.lvls[i]);
		}
	}

	@Override
	public int getOffer(int slot) {
		if (0 > slot || slot > 2) {
			throw new InvalidSlotException(slot, WindowEnchanter.class);
		} else {
			return this.lvls[slot];
		}
	}

	@Override
	public Set<Point> getPoints() {
		return CraftWindowEnchanter.final_points;
	}

	public void sendUpdatePacket(int slot, int lvl) {
		if (this.getUser() == null) {
			return;
		}

		((CraftPlayer) this.getUser()).getHandle().playerConnection
				.sendPacket(new PacketPlayOutWindowData(InterfaceAPI.getActiveWindowId(this.getUser()), slot, lvl));
	}

	@Override
	public int getSlot(Point point) {
		assert this.isInside(point) : "The point " + point + " is undifined for window enchanter";

		return point.getX();
	}

	@Override
	public Point getPoint(int bukkitSlot) {
		assert bukkitSlot == 0 || bukkitSlot == 1 : "The bukkit slot " + bukkitSlot + " is undifined for window enchanter";

		if (bukkitSlot != 0 && bukkitSlot != 1) {
			throw new IllegalCoordinatesException("Invalid slot");
		}

		return new Point(bukkitSlot, 0);
	}

	@Override
	public Inventory newInventory() {
		return Bukkit.createInventory(null, InventoryType.ENCHANTING, this.getName());
	}

}
