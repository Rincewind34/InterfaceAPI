package de.rincewind.api.gui.windows;

import java.util.ArrayList;
import java.util.List;

import lib.securebit.ReflectionUtil;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.windows.abstracts.WindowEditor;
import de.rincewind.defaults.exceptions.InvalidSlotException;
import de.rincewind.plugin.APIReflection;
import de.rincewind.plugin.InterfacePlugin.InterfaceAPI;

public class WindowEnchanter extends WindowEditor {

	private int[] lvls;
	
	/**
	 * WindowEnchanter: Open an enchanter for a player (with enchant function or only for showing)
	 */
	public WindowEnchanter() {
		this.lvls = new int[] {0, 0, 0};
	}
	
	@Override
	public void maximize() {
		super.maximize();
		this.update();
	}
	
	
	@Override
	public int getPositionX(int slot) {
		return slot;
	}
	
	@Override
	public int getPositionY(int slot) {
		return 0;
	}
	
	@Override
	public void updateBukkitInventory() {
		super.updateBukkitInventory();
		
		List<Integer> usedSlots = new ArrayList<Integer>();
		
		for (int i = 0; i < 3; i++) {
			if (!super.hasSpaceAt(this.getPositionX(i), this.getPositionY(i))) {
				ItemStack item = super.getItemAt(this.getPositionX(i), this.getPositionY(i));
				
				if (item.equals(Modifyable.EMPTY_USED_SLOT)) {
					usedSlots.add(i);
				} else {
					usedSlots.add(i);
					inv.setItem(i, item);
					if(super.getUser() != null) super.getUser().updateInventory();
				}
			}
		}
	}
	
	/**
	 * Sets an enchantment-offer to a given slot
	 * @param slot The slot (0 = at the top; 1 = middle, 2 = at the bottom)
	 * @param lvl The required level to use this offer
	 */
	public void setOffer(int slot, int lvl) {
		if (0 > slot || slot > 2) {
			throw new InvalidSlotException(slot, WindowEnchanter.class);
		} else {
			this.lvls[slot] = lvl;
		}
	}
	
	/**
	 * Updates the enchanter for the using client
	 */
	public void update() {
		for (int i = 0; i < 3; i++) {
			this.sendUpdatePacket(i, this.lvls[i]);
		}
	}
	
	/**
	 * @param slot The slot (0 = at the top; 1 = middle, 2 = at the bottom)
	 * @return The required level to use the offer at the given slot 
	 */
	public int getOffer(int slot) {
		if(0 > slot || slot > 2) {
			throw new InvalidSlotException(slot, WindowEnchanter.class);
		} else {
			return this.lvls[slot];
		}
	}
	
	@Override
	protected void createBukkitInventory() {
		super.inv = Bukkit.createInventory(null, InventoryType.ENCHANTING, getName());
	}

	/**
	 * Updates the enchanter for the using client
	 */
	protected void sendUpdatePacket(int slot, int lvl) {
		if (super.getUser() == null) {
			throw new NullPointerException("The user of this window cannot be null");
		}
		
		Object packet = ReflectionUtil.createObject(APIReflection.CONSTRUCTOR_PACKET_WINDOWDATA, 
				new Object[] { InterfaceAPI.getActiveWindowId(super.getUser()), slot, lvl });
		APIReflection.sendPacket(super.getUser(), packet);
	}
	
}
