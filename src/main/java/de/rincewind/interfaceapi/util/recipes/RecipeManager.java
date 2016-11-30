package de.rincewind.interfaceapi.util.recipes;

import java.util.List;

import org.bukkit.inventory.ItemStack;

import de.rincewind.interfaceplugin.recipes.CraftRecipeManager;

public abstract interface RecipeManager {
	
	public abstract void addRecipe(Recipe recipe);
	
	public abstract void removeRecipe(Recipe recipe);
	
	public abstract void removeRecipe(int index, RecipeType type);
	
	public abstract void clear();
	
	public abstract void clear(RecipeType type);
	
	public abstract void importPacket(RecipePacket packet);
	
	public abstract void importManager(RecipeManager manager);
	
	public abstract void activate();
	
	public abstract boolean containsRecipe(Recipe recipe);
	
	public abstract boolean hasRecipe(int index, RecipeType type);
	
	public abstract Recipe getRecipe(int index, RecipeType type);
	
	public abstract List<Recipe> getRecipes(ItemStack result);
	
	public abstract List<Recipe> getRecipes(ItemStack result, RecipeType type);
	
	public abstract List<Recipe> getRecipes(RecipeType type);
	
	
	public abstract static class RecipeManagerExtendable extends CraftRecipeManager {
		
	}
	
}
