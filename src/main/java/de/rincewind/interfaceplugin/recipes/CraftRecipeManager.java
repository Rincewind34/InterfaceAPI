package de.rincewind.interfaceplugin.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.material.MaterialData;

import de.rincewind.interfaceapi.util.recipes.Recipe;
import de.rincewind.interfaceapi.util.recipes.RecipeFurnace;
import de.rincewind.interfaceapi.util.recipes.RecipeManager;
import de.rincewind.interfaceapi.util.recipes.RecipePacket;
import de.rincewind.interfaceapi.util.recipes.RecipeShaped;
import de.rincewind.interfaceapi.util.recipes.RecipeShapeless;
import de.rincewind.interfaceapi.util.recipes.RecipeType;

public class CraftRecipeManager implements RecipeManager {

	private final Map<RecipeType, List<Recipe>> recipes;

	public CraftRecipeManager() {
		this.recipes = new HashMap<>();

		for (RecipeType type : RecipeType.values()) {
			this.recipes.put(type, new ArrayList<Recipe>());
		}
	}

	@Override
	public void addRecipe(Recipe recipe) {
		if (recipe == null) {
			return;
		}

		if (this.containsRecipe(recipe)) {
			return;
		} else {
			this.recipes.get(recipe.getType()).add(recipe);
		}
	}

	@Override
	public void removeRecipe(Recipe recipe) {
		if (recipe == null) {
			return;
		}

		if (!this.containsRecipe(recipe)) {
			return;
		} else {
			this.recipes.get(recipe.getType()).remove(recipe);
		}
	}

	@Override
	public void removeRecipe(int index, RecipeType type) {
		if (!this.hasRecipe(index, type)) {
			return;
		}

		this.removeRecipe(this.getRecipe(index, type));
	}

	@Override
	public void clear() {
		for (RecipeType type : RecipeType.values()) {
			this.clear(type);
		}
	}

	@Override
	public void clear(RecipeType type) {
		this.recipes.get(type).clear();
	}

	@Override
	public void importPacket(RecipePacket packet) {
		for (RecipeShaped recipe : packet.getAllRecipes()) {
			this.addRecipe(recipe);
		}
	}

	@Override
	public void importManager(RecipeManager manager) {
		for (RecipeType type : RecipeType.values()) {
			this.recipes.get(type).addAll(manager.getRecipes(type));
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void activate() {
//		APIReflection.clearRecipes("CraftingManager"); TODO
//		APIReflection.clearRecipes("RecipesFurnace");

		for (Recipe recipe : this.getRecipes(RecipeType.SHAPED)) {
			ShapedRecipe bukkitRecipe = new ShapedRecipe(recipe.getResult());
			bukkitRecipe.shape(((RecipeShaped) recipe).getShape());
			for (char key : ((RecipeShaped) recipe).getIngradientsMap().keySet()) {
				bukkitRecipe.setIngredient(key, ((RecipeShaped) recipe).getIngradientsMap().get(key));
			}
			Bukkit.addRecipe(bukkitRecipe);
		}

		for (Recipe recipe : this.getRecipes(RecipeType.SHAPLESS)) {
			ShapelessRecipe bukkitRecipe = new ShapelessRecipe(recipe.getResult());
			for (MaterialData data : ((RecipeShapeless) recipe).getIngradients()) {
				bukkitRecipe.addIngredient(data);
			}
			Bukkit.addRecipe(bukkitRecipe);
		}

		for (Recipe recipe : this.getRecipes(RecipeType.FURNACE)) {
			Bukkit.addRecipe(new FurnaceRecipe(recipe.getResult(), ((RecipeFurnace) recipe).getInput()));
		}
	}

	@Override
	public Recipe getRecipe(int index, RecipeType type) {
		if (!this.hasRecipe(index, type)) {
			return null;
		} else {
			return this.recipes.get(type).get(index);
		}
	}

	@Override
	public List<Recipe> getRecipes(ItemStack result) {
		if (result == null) {
			return null;
		}

		List<Recipe> targetRecipes = new ArrayList<>();

		for (RecipeType type : RecipeType.values()) {
			targetRecipes.addAll(this.getRecipes(result, type));
		}

		return targetRecipes;
	}

	@Override
	public List<Recipe> getRecipes(ItemStack result, RecipeType type) {
		if (result == null) {
			return null;
		}

		if (type == null) {
			return null;
		}

		List<Recipe> targetRecipes = new ArrayList<>();

		for (Recipe targetRecipe : this.getRecipes(type)) {
			if (targetRecipe.getResult().equals(result)) {
				targetRecipes.add(targetRecipe);
			}
		}

		return targetRecipes;
	}

	@Override
	public List<Recipe> getRecipes(RecipeType type) {
		if (type == null) {
			return null;
		} else {
			return this.recipes.get(type);
		}
	}

	@Override
	public boolean containsRecipe(Recipe recipe) {
		for (RecipeType type : RecipeType.values()) {
			if (this.getRecipes(type).contains(recipe)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean hasRecipe(int index, RecipeType type) {
		if (type == null) {
			return false;
		} else if (0 > index || index >= this.recipes.get(type).size()) {
			return false;
		} else {
			return true;
		}
	}

}
