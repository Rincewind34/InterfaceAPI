package de.rincewind.interfaceapi.util;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceplugin.Validate;
import net.minecraft.server.v1_12_R1.NBTTagCompound;

public class InterfaceUtils {

	public static Icon convertGameMode(GameMode gameMode) {
		Validate.notNull(gameMode, "The gamemode cannot be null");

		switch (gameMode) {
		case ADVENTURE:
			return new Icon(Material.IRON_SWORD, 0, "§7Abenteuer");
		case CREATIVE:
			return new Icon(Material.GRASS, 0, "§7Kreativ");
		case SPECTATOR:
			return new Icon(Material.EYE_OF_ENDER, 0, "§7Beobacher");
		case SURVIVAL:
			return new Icon(Material.IRON_PICKAXE, 0, "§7Überleben");
		default:
			throw new IllegalArgumentException(gameMode.name());
		}
	}

	public static Icon convertEnvironment(Environment environment) {
		Validate.notNull(environment, "The environment cannot be null");

		switch (environment) {
		case NETHER:
			return new Icon(InterfaceUtils.buildHead("0ef495a4-e5df-41c2-b9a2-b2e647cbb491",
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDgzNTc"
							+ "xZmY1ODlmMWE1OWJiMDJiODA4MDBmYzczNjExNmUyN2MzZGNmOWVmZWJlZGU4Y2YxZmRkZSJ9fX0=")).rename("§c");
		case NORMAL:
			return new Icon(InterfaceUtils.buildHead("bd287f02-7b3b-ffd9-c56c-99cb0fafab3b",
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOThkYWExZTNlZDk0ZmYzZTMzZT"
							+ "FkNGM2ZTQzZjAyNGM0N2Q3OGE1N2JhNGQzOGU3NWU3YzkyNjQxMDYifX19")).rename("§9");
		case THE_END:
			return new Icon(InterfaceUtils.buildHead("819b62d9-06cb-44fc-8e5b-e184b30bdae7",
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzZjYWM1OWIyYWFlND"
							+ "g5YWEwNjg3YjVkODAyYjI1NTVlYjE0YTQwYmQ2MmIyMWViMTE2ZmE1NjljZGI3NTYifX19")).rename("§e");
		default:
			throw new IllegalArgumentException(environment.name());
		}
	}

	public static Icon convertWorld(World world) {
		Validate.notNull(world, "The world cannot be null");

		Icon icon = InterfaceUtils.convertEnvironment(world.getEnvironment());
		icon.rename(icon.getName() + world.getName());
		return icon;
	}

