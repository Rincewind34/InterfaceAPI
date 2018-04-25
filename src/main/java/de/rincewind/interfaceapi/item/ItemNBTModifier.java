package de.rincewind.interfaceapi.item;

import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_12_R1.NBTTagCompound;

@Deprecated
public class ItemNBTModifier {
	
	private net.minecraft.server.v1_12_R1.ItemStack nmsItemStack;
	private NBTTagCompound nbtTagCompound;
	
	public ItemNBTModifier(ItemStack itemStack) {
		this.nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
		this.nbtTagCompound = this.nmsItemStack.getTag();
		
		if(this.nbtTagCompound == null) {
			this.nbtTagCompound = new NBTTagCompound();
		}
	}
	
	public ItemNBTModifier setString(String key, String value) {
		this.nbtTagCompound.setString(key, value);
		return this;
	}
	
	public ItemNBTModifier setInteger(String key, Integer value) {
		this.nbtTagCompound.setInt(key, value);
		return this;
	}
	
	public ItemNBTModifier setDouble(String key, Double value) {
		this.nbtTagCompound.setDouble(key, value);
		return this;
	}
	
	public ItemNBTModifier setBoolean(String key, Boolean value) {
		this.nbtTagCompound.setBoolean(key, value);
		return this;
	}
	
	public ItemNBTModifier setByte(String key, Byte value) {
		this.nbtTagCompound.setByte(key, value);
		return this;
	}
	
	public ItemNBTModifier setFloat(String key, Float value) {
		this.nbtTagCompound.setFloat(key, value);
		return this;
	}
	
	public ItemNBTModifier setLong(String key, Long value) {
		this.nbtTagCompound.setLong(key, value);
		return this;
	}
	
	public ItemNBTModifier setShort(String key, Short value) {
		this.nbtTagCompound.setShort(key, value);
		return this;
	}
	
	public ItemStack modify() {
		this.nmsItemStack.setTag(this.nbtTagCompound);
		return CraftItemStack.asBukkitCopy(this.nmsItemStack);
	}
	
}
