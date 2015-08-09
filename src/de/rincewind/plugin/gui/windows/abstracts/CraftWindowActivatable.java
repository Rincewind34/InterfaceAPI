package de.rincewind.plugin.gui.windows.abstracts;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import de.rincewind.api.gui.windows.abstracts.WindowActivatable;

public abstract class CraftWindowActivatable extends CraftWindowColorable implements WindowActivatable {
	
	private Plugin plugin;
	
	private boolean isRunning;
	
	private int progress;
	
	private BukkitTask task;
	
	public CraftWindowActivatable(Plugin plugin) {
		this.plugin = plugin;
		this.progress = 0;
	}
	
	@Override
	public void start(long delay) {
		if (this.isRunning) {
			return;
		} else {
			this.isRunning = true;
			this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(this.getPlugin(), this.getRunnable(), 0L, delay);
		}
	}
	
	@Override
	public void stop() {
		if (this.isRunning) {
			this.isRunning = false;
			this.task.cancel();
		}
	}

	@Override
	public boolean isRunning() {
		return this.isRunning;
	}

	@Override
	public int getProgress() {
		return this.progress;
	}

	@Override
	public void setProgress(int progress) {
		this.progress = progress;
	}
	
	@Override
	public void close() {
		super.close();
		this.stop();
	}

	public Plugin getPlugin() {
		return this.plugin;
	}
	
	public abstract Runnable getRunnable();

}
