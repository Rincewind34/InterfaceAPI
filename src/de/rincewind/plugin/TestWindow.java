package de.rincewind.plugin;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.rincewind.api.gui.Color;
import de.rincewind.api.gui.components.Locatable.Point;
import de.rincewind.api.gui.elements.ElementOutput;
import de.rincewind.api.gui.windows.WindowSizeable;
import de.rincewind.plugin.gui.elements.CraftElementOutput;

public class TestWindow extends WindowSizeable {
	
	public TestWindow() {
		super.setName("Tets");
		super.setSize(9, 6);
		super.setColor(Color.BLACK);
		
		ElementOutput output = new CraftElementOutput(this);
		output.setPoint(new Point(1, 3));
		output.output(new ItemStack(Material.APPLE));
		
		super.addElement(output);
	}
	
	
}
