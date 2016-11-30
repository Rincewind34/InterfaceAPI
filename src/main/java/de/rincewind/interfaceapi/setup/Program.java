package de.rincewind.interfaceapi.setup;

import java.io.File;

public interface Program {
	
	public abstract String getName();
	
	public abstract void execute(File file);
	
}
