package de.rincewind.plugin;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.rincewind.api.events.ButtonPressEvent;
import de.rincewind.api.gui.Color;
import de.rincewind.api.gui.components.Locatable.Point;
import de.rincewind.api.gui.elements.ElementButton;
import de.rincewind.api.listener.ButtonPressListener;
import de.rincewind.plugin.gui.windows.CraftWindowSizeable;

public class TestWindow extends CraftWindowSizeable {
	
	public TestWindow() {
		super.setName("Tets");
		super.setSize(9, 6);
		super.setColor(Color.BLACK);
		
		ElementButton btnDisplay = this.elementCreator().newButton();
		btnDisplay.setIcon(new ItemStack(Material.STAINED_GLASS, 1, (byte) 0));
		btnDisplay.setPoint(new Point(3, 1));
		btnDisplay.getEventManager().addListener(new ButtonPressListener() {
			
			@Override
			public void onFire(ButtonPressEvent event) {
				System.out.println("ButtonPressEvent fired (TestWindow)");
			}
		});
		
//		this.addElement(btnDisplay);
	}
	
	
}
