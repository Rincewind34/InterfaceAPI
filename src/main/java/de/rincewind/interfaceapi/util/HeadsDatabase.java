package de.rincewind.interfaceapi.util;

import org.bukkit.inventory.ItemStack;

import de.rincewind.interfaceapi.gui.elements.util.Icon;

public class HeadsDatabase {

	private static final ItemStack arrow_wood_up = InterfaceUtils.buildHead("478ca0c8-89b1-432c-932b-bb78eb462e06",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzNjZjE2NmE4"
					+ "MjZkODU1NDU0ZWQ0ZDRlYTVmZTMzZjNkZWVhYTQ0Y2NhYTk5YTM0OGQzOTY4NWJhNzFlMWE0ZiJ9fX0=");
	private static final ItemStack arrow_wood_down = InterfaceUtils.buildHead("ee5fed6c-d481-4006-b061-449bdd6b6480",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzhjYTc2MTVjYj"
					+ "gzN2Y2OTRlNDk2ZmY4YTk4NTNjZDdkYjVmZDg1NTI5ZGNhZDk4Yzc4YmEyNmMzZTRmNjg3In19fQ==");
	private static final ItemStack arrow_wood_left = InterfaceUtils.buildHead("63dcc361-6c3e-42ed-87ec-01c356c8a6d9",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzM3NjQ4YWU3YT"
					+ "U2NGE1Mjg3NzkyYjA1ZmFjNzljNmI2YmQ0N2Y2MTZhNTU5Y2U4YjU0M2U2OTQ3MjM1YmNlIn19fQ==");
	private static final ItemStack arrow_wood_right = InterfaceUtils.buildHead("69e2e99e-7009-440e-a6d1-75b1c2ca5b6a",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWE0ZjY4YzhmYjI3OWU1MGFiNzg2ZjlmYT"
					+ "U0Yzg4Y2E0ZWNmZTFlYjVmZDVmMGMzOGM1NGM5YjFjNzIwM2Q3YSJ9fX0=");
	private static final ItemStack arrow_stone_up = InterfaceUtils.buildHead("e4d7b07b-59fc-4f77-b08b-b0446048dcd4",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNThmZTI1MWE0MGU0MTY3ZDM1ZDA4MWMyNz"
					+ "g2OWFjMTUxYWY5NmI2YmQxNmRkMjgzNGQ1ZGM3MjM1ZjQ3NzkxZCJ9fX0=");
	private static final ItemStack arrow_stone_down = InterfaceUtils.buildHead("ccd469f7-1df1-42f9-8915-15de387906e4",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWI3Y2U2ODNkMDg2OGFhNDM3OGFlYjYwY2"
					+ "FhNWVhODA1OTZiY2ZmZGFiNmI1YWYyZDEyNTk1ODM3YTg0ODUzIn19fQ==");
	private static final ItemStack arrow_stone_left = InterfaceUtils.buildHead("2fad8146-186b-4c9c-8c62-7d5ccb083faa",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmIwZjZlOGFmNDZhYzZmYWY4ODkxNDE5MW"
					+ "FiNjZmMjYxZDY3MjZhNzk5OWM2MzdjZjJlNDE1OWZlMWZjNDc3In19fQ==");
	private static final ItemStack arrow_stone_right = InterfaceUtils.buildHead("925b071a-7c83-43e7-9d83-8f231c8217d4",
			"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjJmM2EyZGZjZTBjM2RhYjdlZTEwZGIzOD"
					+ "VlNTIyOWYxYTM5NTM0YThiYTI2NDYxNzhlMzdjNGZhOTNiIn19fQ==");

	public static Icon arrowWoodUp() {
		return new Icon(HeadsDatabase.arrow_wood_up).removeName().showInfo(false);
	}

	public static Icon arrowWoodDown() {
		return new Icon(HeadsDatabase.arrow_wood_down).removeName().showInfo(false);
	}

	public static Icon arrowWoodLeft() {
		return new Icon(HeadsDatabase.arrow_wood_left).removeName().showInfo(false);
	}

	public static Icon arrowWoodRight() {
		return new Icon(HeadsDatabase.arrow_wood_right).removeName().showInfo(false);
	}

	public static Icon arrowStoneUp() {
		return new Icon(HeadsDatabase.arrow_stone_up).removeName().showInfo(false);
	}

	public static Icon arrowStoneDown() {
		return new Icon(HeadsDatabase.arrow_stone_down).removeName().showInfo(false);
	}

	public static Icon arrowStoneLeft() {
		return new Icon(HeadsDatabase.arrow_stone_left).removeName().showInfo(false);
	}

	public static Icon arrowStoneRight() {
		return new Icon(HeadsDatabase.arrow_stone_right).removeName().showInfo(false);
	}

}
