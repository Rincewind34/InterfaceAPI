package de.rincewind.plugin;

import org.bukkit.Material;

import de.rincewind.api.gui.elements.ElementButton;
import de.rincewind.api.gui.elements.ElementMultiButton;
import de.rincewind.api.gui.elements.util.Icon;
import de.rincewind.api.gui.elements.util.Point;
import de.rincewind.api.gui.util.Color;
import de.rincewind.api.gui.windows.util.Windows.WindowSizeableExtendable;
import de.rincewind.api.handling.events.ButtonPressEvent;
import de.rincewind.api.handling.events.MultiButtonPressEvent;
import de.rincewind.api.handling.listener.ButtonPressListener;
import de.rincewind.api.handling.listener.MultiButtonPressListener;
import de.rincewind.plugin.gui.elements.CraftElementMultiButton;

public class TestWindow extends WindowSizeableExtendable {
	
	public TestWindow() {
		super.setName("Test");
		super.setSize(9, 6);
		super.setColor(Color.BLACK);
		
		ElementMultiButton multiButton = new CraftElementMultiButton(this);
		this.addElement(multiButton);
		multiButton.setIcon(new Icon(Material.WOOL, 3, "§eMultiButton"));
		multiButton.setSize(3, 3);
		multiButton.addSwitch("Test1");
		multiButton.addSwitch("Test2");
		multiButton.addSwitch("Test3");
		multiButton.getEventManager().registerListener(new MultiButtonPressListener() {
			
			@Override
			public void onFire(MultiButtonPressEvent event) {
				System.out.println(event.getElement().getSwitch());
			}
		}).addAfter();
		
		ElementButton button2 = this.elementCreator().newButton();
		button2.setIcon(new Icon(Material.STAINED_GLASS, 4, "TestButtonName2"));
		button2.setPoint(new Point(4, 2));
		button2.getEventManager().registerListener(new ButtonPressListener() {
			
			@Override
			public void onFire(ButtonPressEvent event) {
				System.out.println("ButtonPressEvent fired (TestWindow): Button2");
				
				if (!multiButton.isSelected()) {
					multiButton.select();
				} else {
					multiButton.unselect();
				}
			}
		}).addAfter();
		button2.setSize(2, 2);
	}
	
}