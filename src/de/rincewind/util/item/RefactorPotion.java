package de.rincewind.util.item;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RefactorPotion {
	
	public ItemStack addPotionEffect(ItemStack item, PotionEffect effect, boolean flag) {
		ItemStack clone = item.clone();
		PotionMeta meta = (PotionMeta) clone.getItemMeta();
		meta.addCustomEffect(effect, flag);
		item.setItemMeta(meta);
		return clone;
	}
	
	public ItemStack setMainEffect(ItemStack item, PotionEffectType effect) {
		ItemStack clone = item.clone();
		PotionMeta meta = (PotionMeta) clone.getItemMeta();
		meta.setMainEffect(effect);
		item.setItemMeta(meta);
		return clone;
	}
	
	public ItemStack removeEffect(ItemStack item, PotionEffectType effect) {
		ItemStack clone = item.clone();
		PotionMeta meta = (PotionMeta) clone.getItemMeta();
		meta.removeCustomEffect(effect);
		item.setItemMeta(meta);
		return clone;
	}
	
}
