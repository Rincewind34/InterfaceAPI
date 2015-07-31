package de.rincewind.api.gui.windows;

import java.util.ArrayList;
import java.util.List;

import lib.securebit.ReflectionUtil;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.windows.abstracts.WindowActivatable;
import de.rincewind.defaults.exceptions.InvalidSlotException;
import de.rincewind.plugin.APIReflection;
import de.rincewind.plugin.InterfacePlugin.InterfaceAPI;

public class WindowFurnace extends WindowActivatable {
	
	private BukkitTask task;
	
	/**
	 * WindowFurnace: Open a furnace and animate the flames 
	 * The slots are defined:
	 * <ul>
	 * 	<li>Slot 0: 0|0 - (<i>standard: input for the material to cook / burn up</i>)</li>
	 * 	<li>Slot 1: 0|2 - (<i>standard: input for the fuel</i>)</li>
	 * 	<li>Slot 2: 1|1 - (<i>standard: output at the mc-furnace</i>)</li>
	 * </ul>
	 */
	public WindowFurnace(Plugin plugin) {
		super(plugin);
	}
	
	@Override
	public int getPositionX(int slot) {
		if (slot == 0) {
			return 0;
		} else if (slot == 1) {
			return 0;
		} else if (slot == 2) {
			return 1;
		} else {
			throw new InvalidSlotException(slot, WindowFurnace.class);
		}
	}
	
	@Override
	public int getPositionY(int slot) {
		if (slot == 0) {
			return 0;
		} else if (slot == 1) {
			return 2;
		} else if (slot == 2) {
			return 1;
		} else {
			throw new InvalidSlotException(slot, WindowFurnace.class);
		}
	}
	
	@Override
	public void start(long delay) {
		if (!this.isRunning()) {
			super.setRunning(true);
			sendUpdatePacket(6);
			
			this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(super.getPlugin(), new Runnable() {
				
				@Override
				public void run() {
					if(!WindowFurnace.super.isRunning()) return;
					else {
						WindowFurnace.this.sendUpdatePacket(WindowFurnace.super.getProgress());
						WindowFurnace.super.setProgress(WindowFurnace.super.getProgress() + 1);
						
						if(0 > WindowFurnace.super.getProgress() || WindowFurnace.super.getProgress() > 13) WindowFurnace.super.setProgress(0);
					}
				}
				
			}, 0L, delay);
		}
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
					this.inv.setItem(i, item);
					
					if (super.getUser() != null) {
						super.getUser().updateInventory();
					}
				}
			}
		}
		
		super.createBackground(usedSlots);
	}

	@Override
	public void stop() {
		if (super.isRunning()) {
			super.setRunning(false);
			this.task.cancel();
		}
	}
	
	@Override
	protected void onClose() {
		super.onClose();
		this.stop();
	}
	
	@Override
	protected void createBukkitInventory() {
		super.inv = Bukkit.createInventory(null, InventoryType.FURNACE);
	}
	
	/**
	 * Updates the furnace for the currently using player
	 * @param progress A number between 0 and 12; the progress of the flames (0 = flames not visible; 12 = flames are full)
	 */
	protected void sendUpdatePacket(int progress) {
		if (super.getUser() == null) {
			return;
		}
		
		Object packet = ReflectionUtil.createObject(APIReflection.CONSTRUCTOR_PACKET_WINDOWDATA, 
				new Object[] { InterfaceAPI.getActiveWindowId(super.getUser()), 0, progress * 15 });
		APIReflection.sendPacket(super.getUser(), packet);
	}

}
