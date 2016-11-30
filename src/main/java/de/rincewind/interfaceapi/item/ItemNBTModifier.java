package de.rincewind.interfaceapi.item;

import org.bukkit.inventory.ItemStack;

import de.rincewind.interfaceplugin.APIReflection;
import de.rincewind.interfaceplugin.ReflectionUtil;

public class ItemNBTModifier {
	
	private Object nmsItemStack;
	private Object nbtTagCompound;
	
	public ItemNBTModifier(ItemStack itemStack) {
		this.nmsItemStack = ReflectionUtil.createStaticObject(APIReflection.METHOD_NMSCOPY, itemStack);
		this.nbtTagCompound = ReflectionUtil.createObject(APIReflection.METHOD_GETTAG, this.nmsItemStack);
		
		if(this.nbtTagCompound == null) {
			this.nbtTagCompound = ReflectionUtil.createObject(APIReflection.CLASS_NBTTAG);
		}
	}
	
	public ItemNBTModifier setString(String key, String value) {
		ReflectionUtil.invokeMethod(APIReflection.METHOD_NBT_SETSTRING, this.nbtTagCompound, key, value);
		return this;
	}
	
	public ItemNBTModifier setInteger(String key, Integer value) {
		ReflectionUtil.invokeMethod(APIReflection.METHOD_NBT_SETINT, this.nbtTagCompound, key, value);
		return this;
	}
	
	public ItemNBTModifier setDouble(String key, Double value) {
		ReflectionUtil.invokeMethod(APIReflection.METHOD_NBT_SETDOUBLE, this.nbtTagCompound, key, value);
		return this;
	}
	
	public ItemNBTModifier setBoolean(String key, Boolean value) {
		ReflectionUtil.invokeMethod(APIReflection.METHOD_NBT_SETBOOLEAN, this.nbtTagCompound, key, value);
		return this;
	}
	
	public ItemNBTModifier setByte(String key, Byte value) {
		ReflectionUtil.invokeMethod(APIReflection.METHOD_NBT_SETBYTE, this.nbtTagCompound, key, value);
		return this;
	}
	
	public ItemNBTModifier setFloat(String key, Float value) {
		ReflectionUtil.invokeMethod(APIReflection.METHOD_NBT_SETFLOAT, this.nbtTagCompound, key, value);
		return this;
	}
	
	public ItemNBTModifier setLong(String key, Long value) {
		ReflectionUtil.invokeMethod(APIReflection.METHOD_NBT_SETLONG, this.nbtTagCompound, key, value);
		return this;
	}
	
	public ItemNBTModifier setShort(String key, Short value) {
		ReflectionUtil.invokeMethod(APIReflection.METHOD_NBT_SETSHORT, this.nbtTagCompound, key, value);
		return this;
	}
	
	public ItemStack modify() {
		ReflectionUtil.invokeMethod(APIReflection.METHOD_SETTAG, this.nmsItemStack, this.nbtTagCompound);
		return (ItemStack) ReflectionUtil.createStaticObject(APIReflection.METHOD_BUKKITCOPY, this.nmsItemStack);
	}
	
}
