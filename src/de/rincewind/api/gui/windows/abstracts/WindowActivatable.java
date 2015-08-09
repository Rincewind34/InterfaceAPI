package de.rincewind.api.gui.windows.abstracts;

public abstract interface WindowActivatable extends WindowColorable {
	
	public abstract boolean isRunning();
	
	public abstract void start(long delay);
	
	public abstract void stop();
	
	public abstract void setProgress(int progress);
	
	public abstract int getProgress();
	
}
