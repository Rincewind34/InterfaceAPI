package de.rincewind.plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import lib.securebit.ReflectionUtil;

import org.bukkit.entity.Player;

public class APIReflection {
	
	public static final Class<?> CLASS_PACKET = ReflectionUtil.getNMSClass("Packet");
	public static final Class<?> CLASS_PACKET_WINDOWDATA = ReflectionUtil.getNMSClass("PacketPlayOutWindowData");
	public static final Class<?> CLASS_CRAFTPLAYER = ReflectionUtil.getCraftBukkitClass("entity.CraftPlayer");
	public static final Class<?> CLASS_ENTITYPLAYER = ReflectionUtil.getNMSClass("EntityPlayer");
	public static final Class<?> CLASS_PLAYERCONNECTION = ReflectionUtil.getNMSClass("PlayerConnection");
	public static final Class<?> CLASS_CONTAINER = ReflectionUtil.getNMSClass("Container");
	
	public static final Constructor<?> CONSTRUCTOR_PACKET_WINDOWDATA = ReflectionUtil.getConstructor(APIReflection.CLASS_PACKET_WINDOWDATA,
			new Class<?>[] { int.class, int.class, int.class });
	
	public static final Field FIELD_CONNECTION = ReflectionUtil.getDeclaredField(APIReflection.CLASS_ENTITYPLAYER, "playerConnection");
	public static final Field FIELD_ACTIVEWINDOW = ReflectionUtil.getField(APIReflection.CLASS_ENTITYPLAYER, "activeContainer");
	public static final Field FIELD_WINDOWID = ReflectionUtil.getField(APIReflection.CLASS_CONTAINER, "windowId");
	
	public static final Method METHOD_GETHANDLE = ReflectionUtil.getMethod(APIReflection.CLASS_CRAFTPLAYER, "getHandle", ReflectionUtil.emtyClassArray());
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
