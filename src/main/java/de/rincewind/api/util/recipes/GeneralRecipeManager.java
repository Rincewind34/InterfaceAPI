package de.rincewind.api.util.recipes;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.google.common.annotations.Beta;

import de.rincewind.api.util.recipes.RecipeManager.RecipeManagerExtendable;

@Beta
public class GeneralRecipeManager extends RecipeManagerExtendable {
	
	public GeneralRecipeManager() {
		this.addRecipe(new RecipeShaped(new ItemStack(Material.WORKBENCH), "WW", "WW"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.PAPER, 3), "SSS"));
//		this.addRecipe(new ShapedRecipe(new ItemStack(Material.FENCE, 6), "WRW", "WRW"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.COBBLE_WALL, 6, (byte) 0), "CCC", "CCC"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.COBBLE_WALL, 6, (byte) 1), "YYY", "YYY"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.NETHER_FENCE, 6), "NNN", "NNN"));
//		this.addRecipe(new ShapedRecipe(new ItemStack(Material.FENCE_GATE), "NNN", "NNN"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.JUKEBOX), "WWW", "WDW", "WWW"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.LEASH, 2), "~~X", "~QX", "XX~"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.NOTE_BLOCK), "WWW", "WRW", "WWW"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.BOOKSHELF), "WWW", "BBB", "WWW"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.SNOW_BLOCK), "ss", "ss"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.SNOW, 6), "sss"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.CLAY), "cc", "cc"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.BRICK), "bb", "bb"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.GLOWSTONE), "GG", "GG"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.QUARTZ_BLOCK), "qq", "qq"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.WOOL), "~~", "~~"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.TNT), "gKg", "KgK", "gKg"));
//		this.addRecipe(new ShapedRecipe(new ItemStack(Material.STONE_SLAB2), "qq", "qq"));
//		this.addRecipe(new ShapedRecipe(new ItemStack(Material.WOOD_STEP), "qq", "qq"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.LADDER, 3), "|x|", "|||", "|x|"));
//		this.addRecipe(new ShapedRecipe(new ItemStack(Material.WOOD_DOOR), "qq", "qq"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.TRAP_DOOR, 2), "WWW", "WWW"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.IRON_DOOR, 3), "II", "II", "II"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.IRON_TRAPDOOR), "III", "III"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.SIGN, 3), "WWW", "WWW", "X|X"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.CAKE), "MMM", "TET", "www"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.SUGAR), "S"));
//		this.addRecipe(new ShapedRecipe(new ItemStack(Material.WOOD, 4), "MMM", "TET", "www"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.STICK, 4), "W", "W"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.TORCH, 4), "ö", "|"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.BOWL, 4), "WXW", "XWX"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.GLASS_BOTTLE, 3), "ÜXÜ", "XÜX"));
//		TODO RAILS
		this.addRecipe(new RecipeShaped(new ItemStack(Material.MINECART), "IXI", "III"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.CAULDRON_ITEM), "IXI", "IXI", "III"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.BREWING_STAND), "XLX", "CCC"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.JACK_O_LANTERN), "t", "P"));
//		TODO OTHER MINECARTS
		this.addRecipe(new RecipeShaped(new ItemStack(Material.BOAT), "WXW", "WWW"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.BUCKET), "IXI", "XIX"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.FLOWER_POT_ITEM), "bXb", "XbX"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.BREAD), "www"));
