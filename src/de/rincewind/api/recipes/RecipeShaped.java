package de.rincewind.api.recipes;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class RecipeShaped implements Recipe {

	private ItemStack result;
	
	private String[] shape;
	
	private Map<Character, MaterialData> ingradients;
	private static Map<Character, MaterialData> staticIngradients;
	
	public RecipeShaped() {
		this(null);
	}
	
	public RecipeShaped(ItemStack result) {
		this.result = result;
		this.ingradients = new HashMap<Character, MaterialData>();
		
		RecipeShaped.staticIngradients = new HashMap<Character, MaterialData>();
	}
	
	public RecipeShaped(ItemStack result, String... shape) {
		this(result);
		this.shape(shape);
	}
	
	@Override
	public void setResult(ItemStack result) {
		if (result == null) {
			return;
		} else {
			this.result = result;
		}
	}

	@Override
	public ItemStack getResult() {
		return this.result;
	}

	@Override
	public RecipeType getType() {
		return RecipeType.SHAPED;
	}
	
	/**
	 * Sets the shape for this recipe.
	 * 
	 * @param lines the shape
	 */
	public void shape(String... lines) {
		if (lines.length > 3) {
			return;
		} else {
			for(int i = 0; i < lines.length; i++) {
				this.shape = lines;
			}
		}
	}
	
	/**
	 * Defines an used key
	 * 
	 * @param key the used keys
	 * @param type the type
	 */
	public void setIngradient(char key, Material type) {
		this.setIngradient(key, new MaterialData(type));
	}
	
	/**
	 * Defines an used key
	 * 
	 * @param key The used key
	 * @param data The MaterialData
	 */
	public void setIngradient(char key, MaterialData data) {
		this.setIngradient(key, data, true);
	}
	
	/**
	 * Defines an used key
	 * 
	 * @param key The used key
	 * @param data The MaterialData
	 * @param flag When false, the key does not enter the static list
	 */
	public void setIngradient(char key, MaterialData data, boolean flag) {
		this.ingradients.put(key, data);
		if (flag) {
			RecipeShaped.setStaticIngradient(key, data);
		}
	}
	
	/**
	 * 
	 * @return The defined keys
	 */
	public Map<Character, MaterialData> getIngradientsMap() {
		Map<Character, MaterialData> map = new HashMap<Character, MaterialData>();
		
		for (String line : this.shape) {
			for (int i = 0; i < line.length(); i++) {
				char key = line.charAt(i);
				
				if (this.ingradients.containsKey(key)) {
					map.put(key, this.ingradients.get(key));
				} else if (RecipeShaped.staticIngradients.containsKey(key)) {
					map.put(key, RecipeShaped.staticIngradients.get(key));
				} else {
					continue;
				}
				//TODO EXCEPTION
			}
		}
		
		return map;
	}
	
	/**
	 * 
	 * @return The defined shape
	 */
	public String[] getShape() {
		return this.shape;
	}
	
	/**
	 * Adds an used key to the static list
	 * 
	 * @param key The used key
	 * @param data The Material
	 */
	public static void setStaticIngradient(char key, Material data) {
		RecipeShaped.setStaticIngradient(key, new MaterialData(data));
	}
	
	/**
	 * Adds an used key to the static list
	 * 
	 * @param key The used key
	 * @param data The MaterialData
	 */
	public static void setStaticIngradient(char key, MaterialData data) {
		RecipeShaped.staticIngradients.put(key, data);
	}

}
