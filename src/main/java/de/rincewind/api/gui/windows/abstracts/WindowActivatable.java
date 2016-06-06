package de.rincewind.api.gui.windows.abstracts;

import de.rincewind.api.gui.windows.WindowBrewing;
import de.rincewind.api.gui.windows.WindowFurnace;

/**
 * This window can be activated, for example the bubbles in the
 * brewer or the flames in the furnace. To set the flames or the bubbles
 * you have to use the progress of the windows.
 * If the window is not opened, the progress will still be changed.
 * The task will be stopped, if the window gets closed.
 * 
 * @author Rincewind34
 * @since 2.3.3
 * 
 * @see WindowFurnace
 * @see WindowBrewing
 */
public abstract interface WindowActivatable extends WindowColorable {
	
	/**
	 * Returns <code>true</code> if the task is running and
	 * <code>false</code> if not.
	 * 
	 * @return <code>true</code> if the task is running and
	 * 			<code>false</code> if not
	 */
	public abstract boolean isRunning();
	
	/**
	 * Starts the task with a specified delay between the changing of the
	 * progress. If the task already runs, this method will do nothing.
	 * 
	 * @param delay specified
	 */
	public abstract void start(long delay);
	
	/**
	 * Stops the task. If the task is not running, this method will do
	 * nothing.
	 */
	public abstract void stop();
	
	/**
	 * Sets the progress. If the task is running, the progress will still be
	 * set. If the progress is smaller than 0, the progress will be automatically
	 * set to 0.
	 * 
	 * @param progress to set
	 */
	public abstract void setProgress(int progress);
	
	/**
	 * Returns the currently set progress.
	 * 
	 * @return the currently set progress
	 */
	public abstract int getProgress();
	
}
