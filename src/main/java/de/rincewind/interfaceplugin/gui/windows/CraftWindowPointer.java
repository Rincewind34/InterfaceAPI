package de.rincewind.interfaceplugin.gui.windows;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import de.rincewind.interfaceapi.InterfaceAPI;
import de.rincewind.interfaceapi.gui.elements.ElementItem;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.util.Point;
import de.rincewind.interfaceapi.gui.windows.WindowPointer;
import de.rincewind.interfaceapi.handling.InterfaceListener;
import de.rincewind.interfaceapi.handling.element.ElementInteractEvent;

public class CraftWindowPointer extends CraftWindowSizeable implements WindowPointer {

	private Consumer<Point> action;

	private List<Point> blocked;

	public CraftWindowPointer(Plugin plugin, Consumer<Point> action, int width, int height, String name, List<Point> blocked) {
		super(plugin);
		
		this.action = action;
		this.blocked = blocked;

		this.setSize(width, height);
		this.setName(name);
		this.createButtons();
	}

	@Override
	public Consumer<Point> getAction() {
		return this.action;
	}

	@Override
	public void invert() {
		List<Point> inverted = new ArrayList<>();

		this.iterate(point -> {
			for (Point target : this.blocked) {
				if (target.equals(point)) {
					inverted.add(point);
					break;
				}
			}
		});

		this.blocked = inverted;
		this.clearElements();
		this.createButtons();
	}

	private void createButtons() {
		this.iterate((point) -> {
			for (Point target : this.blocked) {
				if (target.equals(point)) {
					ElementItem element = this.elementCreator().newItem();
					element.setPoint(point);
					element.setIcon(new Icon(Material.STAINED_GLASS_PANE, 14));
					return;
				}
			}

			ElementItem element = this.elementCreator().newItem();
			element.setPoint(point);
			element.setIcon(new Icon(Material.STAINED_GLASS_PANE, 13));
			element.getEventManager().registerListener(ElementInteractEvent.class, new ActionHandler()).addAfter();
		});
	}

	private class ActionHandler implements InterfaceListener<ElementInteractEvent> {

		@Override
		public void onAction(ElementInteractEvent event) {
			CraftWindowPointer.this.action.accept(event.getElement().getPoint());
			InterfaceAPI.getSetup(CraftWindowPointer.this.getUser()).close(CraftWindowPointer.this);
		}

	}

}
