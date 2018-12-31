package de.rincewind.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_13_R2.CraftServer;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemFactory;
import org.bukkit.craftbukkit.v1_13_R2.util.CraftMagicNumbers;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;

import com.google.common.collect.ImmutableMap;

@SuppressWarnings("deprecation")
public class TestServer implements InvocationHandler {
	
	private static interface MethodHandler {
		Object handle(TestServer server, Object[] args);
	}

	private static final Map<Method, MethodHandler> methods;

	static {
		try {
			ImmutableMap.Builder<Method, MethodHandler> methodMap = ImmutableMap.builder();
			methodMap.put(Server.class.getMethod("isPrimaryThread"), new MethodHandler() {
				@Override
				public Object handle(TestServer server, Object[] args) {
					return Thread.currentThread().equals(server.creatingThread);
				}
			});
			methodMap.put(Server.class.getMethod("getItemFactory"), new MethodHandler() {
				@Override
				public Object handle(TestServer server, Object[] args) {
					return CraftItemFactory.instance();
				}
			});
			methodMap.put(Server.class.getMethod("getPluginManager"), new MethodHandler() {
				@Override
				public Object handle(TestServer server, Object[] args) {
					return server.pluginManager;
				}
			});
			methodMap.put(Server.class.getMethod("getLogger"), new MethodHandler() {
				final Logger logger = Logger.getLogger(TestServer.class.getCanonicalName());

				@Override
				public Object handle(TestServer server, Object[] args) {
					return this.logger;
				}
			});
			methodMap.put(Server.class.getMethod("getName"), new MethodHandler() {
				@Override
				public Object handle(TestServer server, Object[] args) {
					return TestServer.class.getSimpleName();
				}
			});
			methodMap.put(Server.class.getMethod("getVersion"), new MethodHandler() {
				@Override
				public Object handle(TestServer server, Object[] args) {
					return "Version_" + CraftServer.class.getPackage().getImplementationVersion();
				}
			});
			methodMap.put(Server.class.getMethod("getBukkitVersion"), new MethodHandler() {
				@Override
				public Object handle(TestServer server, Object[] args) {
					return "BukkitVersion_" + CraftServer.class.getPackage().getImplementationVersion();
				}
			});
			methodMap.put(Server.class.getMethod("createInventory", InventoryHolder.class, int.class, String.class), new MethodHandler() {
				@Override
				public Object handle(TestServer server, Object[] args) {
					return new TestInventory((String) args[2], (int) args[1]);
				}
			});
			methodMap.put(Server.class.getMethod("createInventory", InventoryHolder.class, InventoryType.class, String.class), new MethodHandler() {
				@Override
				public Object handle(TestServer server, Object[] args) {
					return new TestInventory((String) args[2], ((InventoryType) args[1]).getDefaultSize());
				}
			});
			methodMap.put(Server.class.getMethod("getUnsafe"), new MethodHandler() {
				@Override
				public Object handle(TestServer server, Object[] args) {
					return CraftMagicNumbers.INSTANCE;
				}
			});
			
			methods = methodMap.build();

			TestServer server = new TestServer();
			Server instance = Proxy.getProxyClass(Server.class.getClassLoader(), Server.class).asSubclass(Server.class)
					.getConstructor(InvocationHandler.class).newInstance(server);
			Bukkit.setServer(instance);
			server.pluginManager = new SimplePluginManager(instance, new SimpleCommandMap(instance));
		} catch (Throwable t) {
			throw new Error(t);
		}
	}

	private Thread creatingThread = Thread.currentThread();
	private PluginManager pluginManager;

	private TestServer() {
	}

	public static void setup() {
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) {
		MethodHandler handler = TestServer.methods.get(method);
		if (handler != null) {
			return handler.handle(this, args);
		}
		throw new UnsupportedOperationException(String.valueOf(method));
	}
}
