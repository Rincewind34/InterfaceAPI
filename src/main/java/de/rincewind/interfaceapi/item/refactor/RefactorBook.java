package de.rincewind.interfaceapi.item.refactor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import de.rincewind.interfaceapi.gui.elements.util.Lore;

public class RefactorBook {
	
	public ItemStack own(ItemStack item, String author) {
		ItemStack clone = item.clone();
		BookMeta meta = (BookMeta) clone.getItemMeta();
		meta.setAuthor(author);
		clone.setItemMeta(meta);
		return clone;
	}
	
	public ItemStack name(ItemStack item, String title) {
		ItemStack clone = item.clone();
		BookMeta meta = (BookMeta) clone.getItemMeta();
		meta.setTitle(title);
		clone.setItemMeta(meta);
		return clone;
	}
	
	public ItemStack addPage(ItemStack item, String page) {
		ItemStack clone = item.clone();
		BookMeta meta = (BookMeta) clone.getItemMeta();
		meta.addPage(page);
		clone.setItemMeta(meta);
		return clone;
	}
	
	public ItemStack setPage(ItemStack item, String page, int index) {
		ItemStack clone = item.clone();
		BookMeta meta = (BookMeta) clone.getItemMeta();
		meta.setPage(index, page);
		clone.setItemMeta(meta);
		return clone;
	}
	
	public ItemStack setPages(ItemStack item, List<String> pages) {
		ItemStack clone = item.clone();
		BookMeta meta = (BookMeta) clone.getItemMeta();
		meta.setPages(pages);
		clone.setItemMeta(meta);
		return clone;
	}
	
	public ItemStack setPages(ItemStack item, String... pages) {
		ItemStack clone = item.clone();
		BookMeta meta = (BookMeta) clone.getItemMeta();
		meta.setPages(pages);
		clone.setItemMeta(meta);
		return clone;
	}
	
	public static class BookContent {
		
		private List<String> pages;
		
		public BookContent() {
			this(new ArrayList<String>());
		}
		
		public BookContent(List<String> pages) {
			this.pages = pages;
		}
		
		public BookContent(BookContent pages) {
			this(pages.asList());
		}
		
		public BookContent(String... array) {
			this(Arrays.asList(array));
		}
		
		public Lore addElement(String element) {
			this.pages.add(element);
			return new Lore(this.pages);
		}
		
		public List<String> asList() {
			return pages;
		}
		
		public String[] asArray() {
			return this.pages.toArray(new String[this.pages.size()]);
		}
		
	}
	
	
}
