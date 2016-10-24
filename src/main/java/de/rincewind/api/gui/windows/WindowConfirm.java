package de.rincewind.api.gui.windows;

import java.util.function.Consumer;

import org.bukkit.Material;

import de.rincewind.api.InterfaceAPI;
import de.rincewind.api.gui.elements.ElementButton;
import de.rincewind.api.gui.elements.util.Icon;
import de.rincewind.api.gui.elements.util.Point;
import de.rincewind.api.gui.util.Color;
import de.rincewind.api.gui.windows.util.Windows.WindowSizeableExtendable;
import de.rincewind.api.handling.InterfaceListener;
import de.rincewind.api.handling.events.ButtonPressEvent;
import de.rincewind.api.item.IconCreator;

public class WindowConfirm extends WindowSizeableExtendable {
	
	private Consumer<Boolean> action;
	
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
		this.setName(name);
		this.setSize(width, 1);
		this.setColor(Color.BLACK);
		
		this.action = action;
		
		ElementButton btnConfirm = this.elementCreator().newButton();
		btnConfirm.setIcon(iconConfirm);
		btnConfirm.setPoint(new Point(0, 0));
		btnConfirm.getEventManager().registerListener(ButtonPressEvent.class, this.new ActionHandler(true)).addAfter();
	
		ElementButton btnDeny = this.elementCreator().newButton();
		btnDeny.setIcon(iconDeny);
		btnDeny.setPoint(new Point(width - 1, 0));
		btnDeny.getEventManager().registerListener(ButtonPressEvent.class, this.new ActionHandler(true)).addAfter();
	}
	
	public Consumer<Boolean> getAction() {
		return this.action;
	}
	
	
	private class ActionHandler implements InterfaceListener<ButtonPressEvent> {
		
		private boolean accept;
		
		private ActionHandler(boolean accept) {
			this.accept = accept;
		}
		
		@Override
		public void onAction(ButtonPressEvent event) {
			WindowConfirm.this.action.accept(this.accept);
			InterfaceAPI.getSetup(WindowConfirm.this.getUser()).close(WindowConfirm.this);
		}
		
	}
	
}
