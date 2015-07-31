package de.rincewind.util.item;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.inventory.ItemStack;

public class ItemLibary {
	
	private static Class<? extends ScanResult> scanResultClass = ScanResult.class;
	
	/**
	 * 
	 * @return The instanced and saved class (which extends ScanResult)
	 */
	public static Class<? extends ScanResult> getScanResultClass() {
		return scanResultClass;
	}
	
	/**
	 * 
	 * @param clazz The class, which will be instanced and saved
	 */
	public static void setScanResultClass(Class<? extends ScanResult> clazz) {
		if (clazz == null) {
			return;
		} else {
			ItemLibary.scanResultClass = clazz;
		}
	}
	
	@SuppressWarnings("finally")
	/**
	 * 
	 * @param item The item to scan
	 * @return A scanresult depend on the given item
	 */
	public static ScanResult scanItem(ItemStack item) {
		if (item == null) {
			return null;
		} else {
			try {
				Constructor<?> c = scanResultClass.getConstructor(new Class<?>[] {ItemStack.class});
				ScanResult result = (ScanResult) c.newInstance(new Object[] {item});
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
			} finally {
				return new ScanResult(item);
			}
		}
	}
	
	/**
	 * 
	 * @return The ItemRefactor
	 */
	public static ItemRefactor refactor() {
		return new ItemRefactor();
	}
	
	/**
	 * 
	 * @return The ItemCreator
	 */
	public static ItemCreator creator() {
		return new ItemCreator();
	}
	
	/**
	 * 
	 * @return The ItemSerializer
	 */
	public static ItemSerializer serialier() {
		return new ItemSerializer();
	}
	
}
