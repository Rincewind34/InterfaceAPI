package de.rincewind.interfaceapi.setup;

import java.io.File;
import java.util.List;

import de.rincewind.interfaceapi.gui.windows.WindowFileBrowser;

@Deprecated
public interface FileBrowser {
	
	public abstract Setup getSetup();
	
	public abstract WindowFileBrowser openWindow();
	
	public abstract File getRootDirectory();
	
	public abstract File getMatchingDirectory();
	
	public abstract File getSelectedFile();
	
	public abstract void navigate(String name);
	
	public abstract void navigateAbsolute(String name);
	
	public abstract void navigateUp();
	
	public abstract void registerFileType(FileType type);
	
	public abstract void registerProgram(String type, Program program);
	
	public abstract void select(String name);
	
	public abstract boolean isTopDirectory();
	
	public abstract List<FileType> getTypes();
	
	public abstract List<Program> getPrograms(String type);
	
}
