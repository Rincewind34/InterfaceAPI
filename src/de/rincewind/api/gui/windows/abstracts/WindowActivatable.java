package de.rincewind.api.gui.windows.abstracts;

import org.bukkit.plugin.Plugin;

public abstract class WindowActivatable extends WindowColorable {
	
	private Plugin plugin;
	
	private boolean isRunning;
	
	private int progress;
	
	/**
	 * @param plugin An instance of a Plugin-main-class
	 */
	public WindowActivatable(Plugin plugin) {
		this.plugin = plugin;
		this.progress = 0;
	}
	
	/**
	 * Starts the animations in a window
	 * @param delay The delay between two animation-steps
	 */
	public abstract void start(long delay);
	
	/**
	 * Stops the animations in a window
	 */
	public abstract void stop();
	
	/**
	 * @return If the animations in this window are currently running
	 */
	public boolean isRunning() {
		return this.isRunning;
	}
	
	/**
	 * @return The progress value of the running animation
	 */
	public int getProgress() {
		return this.progress;
	}
	
	/**
	 * Set the progress value for the animation in this window
	 * @param progress The new progress value
	 */
	public void setProgress(int progress) {
		this.progress = progress;
	}
	
	/**
	 * Start/Stop the animations in this window
	 * @param running True/False for the new status
	 */
	protected void setRunning(boolean running) {
		this.isRunning = running;
	}
	
	/**
	 * @return The defined Plugin-main-class instance for this window
	 */
	protected Plugin getPlugin() {
		return this.plugin;
	}
	
}
