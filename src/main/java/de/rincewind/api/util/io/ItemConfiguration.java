package de.rincewind.api.util.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemConfiguration {
	
	private File file;
	private FileConfiguration cfg;
	
	/**
	 * ItemConfiguration: Use it, to save/load items from YAML-Files
	 * @param filePath The path for your file to save
	 * @param fileName The name of your file to save
	 */
	public ItemConfiguration(String filePath, String fileName) {
		this.file = new File(filePath, fileName);
		this.load();
		this.init();
		this.save();
	}
	
	/**
	 * Loads the file
	 */
	public void load() {
		this.cfg = YamlConfiguration.loadConfiguration(this.file);
	}
	
	/**
	 * Saves this configuration
	 */
	public void save() {
		try {
			this.cfg.options().copyDefaults(true);
			this.cfg.save(this.file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return The id of the saved item
	 */
	public int getItemId() {
		return this.cfg.getInt("id");
	}
	
	/**
	 * 
	 * @return The durability of the saved item
	 */
	public short getItemDurability() {
		return (short) this.cfg.getInt("data");
	}
	
	/**
	 * 
	 * @return The name of the saved item
	 */
	public String getItemName() {
		return this.cfg.getString("name").replace("&", "�");
	}

	/**
	 * 
	 * @return The lore of the saved item
	 */	
	public List<String> getItemLore() {
		return this.cfg.getStringList("lore");
	}

	/**
	 * 
	 * @return The enchants of the saved item
	 */	
	@SuppressWarnings("deprecation")
	public Map<Enchantment, Integer> getItemEnchants() {
		List<String> list = this.cfg.getStringList("enchants");
		Map<Enchantment, Integer> map = new HashMap<Enchantment, Integer>();
		
		for(String element : list) {
			try {
				map.put(Enchantment.getById(Integer.parseInt(element.split(":")[0])), Integer.parseInt(element.split(":")[1]));
			} catch(Exception ex) {
				continue;
			}
		}
		
		return map;
	}

	/**
	 * 
	 * @return The amount of the saved item
	 */	
	public int getItemAmount() {
		return this.cfg.getInt("amount");
	}


	@SuppressWarnings("deprecation")
	/**
	 * 
	 * @return The saved item
	 */
	public ItemStack createItem() {
		ItemStack item = new ItemStack(Material.getMaterial(this.getItemId()), this.getItemAmount(), this.getItemDurability());
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(this.getItemName());
		meta.setLore(this.getItemLore());
		
		for (Enchantment ench : this.getItemEnchants().keySet()) {
			meta.addEnchant(ench, this.getItemEnchants().get(ench), false);
		}
		
		item.setItemMeta(meta);
		return item;
	}

	/**
	 * Sets the item id
	 * 
	 * @param value the value
	 */	
	public void setItemId(int value) {
		this.cfg.set("id", value);
	}

	/**
	 * Sets the item durability
	 * 
	 * @param value the value
	 */	
	public void setItemDurability(int value) {
		this.cfg.set("durability", value);
	}	

	/**
	 * Sets the item amount
	 * 
	 * @param value the value
	 */	
	public void setItemAmount(int value) {
		this.cfg.set("amount", value);
	}

	/**
	 * Sets the item name
	 * 
	 * @param value the value
	 */	
	public void setItemName(String value) {
		this.cfg.set("name", value.replace("�", "&"));
	}
	
	@SuppressWarnings("deprecation")
	/**
	 * Changes the enchantments of the saved item
	 * @param enchants The new enchantment-information for this item
	 */
	public void setItemEnchants(Map<Enchantment, Integer> enchants) {
		List<String> list = new ArrayList<String>();
		
		for(Enchantment ench : enchants.keySet()) {
			list.add(Integer.toString(ench.getId()) + ":" + Integer.toString(enchants.get(ench)));
		}
		
		this.cfg.set("enchants", list);
	}
	
	@SuppressWarnings("deprecation")
	/**
	 * Adds a new item to this file; overrides the existing item (if exists)
	 * @return item The new itemstack to save into
	 */
	public void insertItem(ItemStack item) {
		this.setItemId(item.getTypeId());
		this.setItemDurability(item.getDurability());
		this.setItemAmount(item.getAmount());
		
		if (item.getItemMeta().hasDisplayName()) {
			this.setItemName(item.getItemMeta().getDisplayName());
		}
		
		if (item.getItemMeta().hasLore()) {
			this.setItemLore(item.getItemMeta().getLore());
		}
		
		if (item.getItemMeta().hasEnchants()) {
			this.setItemEnchants(item.getItemMeta().getEnchants());
		}
	}
	
	/**
	 * Changes the lore of this item
	 * @param lore The new lore
	 */
	public void setItemLore(List<String> lore) {
		this.cfg.set("lore", lore);		
	}

	/**
	 * @return The internal FileConfiguration
	 */
	protected FileConfiguration getConfig() {
		return this.cfg;
	}
	
	/**
	 * @return The file
	 */
	protected File getFile() {
		return this.file;
	}
	
	/**
	 * Initializes the configuration-settings
	 */
	protected void init() {
		this.cfg.addDefault("id", 1);
		this.cfg.addDefault("durability", 0);
		this.cfg.addDefault("amount", 1);
		this.cfg.addDefault("name", "stone");
		this.cfg.addDefault("lore", new ArrayList<String>());
		this.cfg.addDefault("enchants", new ArrayList<String>());
	}

}
