package de.rincewind.interfaceapi.util.recipes;

import java.util.List;

import org.bukkit.material.MaterialData;

public abstract interface RecipePacket {
	
	public abstract void setRecipe(String key, RecipeShaped recipe);
	
	public abstract void removeRecipe(String key);
	
	public abstract void addReplace(MaterialData data);
	
	public abstract void removeReplace(MaterialData data);
	
	public abstract RecipeShaped getRecipe(String key);
	
	public abstract List<RecipeShaped> getAllRecipes();
}
