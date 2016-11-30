package de.rincewind.plugin.gui.windows;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import de.rincewind.api.InterfaceAPI;
import de.rincewind.api.exceptions.InvalidSlotException;
import de.rincewind.api.gui.elements.util.Point;
import de.rincewind.api.gui.windows.WindowEnchanter;
import de.rincewind.api.gui.windows.util.WindowState;
import de.rincewind.api.handling.window.WindowChangeStateEvent;
import de.rincewind.plugin.APIReflection;
import de.rincewind.plugin.gui.windows.abstracts.CraftWindowEditor;
import lib.securebit.ReflectionUtil;

public class CraftWindowEnchanter extends CraftWindowEditor implements WindowEnchanter {

	private int[] lvls;

	public CraftWindowEnchanter() {
		this.lvls = new int[] { 0, 0, 0 };

		this.getEventManager().registerListener(WindowChangeStateEvent.class, (event) -> {
			if (event.getNewState() == WindowState.MAXIMIZED) {
				this.update();
			}
		}).addAfter();
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
	public List<Point> getPoints() {
		return Arrays.asList(new Point(0, 0), new Point(1, 0));
	}
	
	public void sendUpdatePacket(int slot, int lvl) {
		if (this.getUser() == null) {
			return;
		}

		Object packet = ReflectionUtil.createObject(APIReflection.CONSTRUCTOR_PACKET_WINDOWDATA,
				new Object[] { InterfaceAPI.getActiveWindowId(super.getUser()), slot, lvl });
		APIReflection.sendPacket(super.getUser(), packet);
	}

	@Override
	public int getSlot(Point point) {
		return point.getX();
	}

	@Override
	public Point getPoint(int bukkitSlot) {
		return new Point(bukkitSlot, 0);
	}

	@Override
	public Inventory newInventory() {
		return Bukkit.createInventory(null, InventoryType.ENCHANTING, this.getName());
	}

}
