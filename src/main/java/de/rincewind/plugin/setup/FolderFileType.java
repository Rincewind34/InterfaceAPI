package de.rincewind.plugin.setup;

import java.io.File;
import java.util.function.Supplier;

import org.bukkit.Material;

import de.rincewind.api.gui.elements.util.Icon;
import de.rincewind.api.setup.FileType;

public class FolderFileType implements FileType {

	@Override
	public String getName() {
		return "FolderType";
	}

	@Override
	public boolean isMatching(File file) {
		return file.isDirectory();
	}

	@Override
	public Supplier<Icon> getIconCreator() {
		return () -> {
			return new Icon(Material.CHEST);
		};
	}

	@Override
	public void createNewFile() {
		
	}

}