	public static Icon convertEntityType(EntityType type) {
		Validate.notNull(type, "The entitytype cannot be null");

		switch (type) {
		case AREA_EFFECT_CLOUD:
			return new Icon(Material.LINGERING_POTION, 0, "§7Effektwolke");
		case ARMOR_STAND:
			return new Icon(Material.ARMOR_STAND, 0, "§7Rüstungsständer");
		case ARROW:
			return new Icon(Material.ARROW, 0, "§7Pfeil");
		case BAT:
			return new Icon(
					InterfaceUtils.buildHead("1d908835-9b22-c26d-f5ab-fb0aadec69c7", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3"
							+ "JhZnQubmV0L3RleHR1cmUvNjZjZmFiMWQyZjc2M2YzMzNi" + "NWUxYmRjZDFhZDE2MmM3ZjEyYTFhYzIyMjQ0NjNhMTQ0NjQzZWMyM2Y5OCJ9fX0="))
									.rename("§7Fledermaus");
		case BLAZE:
			return new Icon(
					InterfaceUtils.buildHead("7ceb88b2-7f5f-4399-abb9-7068251baa9d", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3"
							+ "JhZnQubmV0L3RleHR1cmUvYjc4ZWYyZTRjZjJjNDFhMmQxNGJ" + "mZGU5Y2FmZjEwMjE5ZjViMWJmNWIzNWE0OWViNTFjNjQ2Nzg4MmNiNWYwIn19fQ=="))
									.rename("§7Lohe");
		case BOAT:
			return null;
		case CAVE_SPIDER:
			return new Icon(InterfaceUtils.buildHead("39173a7a-c957-4ec1-ac1a-43e5a64983df", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5"
					+ "lY3JhZnQubmV0L3RleHR1cmUvNDE2NDVkZmQ3N2QwOTkyMzEwN2" + "IzNDk2ZTk0ZWViNWMzMDMyOWY5N2VmYzk2ZWQ3NmUyMjZlOTgyMjQifX19"))
							.rename("§7Hölenspinne");
		case CHICKEN:
			return new Icon(InterfaceUtils.buildHead("7d3a8ace-e045-4eba-ab71-71dbf525daf1",
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Im" + "h0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTYzODQ2OWE1OTljZWVmNzIwNzUz"
							+ "NzYwMzI0OGE5YWIxMWZmNTkxZmQzNzhiZWE0NzM1YjM0NmE3ZmFlODkzIn19fQ==")).rename("§7Huhn");
		case COMPLEX_PART:
			return new Icon(Material.BEDROCK, 0, "§7Komplexes Entityteil");
		case COW:
			return new Icon(InterfaceUtils.buildHead("97ddf3b3-9dbe-4a3b-8a0f-1b19ddeac0bd",
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6" + "Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWQ2YzZlZGE5NDJmN2Y1"
							+ "ZjcxYzMxNjFjNzMwNmY0YWVkMzA3ZDgyODk1ZjlkMmIwN2FiNDUyNTcxOGVkYzUifX19")).rename("§7Kuh");
		case CREEPER:
			return new Icon(Material.SKULL_ITEM, 4, "§7Creeper");
		case DRAGON_FIREBALL:
			return new Icon(Material.FIREBALL, 0, "§7Drachenfeuerball");
		case DROPPED_ITEM:
			return new Icon(Material.APPLE, 0, "§7Item");
		case EGG:
			return new Icon(Material.EGG, 0, "§7Hühnerei");
		case ENDERMAN:
			return new Icon(InterfaceUtils.buildHead("0de98464-1274-4dd6-bba8-370efa5d41a8", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5"
					+ "taW5lY3JhZnQubmV0L3RleHR1cmUvN2E1OWJiMGE3YTMyOTY1YjNkOTBkOGVhZmE4OTlkMTgz" + "NWY0MjQ1MDllYWRkNGU2YjcwOWFkYTUwYjljZiJ9fX0="))
							.rename("§7Enderman");
		case ENDERMITE:
			return new Icon(InterfaceUtils.buildHead("97ddf3b3-9dbe-4a3b-8a0f-1b19ddeac0bd", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW"
					+ "5lY3JhZnQubmV0L3RleHR1cmUvM2JlYWM1MDFlOTd" + "kYjFjYzAzNTI4N2QwNjhhOGViNTM4ZTU1ZWY4MDJmNWNjYTI1NjgzOTMzYTI0MzEzNmMifX19"))
							.rename("§7Endermite");
		case ENDER_CRYSTAL:
			return new Icon(Material.END_CRYSTAL, 0, "§7Endercrystal");
		case ENDER_DRAGON:
			return new Icon(Material.SKULL_ITEM, 5, "§7Enderdrach");
		case ENDER_PEARL:
			return new Icon(Material.ENDER_PEARL, 0, "§7Enderperle");
		case ENDER_SIGNAL:
			return new Icon(Material.EYE_OF_ENDER, 0, "§7Enderei");
		case EXPERIENCE_ORB:
			return new Icon(Material.EXP_BOTTLE, 0, "§7Erfahrung");
		case FALLING_BLOCK:
			return new Icon(Material.SAND, 0, "§7Fallender Block");
		case FIREBALL:
			return new Icon(Material.FIREBALL, 0, "§7Feuerball");
		case FIREWORK:
			return new Icon(Material.FIREWORK, 0, "§7Feuerwerk");
		case FISHING_HOOK:
			return new Icon(Material.FISHING_ROD, 0, "§7Fischköder");
		case GHAST:
			return new Icon(InterfaceUtils.buildHead("807f287f-6499-4e93-a887-0a298ab3091f", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY"
					+ "3JhZnQubmV0L3RleHR1cmUvOGI2YTcyMTM4ZDY5ZmJiZDJmZWEzZmE" + "yNTFjYWJkODcxNTJlNGYxYzk3ZTVmOTg2YmY2ODU1NzFkYjNjYzAifX19"))
							.rename("§7Ghast");
		case GIANT:
			return InterfaceUtils.convertEntityType(EntityType.ZOMBIE).rename("§7Riese");
		case GUARDIAN:
			return new Icon(
					InterfaceUtils.buildHead("628f15d0-e39c-4fd9-9c4e-8c41d4f54b29", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3J"
							+ "hZnQubmV0L3RleHR1cmUvYTBiZjM0YTcxZTc3MTViNmJhNTJ" + "kNWRkMWJhZTVjYjg1Zjc3M2RjOWIwZDQ1N2I0YmZjNWY5ZGQzY2M3Yzk0In19fQ=="))
									.rename("§7Guardian");
		case HORSE:
			return new Icon(
					InterfaceUtils.buildHead("89edbd9c-59d5-43a5-a816-9571cec3dd16", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3J"
							+ "hZnQubmV0L3RleHR1cmUvYmU3OGM0NzYyNjc0ZGRlOG" + "IxYTVhMWU4NzNiMzNmMjhlMTNlN2MxMDJiMTkzZjY4MzU0OWIzOGRjNzBlMCJ9fX0="))
									.rename("§7Pferd");
		case IRON_GOLEM:
			return new Icon(InterfaceUtils.buildHead("7cb6e9a5-994f-40d5-9bfc-4ba5d796d21e", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly9"
					+ "0ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODkwOTFkNzllYTBmNTllZjdlZjk0ZDdiYmE2ZTVmMTdmMmY3" + "ZDQ1NzJjNDRmOTBmNzZjNDgxOWE3MTQifX19"))
							.rename("§7Eisengolem");
		case ITEM_FRAME:
			return new Icon(Material.ITEM_FRAME, 0, "§7Itemframe");
		case LEASH_HITCH:
			return new Icon(Material.LEASH, 0, "§7Leine");
		case LIGHTNING:
			return new Icon(Material.ANVIL, 0, "§7Blitz");
		case LINGERING_POTION:
			return new Icon(Material.LINGERING_POTION, 0, "§7Werfbarer Drachentrank");
		case MAGMA_CUBE:
			return new Icon(InterfaceUtils.buildHead("96aced64-5b85-4b99-b825-53cd7a9f9726", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0d"
					+ "XJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzg5NTdkNTAyM2M5MzdjNGM0MW" + "FhMjQxMmQ0MzQxMGJkYTIzY2Y3OWE5ZjZhYjM2Yjc2ZmVmMmQ3YzQyOSJ9fX0="));
		case MINECART:
			return new Icon(Material.MINECART, 0, "§7Minecart");
		case MINECART_CHEST:
			return new Icon(Material.STORAGE_MINECART, 0, "§7Minecrat (Kiste)");
		case MINECART_COMMAND:
			return new Icon(Material.COMMAND_MINECART, 0, "§7Minecrat (Command)");
		case MINECART_FURNACE:
			return new Icon(Material.POWERED_MINECART, 0, "§7Minecrat (Ofen)");
		case MINECART_HOPPER:
			return new Icon(Material.HOPPER_MINECART, 0, "§7Minecrat (Kiste)");
		case MINECART_MOB_SPAWNER:
			return new Icon(Material.MOB_SPAWNER, 0, "§7Minecrat (Mobspawner)");
		case MINECART_TNT:
			return new Icon(Material.EXPLOSIVE_MINECART, 0, "§7Minecrat (TNT)");
		case MUSHROOM_COW:
			return new Icon(
					InterfaceUtils.buildHead("96aced64-5b85-4b99-b825-53cd7a9f9726", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3"
							+ "JhZnQubmV0L3RleHR1cmUvZDBiYzYxYjk3NTdhN2I4M2UwM2NkMjUwN" + "2EyMTU3OTEzYzJjZjAxNmU3YzA5NmE0ZDZjZjFmZTFiOGRiIn19fQ=="))
									.rename("§7Pilzkuh");
		case OCELOT:
			return new Icon(
					InterfaceUtils.buildHead("96aced64-5b85-4b99-b825-53cd7a9f9726", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI" + "6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3J"
							+ "hZnQubmV0L3RleHR1cmUvNTY1N2NkNWMyO" + "Tg5ZmY5NzU3MGZlYzRkZGNkYzY5MjZh" + "NjhhMzM5MzI1MGMxYmUxZjBiMTE0YTFkYjEifX19"))
									.rename("§7Ocelot");
		case PAINTING:
			return new Icon(Material.PAINTING, 0, "§7Gemälde");
		case PIG:
			return new Icon(InterfaceUtils.buildHead("e1e1c2e4-1ed2-473d-bde2-3ec718535399",
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh" + "0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjIxNjY4ZWY3Y2I3OWRkOWMyMmNlM2"
							+ "QxZjNmNGNiNmUyNTU5ODkzYjZkZjRhNDY5NTE0ZTY2N2MxNmFhNCJ9fX0=")).rename("§7Schwein");
		case PIG_ZOMBIE:
			return new Icon(InterfaceUtils.buildHead("6540c046-d6ea-4aff-9766-32a54ebe6958",
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0d" + "HA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzRlOWM2ZTk4NTgyZmZkOGZmOGZlYjMzMjJjZDE4ND"
							+ "ljNDNmYjE2YjE1OGFiYjExY2E3YjQyZWRhNzc0M2ViIn19fQ==")).rename("§7Zombieschwein");
		case PLAYER:
			return new Icon(Material.SKULL_ITEM, 3, "§7Spieler");
		case POLAR_BEAR:
			return new Icon(InterfaceUtils.buildHead("87324464-1700-468f-8333-e7779ec8c21e", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5ta"
					+ "W5lY3JhZnQubmV0L3RleHR1cmUvZDQ2ZDIzZ" + "jA0ODQ2MzY5ZmEyYTM3MDJjMTBmNzU5MTAxYWY3YmZlODQxOTk2NjQyOTUzM2NkODFhMTFkMmIifX19"))
							.rename("§7Eisbär");
		case PRIMED_TNT:
			return new Icon(Material.TNT, 0, "§7Gezündetes TNT");
		case RABBIT:
			return new Icon(InterfaceUtils.buildHead("e7411f37-9ab5-4342-a944-1f7ee2a061db", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY"
					+ "3JhZnQubmV0L3RleHR1cmUvMWVmNmV" + "kNzY2MzY4YTllMDdiM2IzMTZhODllZmNiOTM3MThjODY2ZmE5MmI5YWFhYTRlYjY4ZGZlNDc2YjFkNSJ9fX0="))
							.rename("§7Hase");
		case SHEEP:
			return new Icon(InterfaceUtils.buildHead("fa234925-9dbe-4b8f-a544-7c70fb6b6ac5", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5l"
					+ "Y3JhZnQubmV0L3RleHR1cmUvZjMxZjljY2M2YjNlMzJlY2" + "YxM2I4YTExYWMyOWNkMzNkMThjOTVmYzczZGI4YTY2YzVkNjU3Y2NiOGJlNzAifX19"));
		case SHULKER:
			return new Icon(InterfaceUtils.buildHead("ef81234c-eb95-4ed6-b914-ca4ec0ac165e", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh"
					+ "0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTQzM2E0YjczMjczYTY0YzhhYjI4MzBiMGZmZjc3" + "N2E2MWE0ODhjOTJmNjBmODNiZmIzZTQyMWY0MjhhNDQifX19"))
							.rename("§7Shulker");
		case SHULKER_BULLET:
			return new Icon(Material.SHULKER_SHELL, 0, "§7Shulker Projektil");
		case SILVERFISH:
			return new Icon(InterfaceUtils.buildHead("12e78d50-6272-4bd9-9187-8c89fcb79560", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY"
					+ "3JhZnQubmV0L3RleHR1cmUvZDA2MzEwYTg5NTJiMj" + "Y1YzZlNmJlZDQzNDgyMzlkZGVhOGU1NDgyYzhjNjhiZTZmZmY5ODFiYTgwNTZiZjJlIn19fQ=="))
							.rename("§7Silberfisch");
		case SKELETON:
			return new Icon(Material.SKULL_ITEM, 0, "§7Skelett");
		case SLIME:
			return new Icon(InterfaceUtils.buildHead("7f0b0873-df6a-4a19-9bcd-f6c90ef804c7", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJ"
					+ "lcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODk1YWVlYzZiODQyYWRhODY2OWY4NDZkNjViYzQ" + "5NzYyNTk3ODI0YWI5NDRmMjJmNDViZjNiYmI5NDFhYmU2YyJ9fX0="))
							.rename("§7Slime");
		case SMALL_FIREBALL:
			return new Icon(Material.FIREBALL, 0, "§7Kleiner Feuerball");
		case SNOWBALL:
			return new Icon(Material.SNOW_BALL, 0, "§7Schneeball");
		case SNOWMAN:
			return new Icon(InterfaceUtils.buildHead("a8096948-5255-464c-8efb-f1d01355474c", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5l"
					+ "Y3JhZnQubmV0L3RleHR1cmUvZWUyOGEwM2VkMmViOTBlYWZmMWE" + "xMTlhNWI1NTQ0NTI3MDFiOTdhZjQ3YmZmNzNjZTcxMDg0OWM2YjAifX19"))
							.rename("§7Schneegolem");
		case SPECTRAL_ARROW:
			return new Icon(Material.SPECTRAL_ARROW, 0, "§7Spektralpfeil");
		case SPIDER:
			return new Icon(InterfaceUtils.buildHead("8bdb71d0-4724-48b2-9344-e79480424798",
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0" + "dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2Q1NDE1NDFkYWFmZjU"
							+ "wODk2Y2QyNThiZGJkZDRjZjgwYzNiYTgxNjczNTcyNjA3OGJmZTM5MzkyN2U1N2YxIn19fQ=="));
		case SPLASH_POTION:
			return new Icon(Material.SPLASH_POTION, 0, "§7Wurftrank");
		case SQUID:
			return new Icon(InterfaceUtils.buildHead("f95d9504-ea2b-4b89-b2d0-d400654a7010", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW"
					+ "5lY3JhZnQubmV0L3RleHR1cmUvMDE0MzNi" + "ZTI0MjM2NmFmMTI2ZGE0MzRiODczNWRmMWViNWIzY2IyY2VkZTM5MTQ1OTc0ZTljNDgzNjA3YmFjIn19fQ=="))
							.rename("§7Tintenfisch");
		case THROWN_EXP_BOTTLE:
			return new Icon(Material.EXP_BOTTLE, 0, "§7Xp-Flasche");
		case TIPPED_ARROW:
			return new Icon(Material.TIPPED_ARROW, 0, "§7Effektpfeil");
		case UNKNOWN:
			return new Icon(InterfaceUtils.buildHead("8f4ab92d-fc8d-4321-87fb-d6f819fe7eab", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlc"
					+ "y5taW5lY3JhZnQubmV0L3RleHR1cmUvNGNlYzg1YmM4MDYxYmRhM2Ux" + "ZDQ5Zjc1NDQ2NDllNjVjODI3MmNhNTZmNzJkODM4Y2FmMmNjNDgxNmI2OSJ9fX0="))
							.rename("§7Unbekannt");
		case VILLAGER:
			return new Icon(InterfaceUtils.buildHead("0a9e8efb-9191-4c81-80f5-e27ca5433156", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5"
					+ "taW5lY3JhZnQubmV0L3RleHR1cmUvODIyZDhlN" + "zUxYzhmMmZkNGM4OTQyYzQ0YmRiMmY1Y2E0ZDhhZThlNTc1ZWQzZWIzNGMxOGE4NmU5M2IifX19"))
							.rename("§7Dorfbewohner");
		case WEATHER:
			return new Icon(Material.WATER_BUCKET, 0, "§7Wetter");
		case WITCH:
			return new Icon(InterfaceUtils.buildHead("ad889fc0-2fdc-4314-a555-7d22159160dc", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5t"
					+ "aW5lY3JhZnQubmV0L3RleHR1cmUvMjZhNj" + "llYWQ1OGI5NzEzYzM4NGY5ZGRjZTg2ZjU0YjNkMjljNmFmZDIyYjEyNzJlNDlhNDNiNWE0ODcw")).rename("§7Hexe");
		case WITHER:
			return new Icon(InterfaceUtils.buildHead("119c371b-ea16-47c9-ad7f-23b3d894520a", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5"
					+ "taW5lY3JhZnQubmV0L3RleHR1cmUvY2RmNzRlMzIzZW" + "Q0MTQzNjk2NWY1YzU3ZGRmMjgxNWQ1MzMyZmU5OTllNjhmYmI5ZDZjZjVjOGJkNDEzOWYifX19"))
							.rename("§7Wither");
		case WITHER_SKULL:
			return new Icon(Material.SKULL_ITEM, 1, "§7Witherschädel Projektil");
		case WOLF:
			return new Icon(InterfaceUtils.buildHead("a3585839-876f-4c6d-bd25-b5a4750d428b", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5"
					+ "taW5lY3JhZnQubmV0L3RleHR1cmUvYWRjNjQyOWNmY" + "WJhY2YyMTFkZDNkYjI2YzVjYTdiNTk0MmRkODI1OTlmYmIxZDUzN2NmNzJlNDk1MmUyYzdiIn19fQ=="))
							.rename("§7Wolf");
		case ZOMBIE:
			return new Icon(Material.SKULL_ITEM, 2, "§7Zombie");
		case DONKEY:
			return new Icon(InterfaceUtils.buildHead("4beb3ab6-9e20-4416-967c-d6014032ab03", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy"
					+ "5taW5lY3JhZnQubmV0L3RleHR1cmUvM2IyNT" + "I2NmQ0MGNlY2Q5M2QwNTMxNTZlNGE0YTc4NDE0MGQwMzQyNTVjNzIxY2MzNzVkMWMzNjQ4MzQyYjZmZCJ9fX0="))
							.rename("§7Esel");
		case ELDER_GUARDIAN:
			return new Icon(InterfaceUtils.buildHead("e56a8749-8a4a-40cc-9ded-3c90f8ae8c63", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5"
					+ "taW5lY3JhZnQubmV0L3RleHR1cmUvMWM3OT" + "c0ODJhMTRiZmNiODc3MjU3Y2IyY2ZmMWI2ZTZhOGI4NDEzMzM2ZmZiNGMyOWE2MTM5Mjc4YjQzNmIifX19"))
							.rename("§7Elder Guardian");
		case EVOKER:
			return new Icon(InterfaceUtils.buildHead("98f19bf0-2ba9-45f8-a89b-c0f4243909f9", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy"
					+ "5taW5lY3JhZnQubmV0L3RleHR1cmUvNmRlYWVj" + "MzQ0YWIwOTViNDhjZWFkNzUyN2Y3ZGVlNjFiMDYzZmY3OTFmNzZhOGZhNzY2NDJjODY3NmUyMTczIn19fQ=="))
							.rename("§7Evoker");
		case EVOKER_FANGS:
			return new Icon(Material.IRON_FENCE, 0, "§7Fangzähne");
		case HUSK:
			return new Icon(InterfaceUtils.buildHead("1abe147b-ea7a-470c-8e74-16ce8fed6cb6", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy"
					+ "5taW5lY3JhZnQubmV0L3RleHR1cmUvZDY" + "3NGM2M2M4ZGI1ZjRjYTYyOGQ2OWEzYjFmOGEzNmUyOWQ4ZmQ3NzVlMWE2YmRiNmNhYmI0YmU0ZGIxMjEifX19"))
							.rename("§7Wüstenzombie");
		case ILLUSIONER:
			return new Icon(InterfaceUtils.buildHead("a024cb0c-6f05-45e5-b4e3-369984721032", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY"
					+ "3JhZnQubmV0L3RleHR1cmUvMWM2NzhjOW" + "Y0YzZkZDRkOTkxOTMwZjgyZTZlN2Q4Yjg5YjI4OTFmMzVjYmE0OGE0YjE4NTM5YmJlN2VjOTI3In19fQ=="))
							.rename("§7Illusioner");
		case LLAMA:
			return new Icon(InterfaceUtils.buildHead("2738c790-8f3f-49dc-a735-9168e44ee5aa", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5ta"
					+ "W5lY3JhZnQubmV0L3RleHR1cmUvODE4" + "Y2Q0NTdmYmFmMzI3ZmEzOWYxMGI1YjM2MTY2ZmQwMTgyNjQwMzY4NjUxNjRjMDJkOWU1ZmY1M2Y0NSJ9fX0="))
							.rename("§7Lama");
		case LLAMA_SPIT:
			return InterfaceUtils.convertEntityType(EntityType.LLAMA).rename("§7Lamaspucke");
		case MULE:
			return new Icon(InterfaceUtils.buildHead("1fd5db60-329f-4dcd-9e8d-7d4adc68ff29", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJl"
					+ "cy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTA0" + "ODZhNzQyZTdkZGEwYmFlNjFjZTJmNTVmYTEzNTI3ZjFjM2IzMzRjNTdjMDM0YmI0Y2YxMzJmYjVmNWYifX19"))
							.rename("§7Maultier");
		case PARROT:
			return new Icon(InterfaceUtils.buildHead("dbde9ab3-cd6e-4822-af69-e5a2be8bd73d", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy"
					+ "5taW5lY3JhZnQubmV0L3RleHR1cmUvZjB" + "iZmE4NTBmNWRlNGIyOTgxY2NlNzhmNTJmYzJjYzdjZDdiNWM2MmNhZWZlZGRlYjljZjMxMWU4M2Q5MDk3In19fQ=="))
							.rename("§7Papagei");
		case SKELETON_HORSE:
			return new Icon(
					InterfaceUtils.buildHead("bcbce5bf-86c4-4e62-9fc5-0cc90de94b6d", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3J"
							+ "hZnQubmV0L3RleHR1cmUvNDdlZmZjZTM1MT" + "MyYzg2ZmY3MmJjYWU3N2RmYmIxZDIyNTg3ZTk0ZGYzY2JjMjU3MGVkMTdjZjg5NzNhIn19fQ=="))
									.rename("§7Skelettpferd");
		case STRAY:
			return new Icon(InterfaceUtils.buildHead("644c9bad-958b-43ce-9d2f-199d85be607c", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5ta"
					+ "W5lY3JhZnQubmV0L3RleHR1cmUvNzhkZGY3NmU1NTVkZD" + "VjNGFhOGEwYTVmYzU4NDUyMGNkNjNkNDg5YzI1M2RlOTY5ZjdmMjJmODVhOWEyZDU2In19fQ=="))
							.rename("§7Eiswanderer");
		case VEX:
			return new Icon(
					InterfaceUtils.buildHead("f6e25015-1a90-46eb-88b7-ce3f14bf00d4", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3J"
							+ "hZnQubmV0L3RleHR1cmUvYzJlYzVhNTE2NjE3Zm" + "YxNTczY2QyZjlkNWYzOTY5ZjU2ZDU1NzVjNGZmNGVmZWZhYmQyYTE4ZGM3YWI5OGNkIn19fQ=="))
									.rename("§7Vex");
		case VINDICATOR:
			new Icon(InterfaceUtils.buildHead("dbde9ab3-cd6e-4822-af69-e5a2be8bd73d", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy"
					+ "5taW5lY3JhZnQubmV0L3RleHR1cmUvZjB" + "iZmE4NTBmNWRlNGIyOTgxY2NlNzhmNTJmYzJjYzdjZDdiNWM2MmNhZWZlZGRlYjljZjMxMWU4M2Q5MDk3In19fQ=="))
							.rename("§7Papagei"); // TODO
		case WITHER_SKELETON:
			return new Icon(Material.SKULL_ITEM, 1, "§7Witherskelett");
		case ZOMBIE_HORSE:
			new Icon(InterfaceUtils.buildHead("dbde9ab3-cd6e-4822-af69-e5a2be8bd73d", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy"
					+ "5taW5lY3JhZnQubmV0L3RleHR1cmUvZjB" + "iZmE4NTBmNWRlNGIyOTgxY2NlNzhmNTJmYzJjYzdjZDdiNWM2MmNhZWZlZGRlYjljZjMxMWU4M2Q5MDk3In19fQ=="))
							.rename("§7Papagei"); // TODO
		case ZOMBIE_VILLAGER:
			new Icon(InterfaceUtils.buildHead("dbde9ab3-cd6e-4822-af69-e5a2be8bd73d", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy"
					+ "5taW5lY3JhZnQubmV0L3RleHR1cmUvZjB" + "iZmE4NTBmNWRlNGIyOTgxY2NlNzhmNTJmYzJjYzdjZDdiNWM2MmNhZWZlZGRlYjljZjMxMWU4M2Q5MDk3In19fQ=="))
							.rename("§7Papagei"); // TODO
		default:
			assert false : "Ignored entity type " + type.name();
		}

		return null;
	}

