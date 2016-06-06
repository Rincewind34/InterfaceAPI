package de.rincewind.plugin;

import java.util.HashMap;
import java.util.Map;

import de.rincewind.api.gui.windows.abstracts.WindowContainer;

public class GUIHandler implements Runnable {
	
	private long delay;
	
	private Map<WindowContainer, Runnable> windows;
	
	private Thread thread;
	
	private boolean running;
	
	public GUIHandler() {
		this.delay = 20L;
		this.running = true;
		this.windows = new HashMap<>();
		this.thread = new Thread(this);
	}
	
	public void start() {
		this.thread.start();
	}
	
	public void stop() {
		this.running = false;
	}

	@Override
	public void run() {
		while (this.running) {
			for (WindowContainer window : this.windows.keySet()) {
				Runnable runnable = this.windows.get(window);
				runnable.run();
			}
			
			try {
				Thread.sleep(this.delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
