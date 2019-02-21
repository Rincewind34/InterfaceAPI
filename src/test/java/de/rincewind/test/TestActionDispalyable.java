package de.rincewind.test;

import org.bukkit.Material;

import de.rincewind.interfaceapi.gui.components.ActionItem;
import de.rincewind.interfaceapi.gui.components.Displayable;
import de.rincewind.interfaceapi.gui.elements.util.Icon;

public class TestActionDispalyable implements Displayable, ActionItem {

	@Override
	public void performCustomAction(Runnable executed) {
		throw new Success();
	}

	@Override
	public String getCustomActionInstructions() {
		return "Custom Action";
	}

	@Override
	public Icon getIcon() {
		return new Icon(Material.COMMAND_BLOCK);
	}

}
