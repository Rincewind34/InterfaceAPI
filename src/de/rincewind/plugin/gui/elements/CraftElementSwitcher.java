package de.rincewind.plugin.gui.elements;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

import de.rincewind.api.events.ButtonPressEvent;
import de.rincewind.api.gui.components.Modifyable;
import de.rincewind.api.gui.elements.ElementSwitcher;
import de.rincewind.api.listener.ButtonPressListener;

public class CraftElementSwitcher extends CraftElementButton implements ElementSwitcher {

	private Map<Integer, ItemStack> items;
	private int switchid;
	
	public CraftElementSwitcher(Modifyable handle) {
		super(handle);
		
		this.items = new HashMap<>();
		this.registerListener();
	}
	
	@Override
	public void setIcon(ItemStack icon) {
		super.setIcon(icon);
		
		this.items.put(0, icon);
		this.setSwitchId(0);
	}
	
	@Override
	public void updateItemMap() {
		this.iterate((point) -> {
			this.setItemAt(point, this.items.get(this.switchid));
		});
		
		this.updateItemMap(false);
	}
	
	@Override
	public void next() {
		this.setSwitchId(this.getSwitchid() + 1);
	}
	
	@Override
	public void back() {
		this.setSwitchId(this.getSwitchid() - 1);
	}
	
	@Override
	public void setSwitchId(int id) {
		this.switchid = id;
		
		if (this.switchid < 0) {
			this.switchid = this.items.size() - 1;
		}
		
		if (this.items.size() == this.switchid) {
			this.switchid = 0;
		}
		this.getHandle().readItemsFrom(this);
	}
	
	@Override
	public int getSwitchid() {
		return this.switchid;
	}
	
	@Override
	public void addSwitch(ItemStack item) {
		this.items.put(this.items.size(), item);
		this.getHandle().readItemsFrom(this);
	}
	
	@Override
	public void removeSwitch(ItemStack item) {
		this.items.remove(item);
		this.setSwitchId(this.getSwitchid());
	}
	
	@Override
	public void clear() {
		this.items.clear();
	}
	
	@Override
	public int size() {
		return this.items.size();
	}
	
	protected void registerListener() {
		this.getEventManager().addListener(new ButtonPressListener() {
			
			@Override
			public void onFire(ButtonPressEvent event) {
				if (event.isRightClick()) {
					back();
					
					if (event.isShiftClick()) {
						back();
					}
				} else {
					next();
					
					if (event.isShiftClick()) {
						next(); 
					}
				}
			}
		});
	}
	
}