	public static Icon convertPotionEffectType(PotionEffectType type) {
		if (type == null) {
			return null;
		}

		if (type.equals(PotionEffectType.SPEED)) {
			return new Icon(Material.SUGAR, 0, "§bGeschwindigkeit §7(Buff)");
		} else if (type.equals(PotionEffectType.SLOW)) {
			return InterfaceUtils.convertEntityType(EntityType.STRAY).rename("§8Verlangsamung §7(Debuff)");
		} else if (type.equals(PotionEffectType.FAST_DIGGING)) {
			return new Icon(Material.GOLD_PICKAXE, 0, "§eHast §7(Buff)");
		} else if (type.equals(PotionEffectType.SLOW_DIGGING)) {
			return InterfaceUtils.convertEntityType(EntityType.GUARDIAN).rename("§4Abbaulähmung §7(Debuff)");
		} else if (type.equals(PotionEffectType.INCREASE_DAMAGE)) {
			return new Icon(Material.BLAZE_POWDER, 0, "§4Stärke §7(Buff)");
		} else if (type.equals(PotionEffectType.HEAL)) {
			return new Icon(Material.SPECKLED_MELON, 0, "§cDirekte Heilung §7(Instant Buff)");
		} else if (type.equals(PotionEffectType.HARM)) {
			return new Icon(Material.FERMENTED_SPIDER_EYE, 0, "§8Direkter Schaden §7(Instant Debuff)");
		} else if (type.equals(PotionEffectType.JUMP)) {
			return new Icon(Material.RABBIT_FOOT, 0, "§aSprungkraft §7(Buff)");
		} else if (type.equals(PotionEffectType.CONFUSION)) {
			return new Icon(Material.RAW_FISH, 3, "§5Übelkeit §7(Debuff)");
		} else if (type.equals(PotionEffectType.REGENERATION)) {
			return new Icon(Material.GHAST_TEAR, 0, "§dRegerneration §7(Buff)");
		} else if (type.equals(PotionEffectType.DAMAGE_RESISTANCE)) {
			return new Icon(Material.IRON_CHESTPLATE, 0, "§6Resistanz §7(Buff)");
		} else if (type.equals(PotionEffectType.FIRE_RESISTANCE)) {
			return new Icon(Material.LAVA_BUCKET, 0, "§6Feuer-Resistanz §7(Buff)");
		} else if (type.equals(PotionEffectType.WATER_BREATHING)) {
			return new Icon(Material.RAW_FISH, 0, "§9Unterwasseratem §7(Buff)");
		} else if (type.equals(PotionEffectType.INVISIBILITY)) {
			return new Icon(Material.GLASS, 0, "§7Unsichtbarkeit (Buff)");
		} else if (type.equals(PotionEffectType.BLINDNESS)) {
			return new Icon(Material.COAL_BLOCK, 0, "§8Blindheit §7(Buff))");
		} else if (type.equals(PotionEffectType.NIGHT_VISION)) {
			return new Icon(Material.GOLDEN_CARROT, 0, "§1Nachsicht §7(Buff)");
		} else if (type.equals(PotionEffectType.HUNGER)) {
			return new Icon(Material.ROTTEN_FLESH, 0, "§2Hunger §7(Debuff)");
		} else if (type.equals(PotionEffectType.WEAKNESS)) {
			return new Icon(Material.WOOD_SWORD, 0, "§8Schwäche §7(Debuff)");
		} else if (type.equals(PotionEffectType.POISON)) {
			return new Icon(Material.SPIDER_EYE, 0, "§2Vergiftung §7(Debuff)");
		} else if (type.equals(PotionEffectType.WITHER)) {
			return InterfaceUtils.convertEntityType(EntityType.WITHER_SKELETON).rename("§8Wither §7(Debuff)");
		} else if (type.equals(PotionEffectType.HEALTH_BOOST)) {
			return new Icon(Material.GOLDEN_APPLE, 0, "§6Extraenergie §7(Buff)");
		} else if (type.equals(PotionEffectType.ABSORPTION)) {
			return new Icon(Material.GOLDEN_APPLE, 1, "§3Absorbtion §7(Buff)");
		} else if (type.equals(PotionEffectType.SATURATION)) {
			return new Icon(Material.COOKED_BEEF, 0, "§fSättigung §7(Buff)");
		} else if (type.equals(PotionEffectType.GLOWING)) {
			return new Icon(Material.SPECTRAL_ARROW, 0, "§aLeuchten §7(Buff/Debuff)");
		} else if (type.equals(PotionEffectType.LEVITATION)) {
			return new Icon(Material.ELYTRA, 0, "§bSchwebekraft §7(Buff/Debuff)");
		} else if (type.equals(PotionEffectType.LUCK)) {
			return new Icon(Material.EMERALD, 0, "§2Glück §7(Buff)");
		} else if (type.equals(PotionEffectType.UNLUCK)) {
			return new Icon(Material.DIRT, 0, "§ePech §7(Debuff)");
		} else {
			assert false : "Ignored potion effect type " + type.getName();
			return null;
		}
	}

