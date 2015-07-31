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

public class WindowBrewing extends WindowActivatable {

	private BukkitTask task;
	
	/**
	 * WindowFurnace: Open a furnace and animate the flames 
	 * The slots are defined:
	 * <ul>
	 * 	<li>Slot 0: 0|1 - (<i>standard: rigth input for potions</i>)</li>
	 * 	<li>Slot 1: 1|1 - (<i>standard: middle input for potions</i>)</li>
	 * 	<li>Slot 2: 2|1 - (<i>standard: left input for potions</i>)</li>
	 * 	<li>Slot 3: 1|0 - (<i>standard: input for the recipe</i>)</li>
	 * </ul>
	 */
	public WindowBrewing(Plugin plugin) {
		super(plugin);
		super.setProgress(6);
	}
	
	
	@Override
	public int getPositionX(int slot) {
		if (slot == 0) {
			return 0;
		} else if (slot == 1) {
			return 1;
		} else if (slot == 2) {
			return 2;
		} else if (slot == 3) {
			return 1;
		} else {
			throw new InvalidSlotException(slot, WindowBrewing.class);
		}
	}
	
	@Override
	public int getPositionY(int slot) {
		if (slot == 0) {
			return 1;
		} else if(slot == 1) {
			return 1;
		} else if(slot == 2) {
			return 1;
		} else if(slot == 3) {
			return 0;
		} else {
			throw new InvalidSlotException(slot, WindowBrewing.class);
		}
	}
	
	@Override
	public void start(long delay) {
		if (this.isRunning()) {
			return;
		} else {
			super.setRunning(true);
			
			this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(super.getPlugin(), new Runnable() {
				
				@Override
				public void run() {
					if (!WindowBrewing.super.isRunning()) {
						return;
					} else {
						WindowBrewing.this.sendUpdatePacket(WindowBrewing.super.getProgress());
						WindowBrewing.super.setProgress(WindowBrewing.super.getProgress() - 1);
						
						if (0 > WindowBrewing.super.getProgress() || WindowBrewing.super.getProgress() > 6) {
							WindowBrewing.super.setProgress(6);
						}
					}
				}
				
			}, 0L, delay);
		}
	}
	
	@Override
	public void updateBukkitInventory() {
		super.updateBukkitInventory();
		
		List<Integer> usedSlots = new ArrayList<Integer>();
		
		for (int i = 0; i < 4; i++) {
			if (!super.hasSpaceAt(this.getPositionX(i), this.getPositionY(i))) {
				ItemStack item = super.getItemAt(this.getPositionX(i), this.getPositionY(i));
				
				if (item.equals(Modifyable.EMPTY_USED_SLOT)){
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
		super.inv = Bukkit.createInventory(null, InventoryType.BREWING);
	}
	
	/**
	 * Updates the brewer for the currently using player
	 * @param progress A number between 0 and 5; the progress of the bubbles (0 = bubbles are full; 5 = bubbles not visible)
	 */
	protected void sendUpdatePacket(int progress) {
		if (super.getUser() == null) {
			return;
		}
		
		Object packet = ReflectionUtil.createObject(APIReflection.CONSTRUCTOR_PACKET_WINDOWDATA, 
				new Object[] { InterfaceAPI.getActiveWindowId(super.getUser()), 0, progress * 2 + 400 });
		APIReflection.sendPacket(super.getUser(), packet);
	}

}
