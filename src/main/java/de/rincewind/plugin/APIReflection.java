package de.rincewind.plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import lib.securebit.ReflectionUtil;

public class APIReflection {
	
	public static final Class<?> CLASS_PACKET = ReflectionUtil.getNMSClass("Packet");
	public static final Class<?> CLASS_PACKET_WINDOWDATA = ReflectionUtil.getNMSClass("PacketPlayOutWindowData");
	public static final Class<?> CLASS_CRAFTPLAYER = ReflectionUtil.getCraftBukkitClass("entity.CraftPlayer");
	public static final Class<?> CLASS_ENTITYPLAYER = ReflectionUtil.getNMSClass("EntityPlayer");
	public static final Class<?> CLASS_PLAYERCONNECTION = ReflectionUtil.getNMSClass("PlayerConnection");
	public static final Class<?> CLASS_CONTAINER = ReflectionUtil.getNMSClass("Container");
	public static final Class<?> CLASS_CRAFTITEM = ReflectionUtil.getCraftBukkitClass("inventory.CraftItemStack");
	public static final Class<?> CLASS_NBTTAG = ReflectionUtil.getNMSClass("NBTTagCompound");
	public static final Class<?> CLASS_NMSITEM = ReflectionUtil.getNMSClass("ItemStack");
	
	public static final Constructor<?> CONSTRUCTOR_PACKET_WINDOWDATA = ReflectionUtil.getConstructor(APIReflection.CLASS_PACKET_WINDOWDATA,
			new Class<?>[] { int.class, int.class, int.class });
	
	public static final Field FIELD_CONNECTION = ReflectionUtil.getDeclaredField(APIReflection.CLASS_ENTITYPLAYER, "playerConnection");
	public static final Field FIELD_ACTIVEWINDOW = ReflectionUtil.getField(APIReflection.CLASS_ENTITYPLAYER, "activeContainer");
	public static final Field FIELD_WINDOWID = ReflectionUtil.getField(APIReflection.CLASS_CONTAINER, "windowId");
	
	public static final Method METHOD_GETHANDLE = ReflectionUtil.getMethod(APIReflection.CLASS_CRAFTPLAYER, "getHandle", ReflectionUtil.emtyClassArray());
	public static final Method METHOD_NMSCOPY = ReflectionUtil.getMethod(APIReflection.CLASS_CRAFTITEM, "asNMSCopy", new Class<?>[] { ItemStack.class });
	public static final Method METHOD_BUKKITCOPY = ReflectionUtil.getMethod(APIReflection.CLASS_CRAFTITEM, "asBukkitCopy", new Class<?>[] { APIReflection.CLASS_NMSITEM });
	public static final Method METHOD_GETTAG = ReflectionUtil.getMethod(APIReflection.CLASS_NMSITEM, "getTag", ReflectionUtil.emtyClassArray());
	public static final Method METHOD_NBT_SETFLOAT = ReflectionUtil.getMethod(APIReflection.CLASS_NBTTAG, "setFloat", new Class<?>[] { String.class, float.class });
	public static final Method METHOD_NBT_GETFLOAT = ReflectionUtil.getMethod(APIReflection.CLASS_NBTTAG, "getFloat", new Class<?>[] { String.class });
	public static final Method METHOD_NBT_SETDOUBLE = ReflectionUtil.getMethod(APIReflection.CLASS_NBTTAG, "setDouble", new Class<?>[] { String.class, double.class });
	public static final Method METHOD_NBT_GETDOUBLE = ReflectionUtil.getMethod(APIReflection.CLASS_NBTTAG, "getDouble", new Class<?>[] { String.class });
	public static final Method METHOD_NBT_SETSTRING = ReflectionUtil.getMethod(APIReflection.CLASS_NBTTAG, "setString", new Class<?>[] { String.class, String.class });
	public static final Method METHOD_NBT_GETSTRING = ReflectionUtil.getMethod(APIReflection.CLASS_NBTTAG, "getString", new Class<?>[] { String.class });
	public static final Method METHOD_NBT_SETBOOLEAN = ReflectionUtil.getMethod(APIReflection.CLASS_NBTTAG, "setBoolean", new Class<?>[] { String.class, boolean.class });
	public static final Method METHOD_NBT_GETBOOLEAN = ReflectionUtil.getMethod(APIReflection.CLASS_NBTTAG, "getBoolean", new Class<?>[] { String.class });
	public static final Method METHOD_NBT_SETBYTE = ReflectionUtil.getMethod(APIReflection.CLASS_NBTTAG, "setByte", new Class<?>[] { String.class, byte.class });
	public static final Method METHOD_NBT_GETBYTE = ReflectionUtil.getMethod(APIReflection.CLASS_NBTTAG, "getByte", new Class<?>[] { String.class });
	public static final Method METHOD_NBT_SETSHORT = ReflectionUtil.getMethod(APIReflection.CLASS_NBTTAG, "setShort", new Class<?>[] { String.class, short.class });
	public static final Method METHOD_NBT_GETSHORT = ReflectionUtil.getMethod(APIReflection.CLASS_NBTTAG, "getShort", new Class<?>[] { String.class });
	public static final Method METHOD_NBT_SETINT = ReflectionUtil.getMethod(APIReflection.CLASS_NBTTAG, "setInt", new Class<?>[] { String.class, int.class });
	public static final Method METHOD_NBT_GETINT = ReflectionUtil.getMethod(APIReflection.CLASS_NBTTAG, "getInt", new Class<?>[] { String.class });
	public static final Method METHOD_NBT_SETLONG = ReflectionUtil.getMethod(APIReflection.CLASS_NBTTAG, "setLong", new Class<?>[] { String.class, long.class });
	public static final Method METHOD_NBT_GETLONG = ReflectionUtil.getMethod(APIReflection.CLASS_NBTTAG, "getLong", new Class<?>[] { String.class });
	public static final Method METHOD_NBT_HASKEY = ReflectionUtil.getMethod(APIReflection.CLASS_NBTTAG, "hasKey", new Class<?>[] { String.class });
	public static final Method METHOD_SETTAG = ReflectionUtil.getMethod(APIReflection.CLASS_NMSITEM, "setTag",
			new Class<?>[] { APIReflection.CLASS_NBTTAG });
	public static final Method METHOD_SENDPACKET = ReflectionUtil.getMethod(APIReflection.CLASS_PLAYERCONNECTION, "sendPacket",
			new Class<?>[] { APIReflection.CLASS_PACKET });
	
	public static void sendPacket(Player player, Object packet) {
		Object nmsPlayer = ReflectionUtil.createObject(APIReflection.METHOD_GETHANDLE, player, ReflectionUtil.emtyObjectArray());
		Object playerCon = ReflectionUtil.createObject(APIReflection.FIELD_CONNECTION, nmsPlayer);
		
		ReflectionUtil.invokeMethod(APIReflection.METHOD_SENDPACKET, playerCon, packet);
	}
	
	public static void clearRecipes(String className) {
		Class<?> classRecipes = ReflectionUtil.getNMSClass(className);
		Method methodInstance = ReflectionUtil.getMethod(classRecipes, "getInstance", ReflectionUtil.emtyClassArray());
		Field fieldRecipes = ReflectionUtil.getField(classRecipes, "recipes");
		List<?> list = (List<?>) ReflectionUtil.createObject(fieldRecipes, ReflectionUtil.createStaticObject(methodInstance));
		list.clear();
	}
}
