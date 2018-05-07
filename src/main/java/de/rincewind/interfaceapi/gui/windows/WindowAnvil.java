package de.rincewind.interfaceapi.gui.windows;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.windows.util.WindowState;
import de.rincewind.interfaceapi.handling.window.WindowChangeStateEvent;
import de.rincewind.interfaceplugin.gui.windows.abstracts.CraftWindow;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.ChatMessage;
import net.minecraft.server.v1_12_R1.ContainerAnvil;
import net.minecraft.server.v1_12_R1.EntityHuman;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.PacketPlayOutOpenWindow;

public class WindowAnvil extends CraftWindow {

	private Icon display;

	private Inventory inventory;

	public WindowAnvil(Plugin plugin) {
		super(plugin);
		
		this.display = new Icon(Material.STONE).rename(">");

		this.getEventManager().registerListener(WindowChangeStateEvent.class, (event) -> {
			if (event.getNewState() == WindowState.MAXIMIZED) {
				EntityPlayer nmsPlayer = ((CraftPlayer) event.getWindow().getUser()).getHandle();
	
				AnvilContainer container = new AnvilContainer(nmsPlayer);
	
				WindowAnvil.this.inventory = container.getBukkitView().getTopInventory();
				WindowAnvil.this.inventory.setItem(0, WindowAnvil.this.display.toItem());
	
				int containerId = nmsPlayer.nextContainerCounter();
	
				nmsPlayer.playerConnection.sendPacket(new PacketPlayOutOpenWindow(containerId, "minecraft:anvil", new ChatMessage("Repairing", new Object[0]), 0));
				nmsPlayer.activeContainer = container;
				nmsPlayer.activeContainer.windowId = containerId;
				nmsPlayer.activeContainer.addSlotListener(nmsPlayer);
			} else if (event.getNewState() == WindowState.MINIMIZED) {
				WindowAnvil.this.inventory.clear();
			}
		}).addBefore();
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
			
			super.checkReachable = false;
		}

	}

}