//		this.addRecipe(new ShapedRecipe(new ItemStack(Material.WOOD_STAIRS), "IXI", "XIX"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.FISHING_ROD), "XXW", "XW~", "WX~"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.CARROT_STICK), "F", "K"));
//		TODO ALL THE STAIRS
		this.addRecipe(new RecipeShaped(new ItemStack(Material.PAINTING), "|||", "|Ö|", "|||"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.ITEM_FRAME), "|||", "|l|", "|||"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.GOLDEN_APPLE), "iii", "iAi", "iii"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.GOLDEN_APPLE, 1, (byte) 1), "ÄÄÄ", "ÄAÄ", "ÄÄÄ"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.GOLDEN_CARROT), "nnn", "nKn", "nnn"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.SPECKLED_MELON), "nnn", "nMn", "nnn"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.LEVER), "|", "C"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.TRIPWIRE_HOOK, 2), "I", "|", "W"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.REDSTONE_TORCH_ON), "R", "|"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.DIODE), "RrR", "äää"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.REDSTONE_COMPARATOR), "XrX", "rqr", "äää"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.WATCH), "XiX", "IRi", "XiX"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.COMPASS), "XIX", "IRI", "XIX"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.MAP), "ppp", "püp", "ppp"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.STONE_BUTTON), "ä"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.WOOD_BUTTON), "W"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.STONE_PLATE), "ää"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.WOOD_PLATE), "WW"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.IRON_PLATE), "II"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.GOLD_PLATE), "ii"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.DISPENSER), "CCC", "CxC", "CRC"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.DROPPER), "CCC", "CXC", "CRC"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.PISTON_BASE), "WWW", "CIC", "CRC"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.PISTON_STICKY_BASE), "Q", "1"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.BED), "ÖÖÖ", "WWW"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.ENCHANTMENT_TABLE), "XBX", "DOD", "OOO"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.ANVIL), "III", "X2X", "222"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.LEATHER), "HH", "HH"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.DAYLIGHT_DETECTOR), "ÜÜÜ", "qqq", "333"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.HOPPER), "IXI", "I5I", "XIX"));
		this.addRecipe(new RecipeShaped(new ItemStack(Material.ARMOR_STAND), "|||", "X¦X", "|6|"));
		
		RecipeShaped.setStaticIngradient('W', Material.WOOD);
		RecipeShaped.setStaticIngradient('S', Material.SUGAR_CANE);
		RecipeShaped.setStaticIngradient('R', Material.REDSTONE);
		RecipeShaped.setStaticIngradient('C', Material.COBBLESTONE);
		RecipeShaped.setStaticIngradient('N', Material.NETHER_FENCE);
		RecipeShaped.setStaticIngradient('Y', Material.MOSSY_COBBLESTONE);
		RecipeShaped.setStaticIngradient('D',Material.DIAMOND);
		RecipeShaped.setStaticIngradient('X', Material.AIR);
		RecipeShaped.setStaticIngradient('~', Material.STRING);
		RecipeShaped.setStaticIngradient('Q', Material.SLIME_BALL);
		RecipeShaped.setStaticIngradient('B', Material.BOOK);
		RecipeShaped.setStaticIngradient('G', Material.GLOWSTONE_DUST);
		RecipeShaped.setStaticIngradient('s', Material.SNOW_BALL);
		RecipeShaped.setStaticIngradient('c', Material.CLAY_BALL);
		RecipeShaped.setStaticIngradient('b', Material.CLAY_BRICK);
		RecipeShaped.setStaticIngradient('q', Material.QUARTZ);
		RecipeShaped.setStaticIngradient('g', Material.SULPHUR);
		RecipeShaped.setStaticIngradient('K', Material.SAND);
		RecipeShaped.setStaticIngradient('|', Material.STICK);
		RecipeShaped.setStaticIngradient('I', Material.IRON_INGOT);
		RecipeShaped.setStaticIngradient('w', Material.WHEAT);
		RecipeShaped.setStaticIngradient('E', Material.EGG);
		RecipeShaped.setStaticIngradient('T', Material.SUGAR);
		RecipeShaped.setStaticIngradient('ö', Material.COAL);
		RecipeShaped.setStaticIngradient('Ü', Material.GLASS);
		RecipeShaped.setStaticIngradient('L', Material.BLAZE_ROD);
		RecipeShaped.setStaticIngradient('t', Material.TORCH);
		RecipeShaped.setStaticIngradient('P', Material.PUMPKIN);
		RecipeShaped.setStaticIngradient('K', Material.CARROT_ITEM);
		RecipeShaped.setStaticIngradient('Ö', Material.WOOL);
		RecipeShaped.setStaticIngradient('l', Material.LEATHER);
		RecipeShaped.setStaticIngradient('i', Material.GOLD_INGOT);
		RecipeShaped.setStaticIngradient('Ä', Material.GOLD_BLOCK);
		RecipeShaped.setStaticIngradient('n', Material.GOLD_NUGGET);
		RecipeShaped.setStaticIngradient('M', Material.MELON);
		RecipeShaped.setStaticIngradient('r', Material.REDSTONE_TORCH_ON);
		RecipeShaped.setStaticIngradient('ä', Material.STONE);
		RecipeShaped.setStaticIngradient('p', Material.PAPER);
		RecipeShaped.setStaticIngradient('ü', Material.COMPASS);
		RecipeShaped.setStaticIngradient('x', Material.BOW);
		RecipeShaped.setStaticIngradient('ü', Material.COMPASS);
		RecipeShaped.setStaticIngradient('1', Material.PISTON_BASE);
		RecipeShaped.setStaticIngradient('O', Material.OBSIDIAN);
		RecipeShaped.setStaticIngradient('2', Material.IRON_BLOCK);
		RecipeShaped.setStaticIngradient('H', Material.RABBIT_HIDE);
		RecipeShaped.setStaticIngradient('4', Material.BLAZE_POWDER);
		RecipeShaped.setStaticIngradient('3', Material.WOOD_STEP);
		RecipeShaped.setStaticIngradient('4', Material.BLAZE_POWDER);
		RecipeShaped.setStaticIngradient('5', Material.CHEST);
		RecipeShaped.setStaticIngradient('6', Material.STEP);
		
	}
	
}
