package de.rincewind.interfaceapi.item;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.inventory.ItemStack;

public class ItemLibrary {
	
	public static <T extends ScanResult> T scanItem(ItemStack item, Class<T> clazz) {
		if (item == null) {
			return null;
		} else {
			try {
				Constructor<T> c = clazz.getConstructor(new Class<?>[] { ItemStack.class });
				T result = (T) c.newInstance(new Object[] { item });
				return result;
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			
			return null;
		}
	}
	
	public static ItemRefactor refactor() {
		return new ItemRefactor();
	}
	
	public static ItemSerializer serialier() {
		return new ItemSerializer();
	}
	
	@Deprecated
	public static ItemNBTModifier nbtModifier(ItemStack item) {
		return new ItemNBTModifier(item);
	}
	
}
