package de.rincewind.interfaceapi.gui.windows;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.bukkit.Material;

import de.rincewind.interfaceapi.InterfaceAPI;
import de.rincewind.interfaceapi.gui.elements.ElementItem;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceapi.gui.util.Color;
import de.rincewind.interfaceapi.handling.InterfaceListener;
import de.rincewind.interfaceapi.handling.element.ElementInteractEvent;
import de.rincewind.interfaceplugin.gui.windows.CraftWindowSizeable;

public class WindowConfirm extends CraftWindowSizeable {

	private Consumer<Boolean> action;

	public WindowConfirm(String name, Consumer<Boolean> action) {
		this(name, action, 5);
	}

	public WindowConfirm(String name, Consumer<Boolean> action, int width) {
		this(name, action, width, new Icon(Material.STAINED_CLAY, 13, "§aConfirm"), new Icon(Material.STAINED_CLAY, 14, "§cNegate"));
	}

	public WindowConfirm(String name, Consumer<Boolean> action, int width, Supplier<Icon> iconConfirm, Supplier<Icon> iconDeny) {
		this(name, action, width, iconConfirm.get(), iconDeny.get());
	}

	public WindowConfirm(String name, Consumer<Boolean> action, int width, Icon iconConfirm, Icon iconDeny) {
		this.setName(name);
		this.setSize(width, 1);
		this.setColor(Color.BLACK);

		this.action = action;

		ElementItem btnConfirm = this.elementCreator().newItem();
		btnConfirm.setIcon(iconConfirm);
		btnConfirm.setPoint(new Point(0, 0));
		btnConfirm.getEventManager().registerListener(ElementInteractEvent.class, this.new ActionHandler(true)).addAfter();

		ElementItem btnDeny = this.elementCreator().newItem();
		btnDeny.setIcon(iconDeny);
		btnDeny.setPoint(new Point(width - 1, 0));
		btnDeny.getEventManager().registerListener(ElementInteractEvent.class, this.new ActionHandler(true)).addAfter();
	}

	public Consumer<Boolean> getAction() {
		return this.action;
	}

	private class ActionHandler implements InterfaceListener<ElementInteractEvent> {

		private boolean accept;

		private ActionHandler(boolean accept) {
			this.accept = accept;
		}

		@Override
		public void onAction(ElementInteractEvent event) {
			if (event.isLeftClick()) {
				WindowConfirm.this.action.accept(this.accept);
				InterfaceAPI.getSetup(WindowConfirm.this.getUser()).close(WindowConfirm.this);
			}
		}

	}

}