	public static Icon convertPotionType(PotionType potionType) {
		switch (potionType) {
		case AWKWARD:
			return new Icon(Material.POTION, 0, "§7Unnütze Potion");
		case FIRE_RESISTANCE:
			return InterfaceUtils.convertPotionEffectType(PotionEffectType.FIRE_RESISTANCE);
		case INSTANT_DAMAGE:
			return InterfaceUtils.convertPotionEffectType(PotionEffectType.HARM);
		case INSTANT_HEAL:
			return InterfaceUtils.convertPotionEffectType(PotionEffectType.HEAL);
		case INVISIBILITY:
			return InterfaceUtils.convertPotionEffectType(PotionEffectType.INVISIBILITY);
		case JUMP:
			return InterfaceUtils.convertPotionEffectType(PotionEffectType.JUMP);
		case LUCK:
			return InterfaceUtils.convertPotionEffectType(PotionEffectType.LUCK);
		case MUNDANE:
			return InterfaceUtils.convertPotionType(PotionType.AWKWARD);
		case NIGHT_VISION:
			return InterfaceUtils.convertPotionEffectType(PotionEffectType.NIGHT_VISION);
		case POISON:
			return InterfaceUtils.convertPotionEffectType(PotionEffectType.POISON);
		case REGEN:
			return InterfaceUtils.convertPotionEffectType(PotionEffectType.REGENERATION);
		case SLOWNESS:
			return InterfaceUtils.convertPotionEffectType(PotionEffectType.SLOW);
		case SPEED:
			return InterfaceUtils.convertPotionEffectType(PotionEffectType.SPEED);
		case STRENGTH:
			return InterfaceUtils.convertPotionEffectType(PotionEffectType.INCREASE_DAMAGE);
		case THICK:
			return InterfaceUtils.convertPotionType(PotionType.AWKWARD);
		case UNCRAFTABLE:
			return InterfaceUtils.convertPotionType(PotionType.AWKWARD);
		case WATER:
			return new Icon(Material.GLASS_BOTTLE, 1, "§7Wasserflasche");
		case WATER_BREATHING:
			return InterfaceUtils.convertPotionEffectType(PotionEffectType.WATER_BREATHING);
		case WEAKNESS:
			return InterfaceUtils.convertPotionEffectType(PotionEffectType.WEAKNESS);
		default:
			assert false : "Ignored potion type " + potionType.name();
			return null;
		}
	}

