package de.rincewind.plugin.gui.windows.abstracts;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.Color;
import de.rincewind.api.gui.components.Locatable.Point;
import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.windows.abstracts.WindowColorable;

public abstract class CraftWindowColorable extends CraftWindowEditor implements WindowColorable {
	
	private Color color;
	
	public CraftWindowColorable() {
		super();
		
		this.color = Color.TRANSLUCENT;
	}
	
	@Override
	public void updateBukkitInventory() {
		super.updateBukkitInventory();
		
		List<Point> usedPoints = new ArrayList<>();
		
		this.iterate((point) -> {
			if (this.hasSpaceAt(point)) {
				return;
			}
			
			ItemStack item = this.getItemAt(point);
			
			if (item.equals(Modifyable.EMPTY_USED_SLOT)) {
				usedPoints.add(point);
			} else if (item.equals(Modifyable.INVISIBLE_ELEMENT)) {
				return;
			} else {
				usedPoints.add(point);
				this.getBukkitInventory().setItem(this.getSlot(point), item);
			}
		});
		
		
		this.createBackground(usedPoints);
		
	}
	
	@Override
	public Color getColor() {
		return this.color;
	}
	
	@Override
	public void setColor(Color color) {
		this.color = color;
		this.updateBukkitInventory();
	}
	
	public void createBackground(List<Point> usedPoints) {
		this.iterate((point) -> {
			for (Point target : usedPoints) {
				if (target.isSimilar(point)) {
					return;
				}
			}
			
			this.getBukkitInventory().setItem(this.getSlot(point), this.color.asItem());
		});
	}
	
}
