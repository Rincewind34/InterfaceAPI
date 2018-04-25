package de.rincewind.interfaceapi.item;

import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import de.rincewind.interfaceapi.item.categorys.Categorys;
import net.minecraft.server.v1_12_R1.NBTTagCompound;

public class ScanResult {
	
	private ItemStack item;
	
	private NBTTagCompound nbtTagCompound;
	
	public ScanResult(ItemStack item) {
		this.item = item;
		
		this.nbtTagCompound = CraftItemStack.asNMSCopy(item).getTag();
		
		if (this.nbtTagCompound == null) {
			this.nbtTagCompound = new NBTTagCompound();
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
		return this.nbtTagCompound.getDouble(key);
	}
	
	public float getFloat(String key) {
		return this.nbtTagCompound.getFloat(key);
	}
	
	public String getString(String key) {
		return this.nbtTagCompound.getString(key);
	}
	
	public int getInteger(String key) {
		return this.nbtTagCompound.getInt(key);
	}
	
	public boolean getBoolean(String key) {
		return this.nbtTagCompound.getBoolean(key);
	}
	
	public boolean hasKey(String key) {
		return this.nbtTagCompound.hasKey(key);
	}
	
	public byte getByte(String key) {
		return this.nbtTagCompound.getByte(key);
	}
	
	public short getShort(String key) {
		return this.nbtTagCompound.getShort(key);
	}
	
	public long getLong(String key) {
		return this.nbtTagCompound.getLong(key);
	}
	
}
