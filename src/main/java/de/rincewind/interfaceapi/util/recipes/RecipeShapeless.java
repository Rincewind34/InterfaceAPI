package de.rincewind.interfaceapi.util.recipes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class RecipeShapeless implements Recipe {

	private ItemStack result;
	
	private List<MaterialData> ingradients;
	
	public RecipeShapeless() {
		this(null);
	}
	
	public RecipeShapeless(ItemStack result) {
		this.result = result;
		this.ingradients = new ArrayList<>();
	}
	
	public RecipeShapeless(ItemStack result, Material... types) {
		this(result);
		
		for(Material type : types)  {
			this.ingradients.add(new MaterialData(type));
		}
	}
	
	public RecipeShapeless(ItemStack result, MaterialData... types) {
		this(result);
		
		for(MaterialData type : types)  {
			this.ingradients.add(type);
		}
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
	
	public void addIngradient(Material type) {
		this.addIngradient(new MaterialData(type));
	}
	
	public void addIngradient(MaterialData data) {
		this.ingradients.add(data);
	}
	
	public List<MaterialData> getIngradients() {
		return this.ingradients;
	}
}