	public static ItemStack buildHead(Player player) {
		String value = ((CraftPlayer) player).getHandle().getProfile().getProperties().get("textures").iterator().next().getValue();

		return InterfaceUtils.buildHead("{SkullOwner:{Id:\"" + player.getUniqueId().toString() + "\",Properties:" + "{textures:[{Value:\"" + value + "\"}]}}}");
	}

	public static ItemStack buildHead(String ownerUuid, String value) {
		return InterfaceUtils.buildHead("{SkullOwner:{Id:\"" + ownerUuid + "\",Properties:{textures:[{Value:\"" + value + "\"}]}}}");
	}

	public static ItemStack buildHead(String nbt) {
		ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		NBTTagCompound nbtTag;

		try {
			nbtTag = NBTParser.parse(nbt);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		net.minecraft.server.v1_12_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);

		if (nmsItem.getTag() == null) {
			nmsItem.setTag(new NBTTagCompound());
		}

		nmsItem.getTag().a(nbtTag);
		return CraftItemStack.asBukkitCopy(nmsItem);
	}

	public static ItemStack normalize(ItemStack input) {
		return input != null ? (input.getType() != Material.AIR ? input : null) : null;
	}

	public static ItemStack normalizeAir(ItemStack input) {
		return input != null ? input : new ItemStack(Material.AIR);
	}

}
