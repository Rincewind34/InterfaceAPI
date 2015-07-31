package de.rincewind.util.item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ItemSerializer {

	/**
	 * @author Janhektor
	 */
	
	private Gson gson;
	
	/**
	 * ItemSerializer: Serialize/Deserialize Item-Arrays as JSON-Strings!
	 * A lightweight and fast util, which is using Gson to work fast with JSON
	 */
	public ItemSerializer() {
		gson = new Gson();
	}
	
	/**
	 * 
	 * @param items (ItemStack-Array) - the items to serialize
	 * @return A string: The JSON-formatted string (result)
	 */
	public String encode(ItemStack[] items) {
		JsonObject root = new JsonObject();
		JsonArray array = new JsonArray();
		
		for (ItemStack is : items) {
			if (is == null) {
				continue;
			}
			
			JsonObject itemObject = new JsonObject();
			itemObject.addProperty("type", is.getType().toString());
			itemObject.addProperty("amount", is.getAmount());
			itemObject.addProperty("durability", is.getDurability());
			
			if (is.getItemMeta().hasDisplayName()) {
				itemObject.addProperty("name", is.getItemMeta().getDisplayName());
			}
			
			if (is.getItemMeta().hasLore()) {
				List<String> lore = is.getItemMeta().getLore();
				JsonArray loreArray = new JsonArray();
				for (String line : lore) {
					JsonObject lineObject = new JsonObject();
					lineObject.addProperty("line", line);
					loreArray.add(lineObject);
				}
				itemObject.add("lore", loreArray);
			}
			
			if (is.getItemMeta().hasEnchants()) {
				JsonArray enchArray = new JsonArray();
				
				for (Enchantment ench : is.getItemMeta().getEnchants().keySet()) {
					JsonObject enchObject = new JsonObject();
					enchObject.addProperty("name", ench.getName());
					enchObject.addProperty("level", is.getItemMeta().getEnchants().get(ench));
					enchArray.add(enchObject);
				}
				
				itemObject.add("enchants", enchArray);
			}
			
			array.add(itemObject);
		}
		
		root.add("items", array);
		return gson.toJson(root);
	}
	
	/**
	 * 
	 * @param saveString The JSON-string, which will be deserialized
	 * @return (ItemStack-Array) - the items ;)
	 */
	public ItemStack[] decode(String saveString) {
		JsonObject root = gson.fromJson(saveString, JsonObject.class);
		JsonArray itemArray = root.get("items").getAsJsonArray();
		ItemStack[] items = new ItemStack[itemArray.size()];
		
		for (int i = 0; i < itemArray.size(); i++) {
			JsonObject itemObject = (JsonObject) itemArray.get(i);
			Material type = Material.valueOf(itemObject.get("type").getAsString());
			int amount = itemObject.get("amount").getAsInt();
			short durability = itemObject.get("durability").getAsShort();
			ItemStack currentItem = new ItemStack(type, amount, durability);
			ItemMeta meta = currentItem.getItemMeta();
			
			if (itemObject.has("name")) {
				meta.setDisplayName(itemObject.get("name").getAsString());
			}
			
			if (itemObject.has("lore")) {
				JsonArray loreArray = itemObject.get("lore").getAsJsonArray();
				List<String> loreLines = new ArrayList<String>();
				
				for (int k = 0; k < loreArray.size(); k++) {
					String line = loreArray.get(i).getAsJsonObject().get("line").getAsString();
					loreLines.add(line);
				}
				
				meta.setLore(loreLines);
			}
			
			if (itemObject.has("enchants")) {
				JsonArray enchArray = itemObject.get("enchants").getAsJsonArray();
				
				for (int k = 0; k < enchArray.size(); k++) {
					JsonObject enchObject = enchArray.get(k).getAsJsonObject();
					String name = enchObject.get("name").getAsString();
					int lvl = enchObject.get("level").getAsInt();
					meta.addEnchant(Enchantment.getByName(name), lvl, false);
				}
			}
			
			currentItem.setItemMeta(meta);
		}
		
		return items;
	}
	
	/**
	 * @see encode
	 */
	public String serialize(ItemStack[] items) {
		return encode(items);
	}
	
	/**
	 * @see decode
	 */
	public ItemStack[] deserialize(String saveString) {
		return decode(saveString);
	}
}
