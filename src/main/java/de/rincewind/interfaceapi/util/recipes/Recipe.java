package de.rincewind.interfaceapi.util.recipes;

import org.bukkit.inventory.ItemStack;

public abstract interface Recipe {
	
	public abstract void setResult(ItemStack result);
	
	public abstract ItemStack getResult();
	
	public abstract RecipeType getType();
	
}
