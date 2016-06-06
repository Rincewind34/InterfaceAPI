package de.rincewind.api.setup;

import java.io.File;

import de.rincewind.api.item.IconCreator;

public interface FileType {
	
	public abstract String getName();
	
	public abstract boolean isMatching(File file);
	
	public abstract IconCreator getIconCreator();
	
	public abstract void createNewFile();
	
}
