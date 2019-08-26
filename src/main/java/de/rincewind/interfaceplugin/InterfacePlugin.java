package de.rincewind.interfaceplugin;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.boss.BarColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_13_R2.potion.CraftPotionEffectType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import de.rincewind.interfaceapi.InterfaceAPI;
import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.util.HeadsDatabase;
import de.rincewind.interfaceapi.util.InterfaceUtils;
import de.rincewind.interfaceplugin.listener.InventoryClickListener;
import de.rincewind.interfaceplugin.listener.InventoryCloseListener;
import de.rincewind.interfaceplugin.listener.InventoryDragListener;
import de.rincewind.interfaceplugin.listener.PlayerQuitListener;

public class InterfacePlugin extends JavaPlugin {
	
	public static boolean debugOutput = true;

	public static InterfacePlugin instance;
	
	public static void registerConverter() {
		Displayable.put(BarColor.class, InterfaceUtils::convertBarColor);
		Displayable.put(GameMode.class, InterfaceUtils::convertGameMode);
		Displayable.put(Environment.class, InterfaceUtils::convertEnvironment);
		Displayable.put(World.class, InterfaceUtils::convertWorld);
		Displayable.put(EntityType.class, InterfaceUtils::convertEntityType);
		Displayable.put(PotionEffectType.class, InterfaceUtils::convertPotionEffectType);
		Displayable.put(CraftPotionEffectType.class, InterfaceUtils::convertPotionEffectType);
		Displayable.put(PotionType.class, InterfaceUtils::convertPotionType);
		Displayable.put(EquipmentSlot.class, InterfaceUtils::convertEquipmentSlot);
		Displayable.put(Material.class, (material) -> {
			return new Icon(material, "§7" + material.name());
		});
		Displayable.put(Boolean.class, (input) -> {
			return input ? new Icon(Material.GREEN_CONCRETE, "§aTrue") : new Icon(Material.RED_CONCRETE, "§cFalse");
		});

		Displayable.copy(Boolean.class, boolean.class);
	}

	@Override
	public void onLoad() {
		InterfacePlugin.registerConverter();
		InterfaceAPI.enable();
	}

	@Override
	public void onEnable() {
		InterfacePlugin.instance = this;

		this.getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
		this.getServer().getPluginManager().registerEvents(new InventoryDragListener(), this);
		this.getServer().getPluginManager().registerEvents(new InventoryCloseListener(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
	}

	@Override
	public void onDisable() {
		InterfaceAPI.disable();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equals("interfacetest")) {
			((Player) sender).getInventory()
					.addItem(InterfaceUtils.buildHead("ccd469f7-1df1-42f9-8915-15de387906e4",
							"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWI3Y2U2ODNkMDg2OGFhNDM3OGFlYjYwY2"
									+ "FhNWVhODA1OTZiY2ZmZGFiNmI1YWYyZDEyNTk1ODM3YTg0ODUzIn19fQ=="));

			((Player) sender).getInventory().addItem(HeadsDatabase.arrowStoneDown().toItem());
		}

		return true;
	}

}