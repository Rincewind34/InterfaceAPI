package de.rincewind.api.recipes;

import org.bukkit.inventory.ItemStack;

public abstract interface Recipe {
	
	/**
	 * Sets the result
	 * @param result The itemstack result
	 */
	public abstract void setResult(ItemStack result);
	
	/**
	 * @return The result (itemstack)
	 */
	public abstract ItemStack getResult();
	
	/**
	 * @return The RecipeType (type of this recipe)
	 * The following RecipeTypes are currently exists:
	 * <ul>
	 * 	<li>FURNACE</li>
	 * 	<li>SHAPED</li>
	 * 	<li>SHAPELESS</li>
	 * </ul>
	 */
	public abstract RecipeType getType();
	
}
