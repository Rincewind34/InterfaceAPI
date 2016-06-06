package de.rincewind.api.setup;

import java.io.File;

public interface Program {
	
	public abstract String getName();
	
	public abstract void execute(File file);
	
}
