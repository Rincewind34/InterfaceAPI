package de.rincewind.interfaceplugin.gui.elements;

import org.bukkit.Material;

import de.rincewind.interfaceapi.gui.components.Modifyable;
import de.rincewind.interfaceapi.gui.elements.ElementButton;
import de.rincewind.interfaceapi.gui.elements.ElementCounter;
import de.rincewind.interfaceapi.gui.elements.abstracts.Element;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceapi.handling.InterfaceListener;
import de.rincewind.interfaceapi.handling.element.ButtonPressEvent;
import de.rincewind.interfaceapi.handling.element.ElementInteractEvent;
import de.rincewind.interfaceplugin.Validate;
import de.rincewind.interfaceplugin.gui.elements.abstracts.CraftElementDisplayable;

public class CraftElementCounter extends CraftElementDisplayable implements ElementCounter {

	private int count;
	
	private int minCount;
	private int maxCount;
	
	public CraftElementCounter(Modifyable handle) {
		super(handle);
		
		this.minCount = ElementCounter.MINIMUM_COUNT;
		this.maxCount = ElementCounter.MAXIMUM_COUNT;
		
		this.getComponent(Element.WIDTH).setEnabled(true);
		this.getComponent(Element.HEIGHT).setEnabled(true);
		
		this.getEventManager().registerListener(ElementInteractEvent.class, (event) -> {
			this.setCount((int) (this.minCount + (this.maxCount - this.minCount) / 2.0D));
		}).addAfter();
	}
	
	@Override
	public int getCount() {
		return this.count;
	}

	@Override
	public int getMaxCount() {
		return this.maxCount;
	}

	@Override
	public int getMinCount() {
		return this.minCount;
	}

	@Override
	public void setMaxCount(int maxCount) {
		if (maxCount > ElementCounter.MAXIMUM_COUNT) {
			this.setMaxCount(ElementCounter.MAXIMUM_COUNT);
		}
		
		if (maxCount < ElementCounter.MINIMUM_COUNT) {
			this.setMaxCount(ElementCounter.MINIMUM_COUNT);
		}
		
		if (maxCount < this.minCount) {
			throw new RuntimeException("The maximal count is smaller than the current minimal count!");
		}
		
		this.maxCount = maxCount;
		this.setCount(this.count);
	}

	@Override
	public void setMinCount(int minCount) {
		if (minCount > ElementCounter.MAXIMUM_COUNT) {
			this.setMinCount(ElementCounter.MAXIMUM_COUNT);
		}
		
		if (minCount < ElementCounter.MINIMUM_COUNT) {
			this.setMinCount(ElementCounter.MINIMUM_COUNT);
		}
		
		if (minCount > this.maxCount) {
			throw new RuntimeException("The minimal count is bigger than the current maximal count!");
		}
		
		this.minCount = minCount;
		this.setCount(this.count);
	}

	@Override
	public void setCount(int count) {
		if (count > this.maxCount) {
			this.setCount(this.maxCount);
			return;
		}
		
		if (count < this.minCount) {
			this.setCount(this.minCount);
			return;
		}
		
		if (count != this.count) {
			this.count = count;
			this.update();
		}
	}
	
	@Override
	public void increment() {
		this.setCount(this.count + 1);
	}

	@Override
	public void decrement() {
		this.setCount(this.count - 1);
	}

	@Override
	public void addIncrementer(ElementButton btn, int value) {
		Validate.notNull(btn, "The button cannot be null!");
		
		if (value == 0) {
			throw new RuntimeException("The value cannot be zero!");
		}
		
		btn.getEventManager().registerListener(ButtonPressEvent.class, new ActionHandler(value)).addAfter();
	}
	
	@Override
	public void addIncrementer(ElementButton btn, int value, Point point) {
		Validate.notNull(point, "The point cannot be null!");
		
		btn.setIcon(new Icon(Material.ARROW, 0, (value < 0 ? "§c- " : "§a+ ") + "§7" + Math.abs(value) + " " + this.getIcon().getName()));
		btn.setPoint(point);
		
		this.addIncrementer(btn, value);
	}
	
	@Override
	public Icon getIcon(Point point) {
		Icon icon = super.getIcon(point);
		
		if (this.isEnabled() && icon != null) {
			return icon.count(this.count);
		} else {
			return icon;
		}
	}
	
	
	private class ActionHandler implements InterfaceListener<ButtonPressEvent> {

		private int value;
		
		private ActionHandler(int value) {
			this.value = value;
		}
		
		@Override
		public void onAction(ButtonPressEvent event) {
			CraftElementCounter.this.setCount(CraftElementCounter.this.getCount() + this.value * (event.isShiftClick() ? 2 : 1));
		}
		
	}

}
