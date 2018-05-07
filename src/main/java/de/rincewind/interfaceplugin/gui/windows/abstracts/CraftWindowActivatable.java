package de.rincewind.interfaceplugin.gui.windows.abstracts;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import de.rincewind.interfaceapi.gui.windows.abstracts.WindowActivatable;
import de.rincewind.interfaceapi.gui.windows.util.WindowState;
import de.rincewind.interfaceapi.handling.window.WindowChangeStateEvent;

public abstract class CraftWindowActivatable extends CraftWindowColorable implements WindowActivatable {
	
	private boolean isRunning;
	
	private int progress;
	
	private BukkitTask task;
	
	public CraftWindowActivatable(Plugin plugin) {
		super(plugin);
		
		this.progress = 0;
		
		this.getEventManager().registerListener(WindowChangeStateEvent.class, (event) -> {
			if (event.getNewState() == WindowState.CLOSED) {
				this.stop();
			}
		}).addBefore();
	}
	
	@Override
	public void start(long delay) {
		if (this.isRunning) {
			return;
		} else {
			this.isRunning = true;
			this.task = Bukkit.getScheduler().runTaskTimer(this.getPlugin(), this.getRunnable(), 0L, delay);
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
	
	public abstract Runnable getRunnable();

}
