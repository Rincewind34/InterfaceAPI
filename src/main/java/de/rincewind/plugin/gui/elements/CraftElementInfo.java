package de.rincewind.plugin.gui.elements;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.rincewind.api.DebugScene;
import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.ElementInfo;
import de.rincewind.api.gui.elements.util.Icon;
import de.rincewind.api.item.ItemRefactor.Lore;
import de.rincewind.plugin.gui.elements.abstracts.CraftElementDisplayable;

public class CraftElementInfo extends CraftElementDisplayable implements ElementInfo {
	
	public CraftElementInfo(Modifyable handle) {
		super(handle);
		
		this.reset();
	}

	@Override
	public void handleClick(InventoryClickEvent event) {
		this.reset();
	}
	
	@Override
	public void debug(String info, boolean positiv) {
		this.debug(Arrays.asList(info), positiv);
	}
	
	@Override
	public void debug(List<String> info, boolean positiv) {
		this.setIcon(this.getIcon().rename(positiv ? "§aStatus" : "§cStatus").damage(positiv ? 13 : 14).typecast(Material.STAINED_GLASS).describe(this.createLore(info)));
		this.getHandle().readItemsFrom(this);
	}
	
	@Override
	public void debug(DebugScene scene) {
		this.debug(scene.getDebug(), scene.isPositiv());
	}
	
	
	protected Lore createLore(List<String> info) {
		Lore lore = new Lore();
		lore.add("§8====================");
		
		for (String element : info) {
			lore.add("  " + element);
		}
		
		lore.add("§8====================");
		return lore;
	}
	
	protected Icon createDefaultIcon() {
		return new Icon(Material.STAINED_GLASS, 0, "§fStatus").describe(this.createLore(Arrays.asList("§7§oNothing happend")));
	}

	@Override
	public void reset() {
		this.setIcon(this.createDefaultIcon());
	}

}
