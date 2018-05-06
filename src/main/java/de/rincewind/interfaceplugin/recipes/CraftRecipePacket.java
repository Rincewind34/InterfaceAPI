package de.rincewind.interfaceplugin.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.material.MaterialData;

import de.rincewind.interfaceapi.util.recipes.RecipePacket;
import de.rincewind.interfaceapi.util.recipes.RecipeShaped;

public class CraftRecipePacket implements RecipePacket {

	private Map<String, RecipeShaped> recipes;
	
	private List<MaterialData> replaces;
	
	public CraftRecipePacket() {
		this.recipes = new HashMap<>();
		this.replaces = new ArrayList<>();
	}
	
	@Override
	public void setRecipe(String key, RecipeShaped recipe) {
		if (key == null) {
			throw new NullPointerException("The key cannot be null!");
		}
		
		if (recipe == null) {
			throw new NullPointerException("The recipe cannot be null!");
		}
		
		this.recipes.put(key, recipe);
	}

	@Override
	public void removeRecipe(String key) {
		if (key == null) {
			throw new NullPointerException("The key cannot be null!");
		} else {
			this.recipes.remove(key);
		}
	}

	@Override
	public void addReplace(MaterialData data) {
		if (data == null) {
			throw new NullPointerException("The data cannot be null!");
		} else {
			this.replaces.add(data);
		}
	}

	@Override
	public void removeReplace(MaterialData data) {
		if (data == null) {
			throw new NullPointerException("The data cannot be null!");
		} else {
			this.replaces.remove(data);
		}
	}

	@Override
	public RecipeShaped getRecipe(String key) {
		if (key == null) {
			throw new NullPointerException("The key cannot be null!");
		} else {
			return this.recipes.get(key);
		}
	}

	@Override
	public List<RecipeShaped> getAllRecipes() {
		List<RecipeShaped> result = new ArrayList<>();
		
		for (RecipeShaped recipe : this.recipes.values()) {
			for (MaterialData data : this.replaces) {
				recipe.setIngradient('#', data, false);
				result.add(recipe);
			}
		}
		
		return result;
	}

}
