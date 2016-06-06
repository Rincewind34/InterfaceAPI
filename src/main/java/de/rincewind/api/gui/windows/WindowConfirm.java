package de.rincewind.api.gui.windows;

import java.util.function.Consumer;

import org.bukkit.Material;

import de.rincewind.api.InterfaceAPI;
import de.rincewind.api.gui.elements.ElementButton;
import de.rincewind.api.gui.elements.util.Icon;
import de.rincewind.api.gui.elements.util.Point;
import de.rincewind.api.gui.util.Color;
import de.rincewind.api.gui.windows.util.Windows.WindowSizeableExtendable;
import de.rincewind.api.handling.events.ButtonPressEvent;
import de.rincewind.api.handling.listener.ButtonPressListener;
import de.rincewind.api.item.IconCreator;

public class WindowConfirm extends WindowSizeableExtendable {
	
	private Consumer<Boolean> action;
	
	private ElementButton btnDeny;
	private ElementButton btnConfirm;
	
	public WindowConfirm(String name, Consumer<Boolean> action) {
		this(name, action, 5);
	}
	
	public WindowConfirm(String name, Consumer<Boolean> action, int width) {
		this(name, action, width,
				new Icon(Material.STAINED_CLAY, 13, "§aConfirm"),
				new Icon(Material.STAINED_CLAY, 14, "§cNegate"));
	}
	
	public WindowConfirm(String name, Consumer<Boolean> action, int width, IconCreator iconConfirm, IconCreator iconDeny) {
		this(name, action, width, iconConfirm.createIcon(), iconDeny.createIcon());
	}
	
	public WindowConfirm(String name, Consumer<Boolean> action, int width, Icon iconConfirm, Icon iconDeny) {
		super.setName(name);
		super.setSize(width, 1);
		super.setColor(Color.BLACK);
		
		this.action = action;
		
		this.btnConfirm = this.elementCreator().newButton();
		this.btnConfirm.setIcon(iconConfirm);
		this.btnConfirm.setPoint(new Point(0, 0));
		this.btnConfirm.getEventManager().registerListener(new ActionHandler()).addAfter();
	
		this.btnDeny = this.elementCreator().newButton();
		this.btnDeny.setIcon(iconDeny);
		this.btnDeny.setPoint(new Point(width - 1, 0));
		this.btnDeny.getEventManager().registerListener(new ActionHandler()).addAfter();
	}
	
	public Consumer<Boolean> getAction() {
		return this.action;
	}
	
	private class ActionHandler extends ButtonPressListener {

		@Override
		public void onFire(ButtonPressEvent event) {
			if (event.getElement() == WindowConfirm.this.btnConfirm) {
				WindowConfirm.this.action.accept(true);
			} else if (event.getElement() == WindowConfirm.this.btnDeny) {
				WindowConfirm.this.action.accept(false);
			}
			
			InterfaceAPI.getSetup(WindowConfirm.this.getUser()).close(WindowConfirm.this);
		}
		
	}
	
}
