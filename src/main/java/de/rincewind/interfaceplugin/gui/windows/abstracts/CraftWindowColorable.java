package de.rincewind.interfaceplugin.gui.windows.abstracts;

import org.bukkit.plugin.Plugin;

import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceapi.gui.util.Color;
import de.rincewind.interfaceapi.gui.windows.abstracts.WindowColorable;
import de.rincewind.interfaceplugin.Validate;

public abstract class CraftWindowColorable extends CraftWindowEditor implements WindowColorable {
	
	private Color color;
	
	public CraftWindowColorable(Plugin plugin) {
		super(plugin);
		
		this.color = Color.NONE;
	}
	
//	@Override
//	public void updateBukkitInventory() {
//		super.updateBukkitInventory();
//		
//		List<Point> usedPoints = new ArrayList<>();
//		
//		this.iterate((point) -> {
//			if (this.hasSpaceAt(point)) {
//				return;
//			}
//			
//			ItemStack item = this.getItemAt(point);
//			
//			if (item.equals(Modifyable.EMPTY_USED_SLOT)) {
//				usedPoints.add(point);
//			} else if (item.equals(Modifyable.INVISIBLE_ELEMENT)) {
//				return;
//			} else {
//				usedPoints.add(point);
//				this.getBukkitInventory().setItem(this.getSlot(point), item);
//			}
//		});
//		
//		
//		this.createBackground(usedPoints);
//	}
	
	@Override
	public Color getColor() {
		return this.color;
	}
	
	@Override
	public void setColor(Color color) {
		Validate.notNull(color, "The color cannot be null!");
		
		this.color = color;
		this.renderFrame();
	}
	
	@Override
	public Icon getIcon(Point point) {
		Element element = this.getVisibleElementAt(point);

		if (element == null) {
			return this.color.asIcon();
		} else {
			return element.getIcon(point.subtract(element.getPoint()));
		}
	}
	
//	public void createBackground(List<Point> usedPoints) {
//		this.iterate((point) -> {
//			for (Point target : usedPoints) {
//				if (target.isSimilar(point)) {
//					return;
//				}
//			}
//			
//			this.getBukkitInventory().setItem(this.getSlot(point), this.color.asItem());
//		});
//	}
	
}
