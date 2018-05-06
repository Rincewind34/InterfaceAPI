package de.rincewind.interfaceapi.item.categorys;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.material.MaterialData;

public class Category {
	
	public abstract static class Special {
		
		public static final Category ARMOR = Categorys.get("armor");
		public static final Category TOOLS = Categorys.get("tools");
		public static final Category DRINKABLE = Categorys.get("drinkable");
		public static final Category EATABLE = Categorys.get("eatable");
		public static final Category WEAPONS = Categorys.get("weapons");
	
	}
	
	public abstract static class CreativTab {
		
		public static final Category BLOCKS = Categorys.get("tabBlocks");
		public static final Category DEKORATION = Categorys.get("tabDeko");
		public static final Category REDSTONE = Categorys.get("tabRedstone");
		public static final Category MOVEMENT = Categorys.get("tabMove");
		public static final Category UTILITYS = Categorys.get("tabUtil");
		public static final Category FOOD = Categorys.get("tabEat");
		public static final Category TOOLS = Categorys.get("tabTools");
		public static final Category COMBAT = Categorys.get("tabCombats");
		public static final Category BREWING = Categorys.get("tabBrewing");
		public static final Category MATERIALS = Categorys.get("tabMaterials");
		
	}
	
	private List<MaterialData> materials;
	
	public Category() {
		this.materials = new ArrayList<>();
	}
	
	@SuppressWarnings("deprecation")
	public boolean containsMaterial(Material material) {
		return this.containsMaterial(new MaterialData(material, (byte) 0));
	}
	
	@SuppressWarnings("deprecation")
	public boolean containsMaterial(MaterialData material) {
		for (MaterialData data : this.materials) {
			if(data.getItemType() == material.getItemType() && data.getData() == material.getData()) return true;
		}
		
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public void addMaterial(Material material) {
		this.addMaterial(new MaterialData(material, (byte) 0));
	}
	
	public void addMaterial(MaterialData material) {
		if (this.containsMaterial(material)) {
			return;
		} else {
			this.materials.add(material);
		}
	}
	
	public void importFrom(Category category) {
		for (MaterialData data : category.getMaterials()) {
			this.addMaterial(data);
		}
	}
	
	protected List<MaterialData> getMaterials() {
		return this.materials;
	}
	
}
