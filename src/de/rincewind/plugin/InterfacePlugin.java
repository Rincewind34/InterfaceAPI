package de.rincewind.plugin;

import lib.securebit.ReflectionUtil;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

import de.rincewind.api.gui.WindowManager;
import de.rincewind.api.gui.elements.Element;
import de.rincewind.api.gui.elements.ElementBrick;
import de.rincewind.api.gui.elements.ElementButton;
import de.rincewind.api.gui.elements.ElementCounter;
import de.rincewind.api.gui.elements.ElementList;
import de.rincewind.api.gui.elements.ElementManager;
import de.rincewind.api.gui.elements.ElementManager.ElementActivation;
import de.rincewind.api.gui.elements.ElementSwitcher;
import de.rincewind.api.recipes.RecipeManager;
import de.rincewind.api.recipes.RecipePacket;
import de.rincewind.defaults.listener.InventoryClickListener;
import de.rincewind.defaults.listener.InventoryCloseListener;
import de.rincewind.plugin.gui.CraftWindowManager;
import de.rincewind.plugin.recipes.CraftRecipeManager;
import de.rincewind.plugin.recipes.CraftRecipePacket;
import de.rincewind.util.item.categorys.Categorys;

public class InterfacePlugin extends JavaPlugin {
	
	private static WindowManager windowManager;
	
	@Override
	public void onEnable() {
		InterfacePlugin.windowManager = new CraftWindowManager(this);
		
		new Categorys();
		
		this.getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
		this.getServer().getPluginManager().registerEvents(new InventoryCloseListener(), this);
		
		ElementManager.registerElement(ElementButton.class, new ElementActivation() {
			
			@Override
			public boolean run(Element element, InventoryClickEvent event) {
				ElementButton button = (ElementButton) element;
				if(!element.isEnabled()) return event.isCancelled();
				else { 
					button.press(event);
					return event.isCancelled();
				}
			}
		});
		
		ElementManager.registerElement(ElementSwitcher.class, ElementManager.getActivation(ElementButton.class));
		ElementManager.registerElement(ElementCounter.class, ElementManager.getActivation(ElementSwitcher.class));
		ElementManager.registerElement(ElementList.class, ElementManager.getActivation(ElementButton.class));
		ElementManager.registerElement(ElementBrick.class, ElementManager.getActivation(ElementButton.class));
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public static final class InterfaceAPI {
		
		/**
		 * 
		 * @return The default windowmanager
		 */
		public static WindowManager getWindowManager() {
			return InterfacePlugin.windowManager;
		}
		
		/**
		 * 
		 * @return A new RecipeManager
		 */
		public static RecipeManager createRecipeManager() {
			return new CraftRecipeManager();
		}
		
		/**
		 * 
		 * @param player The target player
		 * @return Gets the NMS-WindowID of the activecontainer of the given player
		 */
		public static int getActiveWindowId(Player player) {
			Object nmsPlayer = ReflectionUtil.createObject(APIReflection.METHOD_GETHANDLE, player, ReflectionUtil.emtyObjectArray());
			Object container = ReflectionUtil.createObject(APIReflection.FIELD_ACTIVEWINDOW, nmsPlayer);
			
			return (int) ReflectionUtil.createObject(APIReflection.FIELD_WINDOWID, container);
		}
		
		/**
		 * 
		 * @return A new RecipePacket
		 */
		public static RecipePacket createRecipePacket() {
			return new CraftRecipePacket();
		}
		
	}
	
}