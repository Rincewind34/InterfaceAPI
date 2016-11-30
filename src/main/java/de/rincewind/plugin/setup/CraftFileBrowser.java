package de.rincewind.plugin.setup;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.rincewind.api.exceptions.APIException;
import de.rincewind.api.gui.windows.WindowFileBrowser;
import de.rincewind.api.setup.FileBrowser;
import de.rincewind.api.setup.FileType;
import de.rincewind.api.setup.Program;
import de.rincewind.api.setup.Setup;

@Deprecated
public class CraftFileBrowser implements FileBrowser {
	
	private Setup setup;
	private File rootdir;
	private File matchingdir;
	private File selected;
	
	private List<FileType> types;
	
	private Map<String, List<Program>> programs;
	
	public CraftFileBrowser(Setup setup, File rootdir) {
		this.setup = setup;
		this.rootdir = rootdir;
		this.matchingdir = rootdir;
		this.types = new ArrayList<>();
		this.programs = new HashMap<>();
	}
	
	@Override
	public Setup getSetup() {
		return this.setup;
	}
	
	@Override
	public WindowFileBrowser openWindow() {
//		WindowFileBrowser window = new WindowFileBrowser(this);
//		this.setup.open(window);
//		return window;
		
		return null;
	}
	
	@Override
	public File getRootDirectory() {
		return this.rootdir;
	}
	
	@Override
	public File getMatchingDirectory() {
		return this.matchingdir;
	}
	
	@Override
	public void registerFileType(FileType type) {
		if (this.types.contains(type)) {
			return;
		}
		
		this.types.add(type);
		this.programs.put(type.getName(), new ArrayList<>());
	}
	
	@Override
	public List<FileType> getTypes() {
		return Collections.unmodifiableList(this.types);
	}

	@Override
	public void registerProgram(String type, Program program) {
		this.programs.get(type).add(program);
	}

	@Override
	public void navigate(String name) {
		this.navigateAbsolute(new File(this.matchingdir, name).getAbsolutePath());
	}
	
	@Override
	public void navigateAbsolute(String name) {
		File file = new File(name);
		
		if (file.getAbsolutePath().contains(this.rootdir.getAbsolutePath())) {
			if (file.exists()) {
				if (file.isDirectory()) {
					this.matchingdir = file;
					this.select(null);
				} else {
					throw new APIException("The file has to be a directory!");
				}
			} else {
				throw new APIException("File not found!");
			}
		} else {
			throw new APIException("This directory is out of range!");
		}
	}
	
	@Override
	public void navigateUp() {
		if (this.isTopDirectory()) {
			throw new APIException("Already in the rootdirectory!");
		} else {
			this.matchingdir = this.matchingdir.getParentFile();
			this.select(null);
		}
	}

	@Override
	public File getSelectedFile() {
		return this.selected;
	}

	@Override
	public void select(String name) {
		if (name == null) {
			this.selected = null;
			return;
		}
		
		File file = new File(this.matchingdir, name);
		
		if (file.exists()) {
			this.selected = file;
		} else {
			throw new APIException("File not found!");
		}
	}

	@Override
	public List<Program> getPrograms(String type) {
		return Collections.unmodifiableList(this.programs.get(type));
	}

	@Override
	public boolean isTopDirectory() {
		return this.rootdir.getAbsolutePath().equals(this.matchingdir.getAbsolutePath());
	}
	
}
