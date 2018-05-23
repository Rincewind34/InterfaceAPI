package de.rincewind.interfaceapi.item;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

public class ItemSerializer {

	public String serialize(ItemStack is) {
		ByteArrayOutputStream bytesOut = new ByteArrayOutputStream(); 
		
		try (BukkitObjectOutputStream out = new BukkitObjectOutputStream(bytesOut)) {
			out.writeObject(is);
			out.flush();
			return Base64Coder.encodeLines(bytesOut.toByteArray());
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public ItemStack deserialize(String base64) {
		byte[] data = Base64Coder.decodeLines(base64);
		ByteArrayInputStream bytesIn = new ByteArrayInputStream(data);
		
		try (BukkitObjectInputStream in = new BukkitObjectInputStream(bytesIn)) {
			return (ItemStack) in.readObject();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
