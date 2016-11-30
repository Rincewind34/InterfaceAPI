package de.rincewind.interfaceplugin.gui.windows;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.bukkit.Material;

import de.rincewind.interfaceapi.InterfaceAPI;
import de.rincewind.interfaceapi.gui.elements.ElementButton;
import de.rincewind.interfaceapi.gui.elements.ElementItem;
import de.rincewind.interfaceapi.gui.elements.util.Icon;
import de.rincewind.interfaceapi.gui.elements.util.Point;
import de.rincewind.interfaceapi.gui.windows.WindowPointer;
import de.rincewind.interfaceapi.handling.InterfaceListener;
import de.rincewind.interfaceapi.handling.element.ButtonPressEvent;

public class CraftWindowPointer extends CraftWindowSizeable implements WindowPointer {

	private Consumer<Point> action;
	
	private List<Point> blocked;
	
	public CraftWindowPointer(Consumer<Point> action, int width, int height, String name, List<Point> blocked) {
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
				if (target.isSimilar(point)) {
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
			for (Point target : blocked) {
				if (target.isSimilar(point)) {
					ElementItem element = this.elementCreator().newItem();
					element.setPoint(point);
					element.setIcon(new Icon(Material.STAINED_GLASS_PANE, 14));
					return;
				}
			}
			
			ElementButton element = this.elementCreator().newButton();
			element.setPoint(point);
			element.setIcon(new Icon(Material.STAINED_GLASS_PANE, 13));
			element.getEventManager().registerListener(ButtonPressEvent.class, new ActionHandler()).addAfter();
		});
	}
	
	
	private class ActionHandler implements InterfaceListener<ButtonPressEvent> {
		
		@Override
		public void onAction(ButtonPressEvent event) {
			CraftWindowPointer.this.action.accept(event.getElement().getPoint());
			InterfaceAPI.getSetup(getUser()).close(CraftWindowPointer.this);
		}
		
	}
	
}
