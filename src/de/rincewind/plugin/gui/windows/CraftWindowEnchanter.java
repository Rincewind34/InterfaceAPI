package de.rincewind.plugin.gui.windows;

import java.util.function.Consumer;

import lib.securebit.ReflectionUtil;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import de.rincewind.api.gui.components.Locatable.Point;
import de.rincewind.api.gui.windows.WindowEnchanter;
import de.rincewind.defaults.exceptions.InvalidSlotException;
import de.rincewind.plugin.APIReflection;
import de.rincewind.plugin.InterfacePlugin.InterfaceAPI;
import de.rincewind.plugin.gui.windows.abstracts.CraftWindowEditor;

public class CraftWindowEnchanter extends CraftWindowEditor implements WindowEnchanter {
	
	private int[] lvls;
	
	public CraftWindowEnchanter() {
		this.lvls = new int[] {0, 0, 0};
	}
	
	@Override
	public void maximize() {
		super.maximize();
		this.update();
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
	public void setOffer(int slot, int lvl) {
		if (0 > slot || slot > 2) {
			throw new InvalidSlotException(slot, WindowEnchanter.class);
		} else {
			this.lvls[slot] = lvl;
		}
	}

	@Override
	public void update() {
		for (int i = 0; i < 3; i++) {
			this.sendUpdatePacket(i, this.lvls[i]);
		}
	}
	

	@Override
	public int getOffer(int slot) {
		if(0 > slot || slot > 2) {
			throw new InvalidSlotException(slot, WindowEnchanter.class);
		} else {
			return this.lvls[slot];
		}
	}
	
	@Override
	public Inventory newInventory() {
		return Bukkit.createInventory(null, InventoryType.ENCHANTING, this.getName());
	}
	
	public void sendUpdatePacket(int slot, int lvl) {
		if (super.getUser() == null) {
			throw new NullPointerException("The user of this window cannot be null");
		}
		
		Object packet = ReflectionUtil.createObject(APIReflection.CONSTRUCTOR_PACKET_WINDOWDATA, 
				new Object[] { InterfaceAPI.getActiveWindowId(super.getUser()), slot, lvl });
		APIReflection.sendPacket(super.getUser(), packet);
	}

	@Override
	public void iterate(Consumer<Point> action) {
		action.accept(new Point(0, 0));
		action.accept(new Point(1, 0));
	}
	
}
