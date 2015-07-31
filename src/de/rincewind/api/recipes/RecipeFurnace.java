package de.rincewind.api.recipes;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class RecipeFurnace implements Recipe {

	private ItemStack result;
	
	private MaterialData input;
	
	public RecipeFurnace() {
		this(null);
	}
	
	public RecipeFurnace(ItemStack result) {
		this(null, Material.AIR);
	}
	
	@SuppressWarnings("deprecation")
	public RecipeFurnace(ItemStack result, Material input) {
		this(result, new MaterialData(input, (byte) 0));
	}
	
	public RecipeFurnace(ItemStack result, MaterialData input) {
		this.setInput(input);
		this.setResult(result);
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
		return RecipeType.FURNACE;
	}
	
	@SuppressWarnings("deprecation")
	public void setInput(Material input) {
		this.setInput(new MaterialData(input, (byte) 0));
	}
	
	/**
	 * Sets the input for this recipe
	 * 
	 * @param input The new input
	 */
	public void setInput(MaterialData input) {
		this.input = input;
	}
	
	/**
	 * 
	 * @return The defined input
	 */
	public MaterialData getInput() {
		return this.input;
	}
	
}
