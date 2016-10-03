package de.rincewind.api.gui.windows;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.inventory.Inventory;

import de.rincewind.api.gui.elements.util.Icon;
import de.rincewind.api.handling.events.WindowMaximizeEvent;
import de.rincewind.api.handling.events.WindowMinimizeEvent;
import de.rincewind.api.handling.listener.WindowMaximizeListener;
import de.rincewind.api.handling.listener.WindowMinimizeListener;
import de.rincewind.plugin.gui.windows.abstracts.CraftWindow;
import net.minecraft.server.v1_10_R1.BlockPosition;
import net.minecraft.server.v1_10_R1.ChatMessage;
import net.minecraft.server.v1_10_R1.ContainerAnvil;
import net.minecraft.server.v1_10_R1.EntityHuman;
import net.minecraft.server.v1_10_R1.EntityPlayer;
import net.minecraft.server.v1_10_R1.PacketPlayOutOpenWindow;

public class WindowAnvil extends CraftWindow {
	
	private Icon display;
	
	private Inventory inventory;
	
	public WindowAnvil() {
		this.display = new Icon(Material.STONE).rename(">");

		this.getEventManager().registerListener(new WindowMaximizeListener() {

			@Override
			public void onFire(WindowMaximizeEvent event) {
				EntityPlayer nmsPlayer = ((CraftPlayer) event.getWindow().getUser()).getHandle();

				AnvilContainer container = new AnvilContainer(nmsPlayer);

				WindowAnvil.this.inventory = container.getBukkitView().getTopInventory();
				WindowAnvil.this.inventory.setItem(0, WindowAnvil.this.display.toItem());
				
				int containerId = nmsPlayer.nextContainerCounter();

				nmsPlayer.playerConnection.sendPacket(new PacketPlayOutOpenWindow(containerId, "minecraft:anvil", new ChatMessage("Repairing", new Object[0]), 0));
				nmsPlayer.activeContainer = container;
				nmsPlayer.activeContainer.windowId = containerId;
				nmsPlayer.activeContainer.addSlotListener(nmsPlayer);
			}

		}).addAfter();
		
		this.getEventManager().registerListener(new WindowMinimizeListener() {
			
			@Override
			public void onFire(WindowMinimizeEvent event) {
				WindowAnvil.this.inventory.clear();
			}
			
		}).addAfter();
	}
	
	public void setDisplay(Icon display) {
		this.display = display;
	}
	
	public Icon getDisplay() {
		return this.display;
	}
	
	public String getInsertedName() {
		if (this.inventory.getItem(2) != null && this.inventory.getItem(2).getType() != Material.AIR) {
			return this.inventory.getItem(2).getItemMeta().getDisplayName();
		} else {
			return this.display.getName();
		}
	}
	
	private static class AnvilContainer extends ContainerAnvil {

		public AnvilContainer(EntityHuman entity) {
			super(entity.inventory, entity.world, new BlockPosition(0, 0, 0), entity);
		}

		@Override
		public boolean a(EntityHuman entityhuman) {
			return true;
		}

	}

}
