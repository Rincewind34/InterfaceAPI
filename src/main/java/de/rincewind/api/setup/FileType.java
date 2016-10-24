package de.rincewind.api.setup;

import java.io.File;
import java.util.function.Supplier;

import de.rincewind.api.gui.elements.util.Icon;

public interface FileType {
	
	public abstract String getName();
	
	public abstract boolean isMatching(File file);
	
	public abstract Supplier<Icon> getIconCreator();
	
	public abstract void createNewFile();
	
}
