package de.rincewind.api.item;

import lib.securebit.ReflectionUtil;

import org.bukkit.inventory.ItemStack;

import de.rincewind.api.item.categorys.Categorys;
import de.rincewind.plugin.APIReflection;

public class ScanResult {
	
	private ItemStack item;
	
	private Object nbtTagCompound;
	
	public ScanResult(ItemStack item) {
		this.item = item;
		
		this.nbtTagCompound = ReflectionUtil.createObject(APIReflection.METHOD_GETTAG, ReflectionUtil.createStaticObject(APIReflection.METHOD_NMSCOPY, this.item));
		
		if(this.nbtTagCompound == null) {
			this.nbtTagCompound = ReflectionUtil.createObject(APIReflection.CLASS_NBTTAG);
		}

	}
	
	public ItemStack getResult() {
		return this.item;
	}
	
	public boolean isInCategory(String key) {
		if (!Categorys.containsKey(key)) {
			return false;
		} else {
			return Categorys.get(key).containsMaterial(item.getData());
		}
	}
	
	public double getDouble(String key) {
		return (double) ReflectionUtil.createObject(APIReflection.METHOD_NBT_GETDOUBLE, this.nbtTagCompound, key);
	}
	
	public float getFloat(String key) {
		return (float) ReflectionUtil.createObject(APIReflection.METHOD_NBT_GETFLOAT, this.nbtTagCompound, key);
	}
	
	public String getString(String key) {
		return (String) ReflectionUtil.createObject(APIReflection.METHOD_NBT_GETSTRING, this.nbtTagCompound, key);
	}
	
	public int getInteger(String key) {
		return (int) ReflectionUtil.createObject(APIReflection.METHOD_NBT_GETINT, this.nbtTagCompound, key);
	}
	
	public boolean getBoolean(String key) {
		return (boolean) ReflectionUtil.createObject(APIReflection.METHOD_NBT_GETBOOLEAN, this.nbtTagCompound, key);
	}
	
	public boolean hasKey(String key) {
		return (boolean) ReflectionUtil.createObject(APIReflection.METHOD_NBT_HASKEY, this.nbtTagCompound, key);
	}
	
	public byte getByte(String key) {
		return (byte) ReflectionUtil.createObject(APIReflection.METHOD_NBT_GETBYTE, this.nbtTagCompound, key);
	}
	
	public short getShort(String key) {
		return (short) ReflectionUtil.createObject(APIReflection.METHOD_NBT_GETSHORT, this.nbtTagCompound, key);
	}
	
	public long getLong(String key) {
		return (long) ReflectionUtil.createObject(APIReflection.METHOD_NBT_GETLONG, this.nbtTagCompound, key);
	}

	
}
